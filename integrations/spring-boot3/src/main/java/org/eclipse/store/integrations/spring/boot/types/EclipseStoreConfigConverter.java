package org.eclipse.store.integrations.spring.boot.types;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.eclipse.store.integrations.spring.boot.types.configuration.ConfigurationValues;
import org.eclipse.store.integrations.spring.boot.types.configuration.StorageFilesystem;
import org.eclipse.store.integrations.spring.boot.types.configuration.sql.AbstractSqlConfiguration;
import org.eclipse.store.integrations.spring.boot.types.configuration.sql.Sql;
import org.eclipse.store.storage.embedded.configuration.types.EmbeddedStorageConfigurationPropertyNames;

public class EclipseStoreConfigConverter
{

    private final String STORAGE_DIRECTORY = EmbeddedStorageConfigurationPropertyNames.STORAGE_DIRECTORY;
    private final String STORAGE_FILESYSTEM = EmbeddedStorageConfigurationPropertyNames.STORAGE_FILESYSTEM;
    private final String DELETION_DIRECTORY = EmbeddedStorageConfigurationPropertyNames.DELETION_DIRECTORY;
    private final String TRUNCATION_DIRECTORY = EmbeddedStorageConfigurationPropertyNames.TRUNCATION_DIRECTORY;
    private final String BACKUP_DIRECTORY = EmbeddedStorageConfigurationPropertyNames.BACKUP_DIRECTORY;
    private final String BACKUP_FILESYSTEM = EmbeddedStorageConfigurationPropertyNames.BACKUP_FILESYSTEM;
    private final String CHANNEL_COUNT = EmbeddedStorageConfigurationPropertyNames.CHANNEL_COUNT;
    private final String CHANNEL_DIRECTORY_PREFIX = EmbeddedStorageConfigurationPropertyNames.CHANNEL_DIRECTORY_PREFIX;
    private final String DATA_FILE_PREFIX = EmbeddedStorageConfigurationPropertyNames.DATA_FILE_PREFIX;
    private final String DATA_FILE_SUFFIX = EmbeddedStorageConfigurationPropertyNames.DATA_FILE_SUFFIX;
    private final String TRANSACTION_FILE_PREFIX = EmbeddedStorageConfigurationPropertyNames.TRANSACTION_FILE_PREFIX;
    private final String TRANSACTION_FILE_SUFFIX = EmbeddedStorageConfigurationPropertyNames.TRANSACTION_FILE_SUFFIX;
    private final String TYPE_DICTIONARY_FILE_NAME = EmbeddedStorageConfigurationPropertyNames.TYPE_DICTIONARY_FILE_NAME;
    private final String RESCUED_FILE_SUFFIX = EmbeddedStorageConfigurationPropertyNames.RESCUED_FILE_SUFFIX;
    private final String LOCK_FILE_NAME = EmbeddedStorageConfigurationPropertyNames.LOCK_FILE_NAME;
    private final String HOUSEKEEPING_INTERVAL = EmbeddedStorageConfigurationPropertyNames.HOUSEKEEPING_INTERVAL;
    private final String HOUSEKEEPING_TIME_BUDGET = EmbeddedStorageConfigurationPropertyNames.HOUSEKEEPING_TIME_BUDGET;
    private final String ENTITY_CACHE_THRESHOLD = EmbeddedStorageConfigurationPropertyNames.ENTITY_CACHE_THRESHOLD;
    private final String ENTITY_CACHE_TIMEOUT = EmbeddedStorageConfigurationPropertyNames.ENTITY_CACHE_TIMEOUT;
    private final String DATA_FILE_MINIMUM_SIZE = EmbeddedStorageConfigurationPropertyNames.DATA_FILE_MINIMUM_SIZE;
    private final String DATA_FILE_MAXIMUM_SIZE = EmbeddedStorageConfigurationPropertyNames.DATA_FILE_MAXIMUM_SIZE;
    private final String DATA_FILE_MINIMUM_USE_RATIO = EmbeddedStorageConfigurationPropertyNames.DATA_FILE_MINIMUM_USE_RATIO;
    private final String DATA_FILE_CLEANUP_HEAD_FILE = EmbeddedStorageConfigurationPropertyNames.DATA_FILE_CLEANUP_HEAD_FILE;


