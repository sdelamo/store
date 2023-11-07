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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.eclipse.store.integrations.spring.boot.types.configuration.EclipseStoreProperties;
import org.eclipse.store.integrations.spring.boot.types.configuration.StorageFilesystem;
import org.eclipse.store.integrations.spring.boot.types.configuration.aws.AbstractAwsProperties;
import org.eclipse.store.integrations.spring.boot.types.configuration.aws.Aws;
import org.eclipse.store.integrations.spring.boot.types.configuration.azure.Azure;
import org.eclipse.store.integrations.spring.boot.types.configuration.oracle.Oracle;
import org.eclipse.store.integrations.spring.boot.types.configuration.oracle.coherence.Coherence;
import org.eclipse.store.integrations.spring.boot.types.configuration.oraclecloud.Oraclecloud;
import org.eclipse.store.integrations.spring.boot.types.configuration.sql.AbstractSqlConfiguration;
import org.eclipse.store.integrations.spring.boot.types.configuration.sql.Sql;
import org.eclipse.store.storage.embedded.configuration.types.EmbeddedStorageConfigurationPropertyNames;
import org.springframework.stereotype.Component;

@Component
public class EclipseStoreConfigConverter
{

    /**
     * These fields are there to check in test, if all keys from Eclipse Store Configuration are covered by these module.
     */
    protected static final String STORAGE_DIRECTORY = EmbeddedStorageConfigurationPropertyNames.STORAGE_DIRECTORY;
    protected static final String STORAGE_FILESYSTEM = EmbeddedStorageConfigurationPropertyNames.STORAGE_FILESYSTEM;
    protected static final String DELETION_DIRECTORY = EmbeddedStorageConfigurationPropertyNames.DELETION_DIRECTORY;
    protected static final String TRUNCATION_DIRECTORY = EmbeddedStorageConfigurationPropertyNames.TRUNCATION_DIRECTORY;
    protected static final String BACKUP_DIRECTORY = EmbeddedStorageConfigurationPropertyNames.BACKUP_DIRECTORY;
    protected static final String BACKUP_FILESYSTEM = EmbeddedStorageConfigurationPropertyNames.BACKUP_FILESYSTEM;
    protected static final String CHANNEL_COUNT = EmbeddedStorageConfigurationPropertyNames.CHANNEL_COUNT;
    protected static final String CHANNEL_DIRECTORY_PREFIX = EmbeddedStorageConfigurationPropertyNames.CHANNEL_DIRECTORY_PREFIX;
    protected static final String DATA_FILE_PREFIX = EmbeddedStorageConfigurationPropertyNames.DATA_FILE_PREFIX;
    protected static final String DATA_FILE_SUFFIX = EmbeddedStorageConfigurationPropertyNames.DATA_FILE_SUFFIX;
    protected static final String TRANSACTION_FILE_PREFIX = EmbeddedStorageConfigurationPropertyNames.TRANSACTION_FILE_PREFIX;
    protected static final String TRANSACTION_FILE_SUFFIX = EmbeddedStorageConfigurationPropertyNames.TRANSACTION_FILE_SUFFIX;
    protected static final String TYPE_DICTIONARY_FILE_NAME = EmbeddedStorageConfigurationPropertyNames.TYPE_DICTIONARY_FILE_NAME;
    protected static final String RESCUED_FILE_SUFFIX = EmbeddedStorageConfigurationPropertyNames.RESCUED_FILE_SUFFIX;
    protected static final String LOCK_FILE_NAME = EmbeddedStorageConfigurationPropertyNames.LOCK_FILE_NAME;
    protected static final String HOUSEKEEPING_INTERVAL = EmbeddedStorageConfigurationPropertyNames.HOUSEKEEPING_INTERVAL;
    protected static final String HOUSEKEEPING_TIME_BUDGET = EmbeddedStorageConfigurationPropertyNames.HOUSEKEEPING_TIME_BUDGET;
    protected static final String ENTITY_CACHE_THRESHOLD = EmbeddedStorageConfigurationPropertyNames.ENTITY_CACHE_THRESHOLD;
    protected static final String ENTITY_CACHE_TIMEOUT = EmbeddedStorageConfigurationPropertyNames.ENTITY_CACHE_TIMEOUT;
    protected static final String DATA_FILE_MINIMUM_SIZE = EmbeddedStorageConfigurationPropertyNames.DATA_FILE_MINIMUM_SIZE;
    protected static final String DATA_FILE_MAXIMUM_SIZE = EmbeddedStorageConfigurationPropertyNames.DATA_FILE_MAXIMUM_SIZE;
    protected static final String DATA_FILE_MINIMUM_USE_RATIO = EmbeddedStorageConfigurationPropertyNames.DATA_FILE_MINIMUM_USE_RATIO;
    protected static final String DATA_FILE_CLEANUP_HEAD_FILE = EmbeddedStorageConfigurationPropertyNames.DATA_FILE_CLEANUP_HEAD_FILE;


