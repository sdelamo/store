package org.microstream.spring.boot.example;

import org.eclipse.store.integrations.spring.boot.types.EclipseStoreSpringBoot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(EclipseStoreSpringBoot.class)
public class JokesApplication
{
    public static void main(String... args)
    {
        SpringApplication.run(JokesApplication.class, args);
    }
}
