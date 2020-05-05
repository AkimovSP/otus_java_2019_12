package ru.otus.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.servlet.annotation.HttpConstraint;
import java.beans.BeanProperty;
import java.util.Arrays;

public class HibernateUtils {

  private HibernateUtils() {
  }

  public static SessionFactory buildSessionFactory(String configResourceFileName,
                                                   Class<?>... annotatedClasses) {
    Configuration configuration = new Configuration().configure(configResourceFileName);
    MetadataSources metadataSources = new MetadataSources(createServiceRegistry(configuration));
    Arrays.stream(annotatedClasses).forEach(metadataSources::addAnnotatedClass);

    Metadata metadata = metadataSources.getMetadataBuilder().build();
    return metadata.getSessionFactoryBuilder().build();
  }

  private static StandardServiceRegistry createServiceRegistry(Configuration configuration) {
    return new StandardServiceRegistryBuilder()
        .applySettings(configuration.getProperties()).build();
  }
}
