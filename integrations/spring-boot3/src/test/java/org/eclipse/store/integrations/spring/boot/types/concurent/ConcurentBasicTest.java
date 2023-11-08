package org.eclipse.store.integrations.spring.boot.types.concurent;

/*-
 * #%L
 * spring-boot3
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

import org.eclipse.store.integrations.spring.boot.types.EclipseStoreSpringBoot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.locks.Lock;


@SpringBootTest(classes = {EclipseStoreSpringBoot.class, ConcurentBasicTest.class, LockAspect.class, AnnotationAwareAspectJAutoProxyCreator.class})
@Import(AnnotationAwareAspectJAutoProxyCreator.class)
public class ConcurentBasicTest
{

    @Test
    void name()
    {
        doSomethingRead();
        doSomethingWrite();
    }


    @Read
    private void doSomethingRead()
    {
        System.out.println("read");
    }

    @Write
    private void doSomethingWrite()
    {
        System.out.println("write");
    }
}
