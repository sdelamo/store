package org.microstream.spring.boot.example.service;

import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.microstream.spring.boot.example.model.Root;
import org.microstream.spring.boot.example.storage.JokesStorage;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

@Service
public class JokesServices
{
    private final JokesStorage jokesStorage;


    public String oneJoke(Integer id) {
        String joke;
        joke = jokesStorage.oneJoke(Objects.requireNonNullElse(id, 0));
        return joke;
    }

    public JokesServices(JokesStorage jokesStorage)
    {
        this.jokesStorage = jokesStorage;
    }

    public List<String> allJokes()
    {
        return jokesStorage.allJokes();
    }

    public Integer addNewJoke(String joke)
    {
        return jokesStorage.addNewJoke(joke);
    }

    public void loadPredefiniedJokes() {
        List<String> jokes = null;
        try
        {
            File file = ResourceUtils.getFile("classpath:jokes.txt");
            jokes = Files.readAllLines(file.toPath());
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
            List<String> existingJokes = jokesStorage.allJokes();
            if (existingJokes.containsAll(jokes)) {
                return;
        }
        jokesStorage.addJokes(jokes);
    }

}
