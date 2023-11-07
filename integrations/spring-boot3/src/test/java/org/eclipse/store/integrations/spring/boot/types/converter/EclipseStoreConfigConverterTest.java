package org.eclipse.store.integrations.spring.boot.types.converter;

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

import java.util.Map;

import org.eclipse.store.integrations.spring.boot.types.configuration.ConfigurationValues;
import org.eclipse.store.integrations.spring.boot.types.configuration.StorageFilesystem;
import org.eclipse.store.integrations.spring.boot.types.configuration.sql.Mariadb;
import org.eclipse.store.integrations.spring.boot.types.configuration.sql.Sql;
import org.eclipse.store.integrations.spring.boot.types.converter.EclipseStoreConfigConverter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EclipseStoreConfigConverterTest
{

    private final EclipseStoreConfigConverter converter = new EclipseStoreConfigConverter();

    @Test
    void testBasicConversion()
    {
        ConfigurationValues properties = new ConfigurationValues();
        properties.setChannelCount("4");

        EclipseStoreConfigConverter converter = new EclipseStoreConfigConverter();
        Map<String, String> stringStringMap = converter.convertConfigurationToMap(properties);

        assertNotNull(stringStringMap);
        assertEquals(1, stringStringMap.size());
    }

    @Test
    void testSQLConfiguration()
    {
        final String CATALOG = "super_catalog";

        Sql sql = new Sql();
        Mariadb mariadb = new Mariadb();
        mariadb.setCatalog(CATALOG);
        mariadb.setPassword("myPssw");
        sql.setMariadb(mariadb);
        StorageFilesystem storageFilesystem = new StorageFilesystem();
        storageFilesystem.setSql(sql);
        ConfigurationValues values = new ConfigurationValues();
        values.setStorageFilesystem(storageFilesystem);

        EclipseStoreConfigConverter converter = new EclipseStoreConfigConverter();
        Map<String, String> valueMap = converter.convertConfigurationToMap(values);

        assertTrue(valueMap.containsKey("storage-filesystem.sql.mariadb.catalog"));
        assertEquals(CATALOG, valueMap.get("storage-filesystem.sql.mariadb.catalog"));
    }

    @Test
    void testConvertConfigurationToMap() {
        ConfigurationValues configValues = new ConfigurationValues();
        Map<String, String> result = converter.convertConfigurationToMap(configValues);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testConvertConfigurationToMapWithStorageDirectory() {
        ConfigurationValues configValues = new ConfigurationValues();
        configValues.setStorageDirectory("storage/dir");
        Map<String, String> result = converter.convertConfigurationToMap(configValues);
        assertNotNull(result);
        assertEquals("storage/dir", result.get(EclipseStoreConfigConverter.STORAGE_DIRECTORY));
    }

    @Test
    void testNullValuesAreRemoved() {
        ConfigurationValues configValues = new ConfigurationValues();
        configValues.setStorageDirectory(null);
        configValues.setStorageFilesystem(new StorageFilesystem());
        configValues.setBackupDirectory("backup/dir");

        Map<String, String> result = converter.convertConfigurationToMap(configValues);

        assertNull(result.get(EclipseStoreConfigConverter.STORAGE_DIRECTORY));
        assertNotNull(result.get(EclipseStoreConfigConverter.BACKUP_DIRECTORY));
    }

    @Test
    void testComposeKey() {
        String result = converter.composeKey("prefix", "suffix");
        assertEquals("prefix.suffix", result);
    }
}
