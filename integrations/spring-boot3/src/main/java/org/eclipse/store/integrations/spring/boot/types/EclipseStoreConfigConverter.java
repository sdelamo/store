package org.eclipse.store.integrations.spring.boot.types;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.eclipse.store.integrations.spring.boot.types.configuration.EclipseStoreConfigurationProperties;

public class EclipseStoreConfigConverter
{

    public Map<String, String> convertConfigurationToMap(EclipseStoreConfigurationProperties properties) {

        Map<String, String> configValues = new HashMap<>();
        configValues.put(ConfigKeys.STORAGE_DIRECTORY.getValue(), properties.getStorageDirectory());

        //configValues.put(ConfigKeys.STORAGE_FILESYSTEM.getValue(), properties.getStorageFilesystem()) TODO

        configValues.put(ConfigKeys.DELETION_DIRECTORY.getValue(), properties.getDeletionDirectory());
        configValues.put(ConfigKeys.TRUNCATION_DIRECTORY.getValue(), properties.getTruncationDirectory());
        configValues.put(ConfigKeys.BACKUP_DIRECTORY.getValue(),  properties.getBackupDirectory());

        //configValues.put(ConfigKeys.BACKUP_FILESYSTEM.getValue(), properties.getBACKUP_FILESYSTEM()) TODO

        configValues.put(ConfigKeys.CHANNEL_COUNT.getValue(), properties.getChannelCount());
        configValues.put(ConfigKeys.CHANNEL_DIRECTORY_PREFIX.getValue(), properties.getChannelDirectoryPrefix());
        configValues.put(ConfigKeys.DATA_FILE_PREFIX.getValue(), properties.getDataFilePrefix());
        configValues.put(ConfigKeys.DATA_FILE_SUFFIX.getValue(), properties.getDataFileSuffix());
        configValues.put(ConfigKeys.TRANSACTION_FILE_PREFIX.getValue(), properties.getTransactionFilePrefix());
        configValues.put(ConfigKeys.TRANSACTION_FILE_SUFFIX.getValue(), properties.getTransactionFileSuffix());
        configValues.put(ConfigKeys.TYPE_DICTIONARY_FILE_NAME.getValue(), properties.getTypeDictionaryFileName());
        configValues.put(ConfigKeys.RESCUED_FILE_SUFFIX.getValue(), properties.getRescuedFileSuffix());
        configValues.put(ConfigKeys.LOCK_FILE_NAME.getValue(), properties.getLockFileName());
        configValues.put(ConfigKeys.HOUSEKEEPING_INTERVAL.getValue(), properties.getHousekeepingInterval());
        configValues.put(ConfigKeys.HOUSEKEEPING_TIME_BUDGET.getValue(), properties.getHousekeepingTimeBudget());
        configValues.put(ConfigKeys.ENTITY_CACHE_THRESHOLD.getValue(), properties.getEntityCacheThreshold());
        configValues.put(ConfigKeys.ENTITY_CACHE_TIMEOUT.getValue(), properties.getEntityCacheTimeout());
        configValues.put(ConfigKeys.DATA_FILE_MINIMUM_SIZE.getValue(), properties.getDataFileMinimumSize());
        configValues.put(ConfigKeys.DATA_FILE_MAXIMUM_SIZE.getValue(), properties.getDataFileMaximumSize());
        configValues.put(ConfigKeys.DATA_FILE_MINIMUM_USE_RATIO.getValue(), properties.getDataFileMinimumUseRatio());
        configValues.put(ConfigKeys.DATA_FILE_CLEANUP_HEAD_FILE.getValue(), properties.getDataFileCleanupHeadFile());


        //remove keys with null value
        configValues.values().removeIf(Objects::isNull);


        return configValues;
    }
}
