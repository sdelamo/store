package org.eclipse.store.integrations.spring.boot.types;

import org.eclipse.store.integrations.spring.boot.types.configuration.ConfigurationValues;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class EclipseStoreBeanFactory
{
    private final EclipseStoreProvider eclipseStoreProvider;
    private final ConfigurationValues configurationValues;

    public EclipseStoreBeanFactory(EclipseStoreProvider eclipseStoreProvider, ConfigurationValues configurationValues)
    {
        this.eclipseStoreProvider = eclipseStoreProvider;
        this.configurationValues = configurationValues;
    }


    @Bean
    @Lazy
    public EmbeddedStorageManager embeddedStorageManager()
    {
        return eclipseStoreProvider.createStorage(configurationValues);
    }






}
