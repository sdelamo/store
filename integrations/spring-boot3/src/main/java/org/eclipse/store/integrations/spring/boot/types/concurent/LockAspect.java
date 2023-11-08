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
import org.eclipse.serializer.util.logging.Logging;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Aspect
@Component
public class LockAspect
{

    private final Logger logger = Logging.getLogger(LockAspect.class);

    private final ReentrantReadWriteLock globalLock = new ReentrantReadWriteLock();

    private final Map<String, ReentrantReadWriteLock> locks = new HashMap<>();

    @Around("@annotation(Read)")
    public Object readOperation(ProceedingJoinPoint joinPoint) throws Throwable
    {
        ReentrantReadWriteLock lock = this.findLock(joinPoint);
        lock.readLock().lock();
        logger.trace("lock readLock");
        Object proceed;
        try
        {
            proceed = joinPoint.proceed();
        } finally
        {
            lock.readLock().unlock();
            logger.trace("unlock readLock");
        }
        return proceed;
    }

    @Around("@annotation(Write)")
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
            lock.writeLock().unlock();
            System.out.println("write unlock");
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
            logger.trace("Found method lock annotation for lock: {}", lockName);
            finalLock = getOrCreateLock(lockName);
        } else
        {
            //class annotation second
            Class<?> declaringClass = method.getDeclaringClass();
            Lockable classAnnotation = declaringClass.getAnnotation(Lockable.class);
            if (classAnnotation != null)
            {
                String classLockName = classAnnotation.value();
                logger.trace("Found class lock annotation for lock: {}", classLockName);
                finalLock = getOrCreateLock(classLockName);
            } else
            {
                // no annotation, use global lock
                logger.trace("Found no @Lockable annotation, use global lock");
                finalLock = globalLock;
            }
        }
        return finalLock;
    }

    private ReentrantReadWriteLock getOrCreateLock(String lockName)
    {
        ReentrantReadWriteLock lock;

        if (locks.containsKey(lockName))
        {
            logger.trace("Lock for name: {} found, will be used", lockName);
            lock = locks.get(lockName);
        } else
        {
            logger.trace("Lock for name: {} not found, create new one ", lockName);
            lock = new ReentrantReadWriteLock();
            locks.put(lockName, lock);
        }
        return lock;
    }

}
