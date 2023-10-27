package org.eclipse.store.integrations.spring.boot.types.configuration.oracle;

/*-
 * #%L
 * microstream-integrations-spring-boot3
 * %%
 * Copyright (C) 2019 - 2023 MicroStream Software
 * %%
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * #L%
 */

import org.eclipse.store.integrations.spring.boot.types.configuration.oracle.coherence.Coherence;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

public class Oracle
{

    @NestedConfigurationProperty
    private Coherence coherence;

    public Coherence getCoherence()
    {
        return coherence;
    }

    public void setCoherence(Coherence coherence)
    {
        this.coherence = coherence;
    }

}
