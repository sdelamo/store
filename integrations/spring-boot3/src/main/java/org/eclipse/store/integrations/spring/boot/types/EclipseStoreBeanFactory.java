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

import org.eclipse.store.integrations.spring.boot.types.configuration.ConfigurationValues;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class EclipseStoreBeanFactory
{
    private final EclipseStoreProvider eclipseStoreProvider;
    private final EclipseStoreProperties eclipseStoreProperties;

    public EclipseStoreBeanFactory(EclipseStoreProvider eclipseStoreProvider, EclipseStoreProperties eclipseStoreProperties)
    {
        this.eclipseStoreProvider = eclipseStoreProvider;
        this.eclipseStoreProperties = eclipseStoreProperties;
    }


    @Bean
    @Primary
    @Lazy
    public EmbeddedStorageManager embeddedStorageManager()
    {
        return eclipseStoreProvider.createStorage(eclipseStoreProperties);
    }


}
