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

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Aspect
@Component
public class LockAspect
{
    private final ReentrantReadWriteLock globalLock = new ReentrantReadWriteLock();

    private final Map<String, ReentrantReadWriteLock> locks = new HashMap<>();

    @Around("@annotation(org.eclipse.store.integrations.spring.boot.types.concurent.Read)")
    public Object readOperation(ProceedingJoinPoint joinPoint) throws Throwable
    {
        ReentrantReadWriteLock lock = this.findLock(joinPoint);
        lock.readLock().lock();
        System.out.println("read lock");
        Object proceed;
        try
        {
            proceed = joinPoint.proceed();
        } finally
        {
            System.out.println("read unlock");
            lock.readLock().unlock();
        }
        return proceed;
    }

    @Around("@annotation(org.eclipse.store.integrations.spring.boot.types.concurent.Write)")
    public Object writeOperation(ProceedingJoinPoint joinPoint) throws Throwable
    {
        ReentrantReadWriteLock lock = this.findLock(joinPoint);
        lock.readLock().lock();

        System.out.println("write lock");
        Object proceed;
        try
        {
            proceed = joinPoint.proceed();
        } finally
        {
            System.out.println("write unlock");
            lock.writeLock().unlock();
        }
        return proceed;
    }

    private ReentrantReadWriteLock findLock(ProceedingJoinPoint joinPoint)
    {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        //method annotation first
        ReentrantReadWriteLock finalLock;
        Lockable annotation = method.getAnnotation(Lockable.class);
        if (annotation != null)
        {
            String lockName = annotation.value();
            finalLock = processLockByName(lockName);
        } else
        {
            //class annotation second
            Class<?> declaringClass = method.getDeclaringClass();
            Lockable classAnnotation = declaringClass.getAnnotation(Lockable.class);
            if (classAnnotation != null)
            {
                String classLockName = classAnnotation.value();
                finalLock = processLockByName(classLockName);
            } else
            {
                // no annotation, use global lock
                finalLock = globalLock;
            }
        }
        return finalLock;
    }

    private ReentrantReadWriteLock processLockByName(String lockName)
    {
        ReentrantReadWriteLock newLock;

        if (locks.containsKey(lockName))
        {
            newLock = locks.get(lockName);
        } else
        {
            newLock = new ReentrantReadWriteLock();
            locks.put(lockName, newLock);
        }
        return newLock;
    }

}
