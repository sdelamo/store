package org.eclipse.store.integrations.spring.boot.types;

/*-
 * #%L
 * spring-boot3
 * %%
 * Copyright (C) 2023 MicroStream Software
 * %%
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * #L%
 */

import org.eclipse.serializer.util.logging.Logging;
import org.eclipse.store.integrations.spring.boot.types.configuration.ConfigurationPair;
import org.eclipse.store.integrations.spring.boot.types.configuration.EclipseStoreProperties;
import org.eclipse.store.integrations.spring.boot.types.converter.EclipseStoreConfigConverter;
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
    public EmbeddedStorageManager createStorage(EclipseStoreProperties eclipseStoreProperties, ConfigurationPair... additionalConfiguration)
    {
        EmbeddedStorageFoundation<?> storageFoundation = createStorageFoundation(eclipseStoreProperties, additionalConfiguration);
        return createStorage(storageFoundation, eclipseStoreProperties.getAutoStart());
    }

    @Override
    public EmbeddedStorageManager createStorage(EmbeddedStorageFoundation<?> foundation, boolean autoStart)
    {
        EmbeddedStorageManager storageManager = foundation.createEmbeddedStorageManager();
        if (autoStart)
        {
            storageManager.start();
        }
        return storageManager;
    }

    @Override
    public EmbeddedStorageFoundation<?> createStorageFoundation(EclipseStoreProperties eclipseStoreProperties, ConfigurationPair... additionalConfiguration)
    {

        final EmbeddedStorageConfigurationBuilder builder = EmbeddedStorageConfigurationBuilder.New();
        Map<String, String> valueMap = converter.convertConfigurationToMap(eclipseStoreProperties);
        for (ConfigurationPair pair : additionalConfiguration) {
            valueMap.put(pair.key(), pair.value());
        }

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

        Object o = provideRoot(eclipseStoreProperties);
        if (o != null)
        {
            storageFoundation.setRoot(o);
        }
        storageFoundation.getConnectionFoundation()
                .setClassLoaderProvider(typeName -> this.applicationContext.getClassLoader());

        return storageFoundation;
    }

    private Object provideRoot(EclipseStoreProperties eclipseStoreProperties)
    {
        Object o = null;
        if (eclipseStoreProperties.getRoot() != null)
        {
            try
            {
                o = eclipseStoreProperties.getRoot().getDeclaredConstructor().newInstance();
            } catch (Exception e)
            {
                throw new RuntimeException("Class :" + eclipseStoreProperties.getRoot() + " could not be instantiated." + e);
            }
        }
        return o;
    }

}
