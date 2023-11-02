package org.eclipse.store.integrations.spring.boot.types;

public enum ConfigKeys
{

    STORAGE_DIRECTORY("storage-directory"),
    STORAGE_FILESYSTEM("storage-filesystem"),
    DELETION_DIRECTORY("deletion-directory"),
    TRUNCATION_DIRECTORY("truncation-directory"),
    BACKUP_DIRECTORY("backup-directory"),
    BACKUP_FILESYSTEM("backup-filesystem"),
    CHANNEL_COUNT("channel-count"),
    CHANNEL_DIRECTORY_PREFIX("channel-directory-prefix"),
    DATA_FILE_PREFIX("data-file-prefix"),
    DATA_FILE_SUFFIX("data-file-suffix"),
    TRANSACTION_FILE_PREFIX("transaction-file-prefix"),
    TRANSACTION_FILE_SUFFIX("transaction-file-suffix"),
    TYPE_DICTIONARY_FILE_NAME("type-dictionary-file-name"),
    RESCUED_FILE_SUFFIX("rescued-file-suffix"),
    LOCK_FILE_NAME("lock-file-name"),
    HOUSEKEEPING_INTERVAL("housekeeping-interval"),
    HOUSEKEEPING_TIME_BUDGET("housekeeping-time-budget"),
    ENTITY_CACHE_THRESHOLD("entity-cache-threshold"),
    ENTITY_CACHE_TIMEOUT("entity-cache-timeout"),
    DATA_FILE_MINIMUM_SIZE("data-file-minimum-size"),
    DATA_FILE_MAXIMUM_SIZE("data-file-maximum-size"),
    DATA_FILE_MINIMUM_USE_RATIO("data-file-minimum-use-ratio"),
    DATA_FILE_CLEANUP_HEAD_FILE("data-file-cleanup-head-file")
    ;

    private String value;

    ConfigKeys(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }
}
