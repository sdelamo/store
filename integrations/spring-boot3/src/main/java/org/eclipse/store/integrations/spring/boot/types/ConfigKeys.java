package org.eclipse.store.integrations.spring.boot.types;

public enum ConfigKeys
{

    //storage-filesystem
    SQL("sql"),
    MARIADB("mariadb"),
    ORACLE("oracle"),
    POSTGRES("postgres"),
    SQLITE("sqlite"),

    //aws
    AWS("aws"),
    DYNAMODB("dynamodb"),
    S3("s3"),
    AWS_CREDENTIALS_TYPE("credentials.type"),
    AWS_CREDENTIALS_ACCESS_KEY_ID("credentials.access-key-id"),
    AWS_CREDENTIALS_SECRET_ACCESS_KEY("credentials.secret-access-key"),
    AWS_ENDPOINT_OVERRIDE("endpointOverride"),
    AWS_REGION("region"),

    //azure
    AZURE("azure"),
    AZURE_STORAGE_ENDPOINT("storage.endpoint"),
    AZURE_STORAGE_CONNECTION_STRING("storage.connection-string"),
    AZURE_STORAGE_ENCRYPTION_SCOPE("storage.encryption-scope"),
    AZURE_STORAGE_CREDENTIALS_TYPE("storage.credentials.type"),
    AZURE_STORAGE_CREDENTIALS_USERNAME("storage.credentials.username"),
    AZURE_STORAGE_CREDENTIALS_PASSWORD("storage.credentials.password"),
    AZURE_STORAGE_CREDENTIALS_ACCOUNT_NAME("storage.credentials.account-name"),
    AZURE_STORAGE_CREDENTIALS_ACCOUNT_KEY("storage.credentials.account-key"),


    //sql
    SQL_DATA_SOURCE_PROVIDER("data-source-provider"),
    SQL_CATALOG("catalog"),
    SQL_SCHEMA("schema"),
    SQL_URL("url"),
    SQL_USER("user"),
    SQL_PASSWORD("password")
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
