package org.eclipse.store.integrations.spring.boot.types;

/*-
 * #%L
 * spring-boot3
 * %%
 * Copyright (C) 2023 MicroStream Software
 * %%
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * #L%
 */


import org.eclipse.store.integrations.spring.boot.types.configuration.EclipseStoreProperties;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageFoundation;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.system.ApplicationTemp;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource("classpath:application-run.properties")
@SpringBootTest(classes = {EclipseStoreSpringBoot.class, StorageBeanTest.class})
public class StorageBeanTest
{

    @Autowired
    EclipseStoreProvider provider;

    @Autowired
    EclipseStoreProperties myConfiguration;

    static String tempFolder = null;

    @Bean
    EmbeddedStorageManager injectStorageTest()
    {

        ApplicationTemp temp = new ApplicationTemp();
        tempFolder = temp.getDir().getAbsolutePath();
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

        Assertions.assertEquals(tempFolder, manager.configuration().fileProvider().baseDirectory().toPathString());

        try (EmbeddedStorageManager storage = provider.createStorage(myConfiguration))
        {
            storage.start();
            String stringFromStorage = (String) storage.root();
            Assertions.assertEquals(s, stringFromStorage);
        }
    }
}
