package org.microstream.spring.boot.example.storage;

import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.microstream.spring.boot.example.model.Root;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.List;


@Component
public class JokesStorage
{
    private final EmbeddedStorageManager storageManager;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public JokesStorage(EmbeddedStorageManager storageManager)
    {
        this.storageManager = storageManager;
    }

    public String oneJoke(Integer id) {
        String joke;
        lock.readLock().lock();
        try
        {
            Root root = (Root) storageManager.root();
            if (id > root.getJokes().size()) {
                throw new IllegalArgumentException("No jokes with this id");
            }
            joke = root.getJokes().get(id);

        }
        finally
        {
            lock.readLock().unlock();
        }
        return joke;
    }

    public List<String> allJokes()
    {
        List<String> jokes = null;
        lock.readLock().lock();
        try
        {
            Root root = (Root) storageManager.root();
            jokes = root.getJokes();
        } finally
        {
            lock.readLock().unlock();
        }
        return jokes;
    }

    public Integer addNewJoke(String joke)
    {
        lock.writeLock().lock();
        Root root = (Root) storageManager.root();
        try
        {
            root.getJokes().add(joke);
            storageManager.storeRoot();
        } finally
        {
            lock.writeLock().unlock();
        }
        return root.getJokes().size();
    }

    public void addJokes(List<String> jokes)
    {
        lock.writeLock().lock();
        Root root = (Root) storageManager.root();
        try
        {
            root.getJokes().addAll(jokes);
        } finally
        {
            lock.writeLock().unlock();
        }
        storageManager.store(root.getJokes());
    }
}
