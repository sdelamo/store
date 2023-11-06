package org.eclipse.store.integrations.spring.boot.types;

import org.eclipse.store.integrations.spring.boot.types.configuration.ConfigurationValues;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageFoundation;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;

public interface EclipseStoreProvider
{

    EmbeddedStorageManager createStorage(ConfigurationValues configurationValues);

    EmbeddedStorageManager createStorage(EmbeddedStorageFoundation<?> foundation);

    EmbeddedStorageFoundation<?> createStorageFoundation(ConfigurationValues configurationValues);


}
