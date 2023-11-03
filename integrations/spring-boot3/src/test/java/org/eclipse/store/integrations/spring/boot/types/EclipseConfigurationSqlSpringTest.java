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

import org.eclipse.store.integrations.spring.boot.types.configuration.ConfigurationValues;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestPropertySource("classpath:sql-test.properties")
@SpringBootTest(classes = {EclipseStoreConfiguration.class})
public class EclipseConfigurationSqlSpringTest
{

    @Autowired
    ConfigurationValues values;

    @Autowired
    EclipseStoreConfigConverter converter;

    @Test
    void checkStorageDirectoryValue()
    {
        assertNotNull(values.getStorageFilesystem());
    }

    @Test
    void converterBasicTest()
    {
        Map<String, String> valueMap = converter.convertConfigurationToMap(values);
        assertTrue(valueMap.containsKey("storage-filesystem.sql.postgres.user"));
    }
}
