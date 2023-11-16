package org.microstream.spring.boot.example.controller;

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

import org.microstream.spring.boot.example.service.JokesServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class JokesController
{

    private final JokesServices jokesServices;

    public JokesController(JokesServices jokesServices)
    {
        this.jokesServices = jokesServices;
    }

    @GetMapping
    public List<String> getAll()
    {
        return jokesServices.allJokes();
    }

    @GetMapping("/joke")
    public String getOneJoke(@RequestParam(required = false) Integer id)
    {
        return jokesServices.oneJoke(id);
    }

    @PostMapping("/add")
    public Integer putOne(@RequestBody String joke)
    {
        return jokesServices.addNewJoke(joke);
    }

    @PostMapping("/init")
    public void init()
    {
        jokesServices.loadPredefinedJokes();
    }

    /**
     * This method functions similarly to the 'putOne' method. However, it differs in its implementation by utilizing a nested locking mechanism.
     * The '@Write Lock' annotation is applied at the service layer, invoking the storage layer within another locking annotation.
     * This implementation is solely intended for demonstration purposes.
     *
     * @param joke The new joke to be added.
     * @return The position of the currently inserted joke in storage.
     */
    @PostMapping("/insert")
    public Integer insert(@RequestBody String joke)
    {
        return jokesServices.insertNewJoke(joke);
    }
}
