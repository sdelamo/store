package org.eclipse.store.integrations.spring.boot.types.concurent;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Mutex
{
    String value();
}
