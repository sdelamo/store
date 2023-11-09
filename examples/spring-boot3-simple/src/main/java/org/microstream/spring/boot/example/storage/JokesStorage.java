package org.microstream.spring.boot.example.storage;

import java.util.List;

public interface JokesStorage
{
    String oneJoke(Integer id);

    List<String> allJokes();

    Integer addNewJoke(String joke);

    void addJokes(List<String> jokes);
}
