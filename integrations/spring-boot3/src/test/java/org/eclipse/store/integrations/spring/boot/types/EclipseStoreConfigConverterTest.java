package org.eclipse.store.integrations.spring.boot.types;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;

import org.eclipse.store.integrations.spring.boot.types.configuration.EclipseStoreConfigurationProperties;
import org.junit.jupiter.api.Test;

class EclipseStoreConfigConverterTest
{

    @Test
    void testBasicConvertion()
    {
        EclipseStoreConfigurationProperties properties = new EclipseStoreConfigurationProperties();
        properties.setChannelCount("4");

        EclipseStoreConfigConverter converter = new EclipseStoreConfigConverter();
        Map<String, String> stringStringMap = converter.convertConfigurationToMap(properties);

        assertNotNull(stringStringMap);
        assertEquals(1, stringStringMap.size());
    }
}
