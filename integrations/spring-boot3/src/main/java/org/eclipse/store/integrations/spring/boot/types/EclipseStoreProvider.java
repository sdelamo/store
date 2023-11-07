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

import org.eclipse.store.integrations.spring.boot.types.configuration.EclipseStoreProperties;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageFoundation;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;

public interface EclipseStoreProvider
{

    EmbeddedStorageManager createStorage(EclipseStoreProperties eclipseStoreProperties);

    EmbeddedStorageManager createStorage(EmbeddedStorageFoundation<?> foundation,  Boolean autoStart);

    EmbeddedStorageFoundation<?> createStorageFoundation(EclipseStoreProperties eclipseStoreProperties);


}
