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
open module spring.boot3.simple {
    requires transitive spring.context;
    requires transitive spring.boot.autoconfigure;
    requires transitive spring.boot;
    requires transitive org.eclipse.store.integrations.spring.boot;
    requires transitive spring.web;

}
