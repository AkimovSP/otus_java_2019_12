package ru.otus.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.otus.core.dao.CardDao;
import ru.otus.core.dao.CardDaoException;
import ru.otus.core.dao.MyATMDao;
import ru.otus.core.dao.MyATMDaoException;
import ru.otus.core.model.Card;
import ru.otus.core.model.MyATMImpl;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.hibernate.sessionmanager.DatabaseSessionHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Repository
public class MyATMDaoHibernate implements MyATMDao {
    private static Logger logger = LoggerFactory.getLogger(MyATMDaoHibernate.class);

    private final SessionManagerHibernate sessionManager;

    public MyATMDaoHibernate(SessionManagerHibernate sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public Optional<MyATMImpl> findById(long id) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            return Optional.ofNullable(currentSession.getHibernateSession().find(MyATMImpl.class, id));
        } catch (Exception e) {
        }
        return Optional.empty();
    }

    @Override
    public Optional<MyATMImpl> findByName(String name) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session session = currentSession.getHibernateSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<MyATMImpl> cr = builder.createQuery(MyATMImpl.class);
            Root<MyATMImpl> root = cr.from(MyATMImpl.class);
            cr.select(root).where(builder.equal(root.get("name"), name));
            Query<MyATMImpl> query = session.createQuery(cr);
            MyATMImpl myATM = query.getSingleResult();
            return Optional.ofNullable(myATM);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public long saveMyATM(MyATMImpl myATM) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            if (myATM.getId() > 0) {
                hibernateSession.merge(myATM);
            } else {
                hibernateSession.persist(myATM);
            }
            return myATM.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new MyATMDaoException(e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
