package ru.otus;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.otus.core.model.Card;
import ru.otus.core.model.MyATMImpl;
import ru.otus.core.model.MyCashCellImpl;
import ru.otus.hibernate.HibernateUtils;

public class DatabaseConfig implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;

    public DatabaseConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public SessionFactory sessionFactory() {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml",
                new Class[]{  MyCashCellImpl.class, Card.class , MyATMImpl.class});
        return sessionFactory;
    }
}
