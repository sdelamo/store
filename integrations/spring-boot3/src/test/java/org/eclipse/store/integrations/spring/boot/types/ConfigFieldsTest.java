package org.eclipse.store.integrations.spring.boot.types;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.store.storage.embedded.configuration.types.EmbeddedStorageConfigurationPropertyNames;
import org.junit.jupiter.api.Test;

public class ConfigFieldsTest
{

    @Test
    void name()
    {

        Class<?> embeddedStorageConfigurationPropertyNames = EmbeddedStorageConfigurationPropertyNames.class;
        Field[] originalFields = embeddedStorageConfigurationPropertyNames.getDeclaredFields();

        EclipseStoreConfigConverter converter = new EclipseStoreConfigConverter();
        Field[] converterFields = converter.getClass()
                .getDeclaredFields();

        List<String> originalFieldNames = Arrays.stream(originalFields)
                .map(Field::getName)
                .collect(Collectors.toList());

        List<String> converterFieldsNames = Arrays.stream(converterFields)
                .map(Field::getName)
                .collect(Collectors.toList());

        assertTrue(converterFieldsNames.containsAll(originalFieldNames));
    }
}
