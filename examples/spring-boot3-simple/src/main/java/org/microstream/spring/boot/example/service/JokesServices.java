package org.microstream.spring.boot.example.service;

import java.util.List;

public interface JokesServices
{
    String oneJoke(Integer id);

    List<String> allJokes();

    Integer addNewJoke(String joke);

    void loadPredefinedJokes();
}
