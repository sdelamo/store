package org.eclipse.store.integrations.spring.boot.types;

import org.eclipse.serializer.util.logging.Logging;
import org.eclipse.store.integrations.spring.boot.types.configuration.ConfigurationValues;
import org.eclipse.store.storage.embedded.configuration.types.EmbeddedStorageConfigurationBuilder;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageFoundation;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EclipseStoreProviderImpl implements EclipseStoreProvider
{
    private final EclipseStoreConfigConverter converter;
    private final ApplicationContext applicationContext;

    private final Logger logger = Logging.getLogger(EclipseStoreProviderImpl.class);

    public EclipseStoreProviderImpl(EclipseStoreConfigConverter converter, ApplicationContext applicationContext)
    {
        this.converter = converter;
        this.applicationContext = applicationContext;
    }

    @Override
    public EmbeddedStorageManager createStorage(ConfigurationValues configurationValues)
    {
        EmbeddedStorageFoundation<?> storageFoundation = createStorageFoundation(configurationValues);
        return createStorage(storageFoundation, configurationValues);
    }

    @Override
    public EmbeddedStorageManager createStorage(EmbeddedStorageFoundation<?> foundation, ConfigurationValues configurationValues)
    {
        EmbeddedStorageManager storageManager = foundation.createEmbeddedStorageManager();
        if (configurationValues.getAutoStart() != null && configurationValues.getAutoStart())
        {
            storageManager.start();
        }
        return storageManager;
    }

    @Override
    public EmbeddedStorageFoundation<?> createStorageFoundation(ConfigurationValues configurationValues)
    {
        final EmbeddedStorageConfigurationBuilder builder = EmbeddedStorageConfigurationBuilder.New();
        Map<String, String> valueMap = converter.convertConfigurationToMap(configurationValues);

        this.logger.debug("MicroStream configuration items: ");
        valueMap.forEach((key, value) ->
        {
            if (value != null)
            {
                String logValue = key.contains("password") ? "xxxxxx" : value;
                this.logger.debug(key + " : " + logValue);
                builder.set(key, value);
            }
        });

        EmbeddedStorageFoundation<?> storageFoundation = builder.createEmbeddedStorageFoundation();

        Object o = provideRoot(configurationValues);
        if (o != null)
        {
            storageFoundation.setRoot(o);
        }
        storageFoundation.getConnectionFoundation()
                .setClassLoaderProvider(typeName -> this.applicationContext.getClassLoader());

        return storageFoundation;
    }

    private Object provideRoot(ConfigurationValues configurationValues)
    {
        Object o = null;
        if (configurationValues.getRoot() != null)
        {
            try
            {
                o = Class.forName(configurationValues.getRoot()).getDeclaredConstructor().newInstance();
            } catch (Exception e)
            {
                throw new RuntimeException("Class :" + configurationValues.getRoot() + " could not be instantiated." + e);
            }
        }
        return o;
    }

}
