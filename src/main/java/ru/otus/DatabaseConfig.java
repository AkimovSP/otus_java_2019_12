package ru.otus;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.otus.core.model.User;
import ru.otus.hibernate.HibernateUtils;

@Configuration
@ComponentScan
@EnableWebMvc
public class DatabaseConfig implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;

    public DatabaseConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public SessionFactory sessionFactory() {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml",
                User.class);
        return sessionFactory;
    }
}
