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

import org.eclipse.store.integrations.spring.boot.types.configuration.ConfigurationPair;
import org.eclipse.store.integrations.spring.boot.types.configuration.EclipseStoreProperties;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageFoundation;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;

import java.util.Map;

public interface EclipseStoreProvider
{
    /**
     * Creates an EmbeddedStorageManager using the provided configuration.
     *
     * @param eclipseStoreProperties Configuration file structure representing configuration elements mapped by Spring Configuration.
     * @param additionalConfiguration Optional additional parameters that allow the inclusion of configuration keys not present in EclipseStoreProperties.
     * @return A new EmbeddedStorageManager instance based on the provided configuration.
     */
    EmbeddedStorageManager createStorage(EclipseStoreProperties eclipseStoreProperties, ConfigurationPair... additionalConfiguration);

    /**
     * Creates an EmbeddedStorageManager using a pre-configured foundation. This method is beneficial when additional configuration for the foundation is required.
     *
     * @param foundation The EmbeddedStorageFoundation to be configured before calling this method.
     * @param autoStart Determines whether the newly created EmbeddedStorageManager should start directly after creation.
     * @return A new EmbeddedStorageManager instance based on the provided EmbeddedStorageFoundation.
     */
    EmbeddedStorageManager createStorage(EmbeddedStorageFoundation<?> foundation, boolean autoStart);


    /**
     * Creates an EmbeddedStorageFoundation using the provided configuration. This method should be called when the additional configuration for the foundation is required.
     *
     * @param eclipseStoreProperties Configuration file structure representing configuration elements mapped by Spring Configuration.
     * @param additionalConfiguration Optional additional parameters that allow the inclusion of configuration keys not present in EclipseStoreProperties.
     * @return A new EmbeddedStorageFoundation instance based on the provided configuration.
     */
    EmbeddedStorageFoundation<?> createStorageFoundation(EclipseStoreProperties eclipseStoreProperties, ConfigurationPair... additionalConfiguration);


}
