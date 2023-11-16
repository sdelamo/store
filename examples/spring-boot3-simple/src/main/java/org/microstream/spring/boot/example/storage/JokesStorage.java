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

import java.util.List;

public interface JokesStorage
{
    String oneJoke(Integer id);

    List<String> allJokes();

    Integer addNewJoke(String joke);

    void addJokes(List<String> jokes);

    Integer saveAllJokes(List<String> jokes);
}
