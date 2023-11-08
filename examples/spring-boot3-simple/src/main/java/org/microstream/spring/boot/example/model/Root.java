package org.microstream.spring.boot.example.model;

import java.util.ArrayList;
import java.util.List;

public class Root
{
    private final List<String> jokes = new ArrayList<>();

    public List<String> getJokes()
    {
        return jokes;
    }
}
