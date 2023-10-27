package org.eclipse.store.integrations.spring.boot.types.configuration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@AutoConfiguration
@ComponentScan(basePackages = "org.eclipse.store.integrations.spring.boot")
@EnableConfigurationProperties(EclipseStoreConfigurationProperties.class)
public class EclipseStoreSpringBootIntegration
{
}
