package org.eclipse.store.integrations.spring.boot.types;


import org.eclipse.store.integrations.spring.boot.types.configuration.ConfigurationValues;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageFoundation;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationTemp;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource("classpath:application-test.properties")
@SpringBootTest(classes = {EclipseStoreConfiguration.class, StorageBeanTest.class})
public class StorageBeanTest
{

    @Autowired
    EclipseStoreProvider provider;

    @Autowired
    ConfigurationValues myConfiguration;

    @Bean
    @Primary
    EmbeddedStorageManager injectStorageTest()
    {

        ApplicationTemp temp = new ApplicationTemp();
        myConfiguration.setStorageDirectory(temp.getDir().getAbsolutePath());
        EmbeddedStorageFoundation<?> storageFoundation = provider.createStorageFoundation(myConfiguration);

        return storageFoundation.createEmbeddedStorageManager();
    }

    @Test
    void restartStorageTest(@Autowired EmbeddedStorageManager manager)
    {
        String s = "ahoj";
        manager.start();
        manager.setRoot(s);
        manager.storeRoot();
        manager.shutdown();

        EmbeddedStorageManager storage = provider.createStorage(myConfiguration);
        storage.start();
        String stringFromStorage = (String) storage.root();
        Assertions.assertEquals(s, stringFromStorage);
    }
}
