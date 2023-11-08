package org.microstream.spring.boot.example.storage;

/*-
 * #%L
 * spring-boot3-simple
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

import org.eclipse.store.integrations.spring.boot.types.concurent.Lockable;
import org.eclipse.store.integrations.spring.boot.types.concurent.Read;
import org.eclipse.store.integrations.spring.boot.types.concurent.Write;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.microstream.spring.boot.example.model.Root;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;


@Component
@Lockable("myGreatLock")
public class JokesStorage
{
    private final EmbeddedStorageManager storageManager;

    public JokesStorage(EmbeddedStorageManager storageManager)
    {
        this.storageManager = storageManager;
    }

    ReentrantReadWriteLock myGreatLock = new ReentrantReadWriteLock();

    @Read
    public String oneJoke(Integer id)
    {
        String joke;
        Root root = (Root) storageManager.root();
        if (id > root.getJokes().size())
        {
            throw new IllegalArgumentException("No jokes with this id");
        }
        joke = root.getJokes().get(id);
        return joke;
    }

    @Read
    public List<String> allJokes()
    {
        Root root = (Root) storageManager.root();
        return root.getJokes();
    }

    @Write
    public Integer addNewJoke(String joke)
    {
        Root root = (Root) storageManager.root();
        root.getJokes().add(joke);
        storageManager.storeRoot();
        return root.getJokes().size();
    }

    @Write
    public void addJokes(List<String> jokes)
    {
        Root root = (Root) storageManager.root();
        root.getJokes().addAll(jokes);
        storageManager.store(root.getJokes());
    }
}
