package org.eclipse.store.integrations.spring.boot.types.root;

import org.eclipse.store.integrations.spring.boot.types.EclipseStoreSpringBoot;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestPropertySource("classpath:application-autostart-root.properties")
@SpringBootTest(classes = {EclipseStoreSpringBoot.class})
public class AutostartRootTest
{

    @Test
    void name(@Autowired EmbeddedStorageManager storage)
    {
        assertTrue(storage.isRunning() || storage.isStartingUp());

        Object o = storage.root();
        assertTrue(o instanceof Root);
    }
}
