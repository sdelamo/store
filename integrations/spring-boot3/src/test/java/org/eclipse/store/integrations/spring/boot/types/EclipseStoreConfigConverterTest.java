package org.eclipse.store.integrations.spring.boot.types;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;

import org.eclipse.store.integrations.spring.boot.types.configuration.ConfigurationValues;
import org.eclipse.store.integrations.spring.boot.types.configuration.StorageFilesystem;
import org.eclipse.store.integrations.spring.boot.types.configuration.sql.Mariadb;
import org.eclipse.store.integrations.spring.boot.types.configuration.sql.Sql;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;

class EclipseStoreConfigConverterTest
{


    @Test
    void testBasicConvertion()
    {
        ConfigurationValues properties = new ConfigurationValues();
        properties.setChannelCount("4");

        EclipseStoreConfigConverter converter = new EclipseStoreConfigConverter();
        Map<String, String> stringStringMap = converter.convertConfigurationToMap(properties);

        assertNotNull(stringStringMap);
        assertEquals(1, stringStringMap.size());
    }

    @Test
    void testSQLConfigration()
    {
        Sql sql = new Sql();
        Mariadb mariadb = new Mariadb();
        mariadb.setCatalog("super_catalog");
        mariadb.setPassword("myPssw");
        sql.setMariadb(mariadb);
        StorageFilesystem storageFilesystem = new StorageFilesystem();
        storageFilesystem.setSql(sql);
        ConfigurationValues values = new ConfigurationValues();
        values.setStorageFilesystem(storageFilesystem);

        EclipseStoreConfigConverter converter = new EclipseStoreConfigConverter();
        Map<String, String> stringStringMap = converter.convertConfigurationToMap(values);

        System.out.println(stringStringMap);

    }
}
