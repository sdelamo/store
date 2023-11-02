package org.eclipse.store.integrations.spring.boot.types;

public enum ConfigKeys
{


    //storage-filesystem
    SQL("sql"),
    MARIADB("mariadb"),
    ORACLE("oracle"),
    POSTGRES("postgres"),
    SQLITE("sqlite"),


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
