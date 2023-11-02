package org.eclipse.store.integrations.spring.boot.types;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.eclipse.store.integrations.spring.boot.types.configuration.ConfigurationValues;
import org.eclipse.store.integrations.spring.boot.types.configuration.StorageFilesystem;
import org.eclipse.store.integrations.spring.boot.types.configuration.sql.Mariadb;
import org.eclipse.store.integrations.spring.boot.types.configuration.sql.Sql;
import org.junit.jupiter.api.Test;

class EclipseStoreConfigConverterTest
{


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
        String CATALOG = "super_catalog";
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
}
