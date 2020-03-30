package ru.otus.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.MyCache;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public class DbServiceUserImpl implements DBServiceUser {
    private static Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);

    private final UserDao userDao;

    private final boolean useCache;

    private int readCache;

    private final MyCache<String, User> cache = new MyCache<>();

    public DbServiceUserImpl(UserDao userDao, boolean useCache) {
        this.userDao = userDao;
        this.useCache = useCache;
        this.readCache = 0;
    }

    public void initReadCache()
    {
        this.readCache = 0;
    }

    @Override
    public long saveUser(User user) {
//сохраняем в базу
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long userId = userDao.saveUser(user);
                sessionManager.commitSession();

                logger.info("created user: {}", userId);

                if (useCache) {
//сохраняем в cache, если все ок
                    logger.info("Cached user: {}", userId);
                    cache.put(Long.toString(userId), user);
                }

                return userId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public Optional<User> getUser(long id) {
//читаем cache
        if (useCache) {
//            logger.info("Read user from cache: {}", id);
            Optional<User> userOptionalCache = Optional.ofNullable(cache.get(Long.toString(id)));
            if (!userOptionalCache.isEmpty()) {
                readCache++;
                return userOptionalCache;
            }
        }

        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<User> userOptional = userDao.findById(id);
//                  logger.info("user: {}", userOptional.orElse(null));

                //сохраняем в кэш
                if (useCache) {
                    logger.info("Cached user: {}", id);
                    cache.put(Long.toString(id), userOptional.orElse(null));
                }

                return userOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

    @Override
    public void updateUser(User user) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                userDao.updateUser(user);
                sessionManager.commitSession();

                logger.info("updated user: {}", user);

                if (useCache) {
//сохраняем в cache, если все ок
                    Long userId = user.getId();
                    logger.info("Cached user: {}", userId);
                    cache.remove(Long.toString(userId));
                    cache.put(Long.toString(userId), user);
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    public int getReadCache() {
        return readCache;
    }
}
