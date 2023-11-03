package org.eclipse.store.integrations.spring.boot.types;

import org.eclipse.store.integrations.spring.boot.types.configuration.ConfigurationValues;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application-test.properties")
@EnableConfigurationProperties(ConfigurationValues.class)
public class TestPropertiesConfig
{
}