    public Map<String, String> convertConfigurationToMap(EclipseStoreProperties properties)
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
        configValues.values().removeIf(Objects::isNull);


        return configValues;
    }

    private Map<String, String> prepareFileSystem(StorageFilesystem properties, String key)
    {
        Map<String, String> values = new HashMap<>();
        if (properties.getSql() != null)
        {
            values.putAll(prepareSql(properties.getSql(), composeKey(key, ConfigKeys.SQL.value())));
        }
        if (properties.getAws() != null)
        {
            values.putAll(prepareAws(properties.getAws(), composeKey(key, ConfigKeys.AWS.value())));
        }
        if (properties.getAzure() != null)
        {
            values.putAll(prepareAzure(properties.getAzure(), composeKey(key, ConfigKeys.AZURE.value())));
        }
        if (properties.getHazelcast() != null)
        {
            values.put(ConfigKeys.HAZELCAST_CONFIGURATION.value(), properties.getHazelcast().getConfiguration());
        }
        if (properties.getOraclecloud() != null)
        {
            values.putAll(prepareOracleCloud(properties.getOraclecloud(), composeKey(key, ConfigKeys.ORACLECLOUD.value())));
        }
        if (properties.getOracle() != null)
        {
            values.putAll(prepareOracle(properties.getOracle(), composeKey(key, ConfigKeys.ORACLE.value())));
        }
        if (properties.getRedis() != null)
        {
            values.put(ConfigKeys.REDIS_URI.value(), properties.getRedis().getUri());
        }


        return values;
    }

    private Map<String, String> prepareOracle(Oracle oracle, String key)
    {
        Map<String, String> values = new HashMap<>();
        if (oracle.getCoherence() != null)
        {
            values.putAll(prepareCoherence(oracle.getCoherence(), composeKey(key, ConfigKeys.COHERENCE.value())));
        }
        return values;
    }

    private Map<String, String> prepareCoherence(Coherence coherence, String key)
    {
        Map<String, String> values = new HashMap<>();
        values.put(composeKey(key, ConfigKeys.COHERENCE_CACHE_NAME.value()), coherence.getCacheName());
        values.put(composeKey(key, ConfigKeys.COHERENCE_CACHE_CONFIG.value()), coherence.getCacheConfig());
        return values;
    }

    private Map<String, String> prepareOracleCloud(Oraclecloud oraclecloud, String key)
    {
        Map<String, String> values = new HashMap<>();
        values.put(composeKey(key, ConfigKeys.ORACLECLOUD_CONFIG_FILE_PATH.value()), oraclecloud.getObjectStorage().getConfigFile().getPath());
        values.put(composeKey(key, ConfigKeys.ORACLECLOUD_CONFIG_FILE_PROFILE.value()), oraclecloud.getObjectStorage().getConfigFile().getProfile());
        values.put(composeKey(key, ConfigKeys.ORACLECLOUD_CONFIG_FILE_CHARSET.value()), oraclecloud.getObjectStorage().getConfigFile().getCharset());
        values.put(composeKey(key, ConfigKeys.ORACLECLOUD_CLIENT_CONNECTION_TIMEOUT_MILLIS.value()), oraclecloud.getObjectStorage().getClient().getConnectionTimeoutMillis());
        values.put(composeKey(key, ConfigKeys.ORACLECLOUD_CLIENT_READ_TIMEOUT_MILLIS.value()), oraclecloud.getObjectStorage().getClient().getReadTimeoutMillis());
        values.put(composeKey(key, ConfigKeys.ORACLECLOUD_CLIENT_MAX_ASYNC_THREADS.value()), oraclecloud.getObjectStorage().getClient().getMaxAsyncThreads());
        values.put(composeKey(key, ConfigKeys.ORACLECLOUD_REGION.value()), oraclecloud.getObjectStorage().getRegion());
        values.put(composeKey(key, ConfigKeys.ORACLECLOUD_ENDPOINT.value()), oraclecloud.getObjectStorage().getEndpoint());
        return values;
    }


    private Map<String, String> prepareAzure(Azure azure, String key)
    {
        Map<String, String> values = new HashMap<>();
        values.put(composeKey(key, ConfigKeys.AZURE_STORAGE_CONNECTION_STRING.value()), azure.getStorage().getConnectionString());
        values.put(composeKey(key, ConfigKeys.AZURE_STORAGE_ENCRYPTION_SCOPE.value()), azure.getStorage().getEncryptionScope());
        values.put(composeKey(key, ConfigKeys.AZURE_STORAGE_CREDENTIALS_TYPE.value()), azure.getStorage().getCredentials().getType());
        values.put(composeKey(key, ConfigKeys.AZURE_STORAGE_CREDENTIALS_USERNAME.value()), azure.getStorage().getCredentials().getUsername());
        values.put(composeKey(key, ConfigKeys.AZURE_STORAGE_CREDENTIALS_PASSWORD.value()), azure.getStorage().getCredentials().getPassword());
        values.put(composeKey(key, ConfigKeys.AZURE_STORAGE_CREDENTIALS_ACCOUNT_NAME.value()), azure.getStorage().getCredentials().getAccountMame());
        values.put(composeKey(key, ConfigKeys.AZURE_STORAGE_CREDENTIALS_ACCOUNT_KEY.value()), azure.getStorage().getCredentials().getAccountKey());
        return values;
    }

    private Map<String, String> prepareAws(Aws aws, String key)
    {
        Map<String, String> values = new HashMap<>();
        if (aws.getDynamodb() != null)
        {
            values.putAll(prepareAwsProperties(aws.getDynamodb(), composeKey(key, ConfigKeys.DYNAMODB.value())));
        }
        if (aws.getS3() != null)
        {
            values.putAll(prepareAwsProperties(aws.getS3(), composeKey(key, ConfigKeys.S3.value())));
        }
        return values;
    }


    private Map<String, String> prepareAwsProperties(AbstractAwsProperties awsProperties, String key)
    {
        Map<String, String> values = new HashMap<>();
        values.put(composeKey(key, ConfigKeys.AWS_ENDPOINT_OVERRIDE.value()), awsProperties.getEndpointOverride());
        values.put(composeKey(key, ConfigKeys.AWS_REGION.value()), awsProperties.getRegion());
        values.put(composeKey(key, ConfigKeys.AWS_CREDENTIALS_TYPE.value()), awsProperties.getCredentials().getType());
        values.put(composeKey(key, ConfigKeys.AWS_CREDENTIALS_ACCESS_KEY_ID.value()), awsProperties.getCredentials().getAccessKeyId());
        values.put(composeKey(key, ConfigKeys.AWS_CREDENTIALS_SECRET_ACCESS_KEY.value()), awsProperties.getCredentials().getSecretAccessKey());
        return values;
    }


    private Map<String, String> prepareSql(Sql sql, String key)
    {
        Map<String, String> values = new HashMap<>();
        if (sql.getMariadb() != null)
        {
            values.putAll(prepareSqlBasic(sql.getMariadb(), composeKey(key, ConfigKeys.MARIADB.value())));
        }
        if (sql.getOracle() != null)
        {
            values.putAll(prepareSqlBasic(sql.getOracle(), composeKey(key, ConfigKeys.ORACLE.value())));
        }
        if (sql.getPostgres() != null)
        {
            values.putAll(prepareSqlBasic(sql.getPostgres(), composeKey(key, ConfigKeys.POSTGRES.value())));
        }
        if (sql.getSqlite() != null)
        {
            values.putAll(prepareSqlBasic(sql.getSqlite(), composeKey(key, ConfigKeys.SQLITE.value())));
        }

        return values;
    }

    private Map<String, String> prepareSqlBasic(AbstractSqlConfiguration properties, String key)
    {
        Map<String, String> values = new HashMap<>();
        values.put(composeKey(key, ConfigKeys.SQL_DATA_SOURCE_PROVIDER.value()), properties.getDataSourceProvider());
        values.put(composeKey(key, ConfigKeys.SQL_CATALOG.value()), properties.getCatalog());
        values.put(composeKey(key, ConfigKeys.SQL_SCHEMA.value()), properties.getSchema());
        values.put(composeKey(key, ConfigKeys.SQL_URL.value()), properties.getUrl());
        values.put(composeKey(key, ConfigKeys.SQL_USER.value()), properties.getUser());
        values.put(composeKey(key, ConfigKeys.SQL_PASSWORD.value()), properties.getPassword());
        return values;
    }

    protected String composeKey(String prefix, String suffix)
    {
        return prefix + "." + suffix;
    }

}
