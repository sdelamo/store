package org.eclipse.store.integrations.spring.boot.types;

import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource("classpath:application-run.properties")
@SpringBootTest(classes = {EclipseStoreSpringBoot.class})
public class InjectStorageBeanTest
{

    @Autowired
    EmbeddedStorageManager manager;


    @Test
    void storeSomething()
    {
        manager.start();
        manager.setRoot("ahoj");
        manager.storeRoot();
        manager.shutdown();
    }
}
