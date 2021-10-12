package com.epam.training.demo.repository;

import com.epam.training.demo.entity.User;
import com.epam.training.demo.mapper.UserMapper;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("cache")
public class CachedUserRepository implements UserRepository {
    public static final String SEARCH_BY_NAME = "select user0_.id as id1_0_, user0_.name as name2_0_ from User user0_ where user0_.name=?1";
    private final ConcurrentHashMap<Long, User> cache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> indexNameToId = new ConcurrentHashMap<>();

    @PersistenceContext
    private EntityManager entityManager;

    private final UserMapper mapper;

    public CachedUserRepository(UserMapper mapper) {
        this.mapper = mapper;
    }

    @Transactional
    public Optional<User> findByName(String name) {
        Optional<User> user = findInCacheByName(name);
        if (user.isEmpty())
            return findInDatabaseByName(name);
        return user;
    }

    private Optional<User> findInCacheByName(String name) {
        if (indexNameToId.containsKey(name))
            return Optional.ofNullable(cache.get(indexNameToId.get(name)));
        return Optional.empty();
    }

    private Optional<User> findInDatabaseByName(String name) {
        try {
            Optional<User> user = Optional.of(mapper.convert(entityManager.createQuery(SEARCH_BY_NAME)
                    .setParameter(1, name)
                    .getSingleResult()));
            save(user.get());
            return user;
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }


    public <S extends User> S save(S entity) {
        cache.put(entity.getId(), entity);
        indexNameToId.put(entity.getName(), entity.getId());
        return entity;
    }

    public <S extends User> Iterable<S> saveAll(Iterable<S> entities) {
        throw new NotYetImplementedException();
    }


    public Optional<User> findById(Long aLong) {
        return Optional.ofNullable(cache.get(aLong));
    }


    public boolean existsById(Long aLong) {
        throw new NotYetImplementedException();
    }


    public Iterable<User> findAll() {
        return cache.values();
    }


    public Iterable<User> findAllById(Iterable<Long> longs) {
        throw new NotYetImplementedException();
    }


    public long count() {
        throw new NotYetImplementedException();
    }


    public void deleteById(Long aLong) {
        indexNameToId.remove(cache.get(aLong).getName());
        cache.remove(aLong);
    }


    public void delete(User entity) {
        throw new NotYetImplementedException();
    }


    public void deleteAllById(Iterable<? extends Long> longs) {
        throw new NotYetImplementedException();
    }


    public void deleteAll(Iterable<? extends User> entities) {
        throw new NotYetImplementedException();
    }


    public void deleteAll() {
        throw new NotYetImplementedException();
    }
}
