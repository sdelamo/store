package org.eclipse.store.integrations.spring.boot.types.storages;

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

import org.eclipse.store.integrations.spring.boot.types.EclipseStoreProvider;
import org.eclipse.store.integrations.spring.boot.types.EclipseStoreSpringBoot;
import org.eclipse.store.integrations.spring.boot.types.configuration.EclipseStoreProperties;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageFoundation;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.system.ApplicationTemp;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource("classpath:application-two-storages.properties")
@Import(TwoBeanConfiguration.class)
@SpringBootTest(classes = {EclipseStoreSpringBoot.class, TwoStoragesTest.class})
public class TwoStoragesTest
{

    @Autowired
    @Qualifier("first_storage")
    EmbeddedStorageManager first_storage;

    @Autowired
    @Qualifier("second_storage")
    EmbeddedStorageManager second_storage;



    @Test
    void name()
    {
        System.out.println(first_storage.root());
        System.out.println(second_storage.root());
    }

}
