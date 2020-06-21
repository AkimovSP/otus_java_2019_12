package ru.otus.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.hibernate.sessionmanager.DatabaseSessionHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.core.model.*;
import ru.otus.core.dao.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Repository
public class CardDaoHibernate implements CardDao {
    private static Logger logger = LoggerFactory.getLogger(CardDaoHibernate.class);

    private final SessionManagerHibernate sessionManager;

    public CardDaoHibernate(SessionManagerHibernate sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public Optional<Card> findById(long id) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            return Optional.ofNullable(currentSession.getHibernateSession().find(Card.class, id));
        } catch (Exception e) {
        }
        return Optional.empty();
    }

    @Override
    public Optional<Card> findByCardNumber(String cardNumber) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session session = currentSession.getHibernateSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Card> cr = builder.createQuery(Card.class);
            Root<Card> root = cr.from(Card.class);
            cr.select(root).where(builder.equal(root.get("cardNumber"), cardNumber));
            Query<Card> query = session.createQuery(cr);
            Card card = query.getSingleResult();
            return Optional.ofNullable(card);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public long saveCard(Card card) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            if (card.getId() > 0) {
                hibernateSession.merge(card);
            } else {
                hibernateSession.persist(card);
            }
            return card.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CardDaoException(e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