    public Map<String, String> convertConfigurationToMap(ConfigurationValues properties)
    {

        Map<String, String> configValues = new HashMap<>();
        configValues.put(STORAGE_DIRECTORY, properties.getStorageDirectory());

        if (properties.getStorageFilesystem() != null)
        {
            configValues.putAll(prepareFileSystem(properties.getStorageFilesystem(), STORAGE_FILESYSTEM));
        }

        configValues.put(DELETION_DIRECTORY, properties.getDeletionDirectory());
        configValues.put(TRUNCATION_DIRECTORY, properties.getTruncationDirectory());
        configValues.put(BACKUP_DIRECTORY, properties.getBackupDirectory());

        if (properties.getBackupFilesystem() != null)
        {
            configValues.putAll(prepareFileSystem(properties.getBackupFilesystem(), BACKUP_FILESYSTEM));
        }

        configValues.put(CHANNEL_COUNT, properties.getChannelCount());
        configValues.put(CHANNEL_DIRECTORY_PREFIX, properties.getChannelDirectoryPrefix());
        configValues.put(DATA_FILE_PREFIX, properties.getDataFilePrefix());
        configValues.put(DATA_FILE_SUFFIX, properties.getDataFileSuffix());
        configValues.put(TRANSACTION_FILE_PREFIX, properties.getTransactionFilePrefix());
        configValues.put(TRANSACTION_FILE_SUFFIX, properties.getTransactionFileSuffix());
        configValues.put(TYPE_DICTIONARY_FILE_NAME, properties.getTypeDictionaryFileName());
        configValues.put(RESCUED_FILE_SUFFIX, properties.getRescuedFileSuffix());
        configValues.put(LOCK_FILE_NAME, properties.getLockFileName());
        configValues.put(HOUSEKEEPING_INTERVAL, properties.getHousekeepingInterval());
        configValues.put(HOUSEKEEPING_TIME_BUDGET, properties.getHousekeepingTimeBudget());
        configValues.put(ENTITY_CACHE_THRESHOLD, properties.getEntityCacheThreshold());
        configValues.put(ENTITY_CACHE_TIMEOUT, properties.getEntityCacheTimeout());
        configValues.put(DATA_FILE_MINIMUM_SIZE, properties.getDataFileMinimumSize());
        configValues.put(DATA_FILE_MAXIMUM_SIZE, properties.getDataFileMaximumSize());
        configValues.put(DATA_FILE_MINIMUM_USE_RATIO, properties.getDataFileMinimumUseRatio());
        configValues.put(DATA_FILE_CLEANUP_HEAD_FILE, properties.getDataFileCleanupHeadFile());


        //remove keys with null value
        configValues.values()
                .removeIf(Objects::isNull);


        return configValues;
    }

    private Map<String, String> prepareFileSystem(StorageFilesystem properties, String key)
    {
        Map<String, String> values = new HashMap<>();
        if (properties.getSql() != null) {
            values.putAll(prepareSql(properties.getSql(), key + "." + ConfigKeys.SQL.getValue()));
        }

        return values;
    }

    private Map<String, String> prepareSql(Sql sql, String key)
    {
        Map<String, String> values = new HashMap<>();
        if (sql.getMariadb() != null) {
            values.putAll(prepareSqlBasic(sql.getMariadb(), key + "." + ConfigKeys.MARIADB.getValue()));
        }
        if (sql.getOracle() != null) {
            values.putAll(prepareSqlBasic(sql.getOracle(), key + "." + ConfigKeys.ORACLE.getValue()));
        }
        if (sql.getPostgres() != null) {
            values.putAll(prepareSqlBasic(sql.getPostgres(), key + "." + ConfigKeys.POSTGRES.getValue()));
        }
        if (sql.getSqlite() != null) {
            values.putAll(prepareSqlBasic(sql.getSqlite(), key + "." + ConfigKeys.SQLITE.getValue()));
        }

        return values;
    }

    private Map<String, String> prepareSqlBasic(AbstractSqlConfiguration properties, String key)
    {
        Map<String, String> values = new HashMap<>();
        values.put(key + "." + ConfigKeys.SQL_DATA_SOURCE_PROVIDER.getValue(), properties.getDataSourceProvider());
        values.put(key + "." + ConfigKeys.SQL_CATALOG.getValue(), properties.getCatalog());
        values.put(key + "." + ConfigKeys.SQL_SCHEMA.getValue(), properties.getSchema());
        values.put(key + "." + ConfigKeys.SQL_URL.getValue(), properties.getUrl());
        values.put(key + "." + ConfigKeys.SQL_USER.getValue(), properties.getUser());
        values.put(key + "." + ConfigKeys.SQL_PASSWORD.getValue(), properties.getPassword());
        return values;
    }

}
