package com.tapestwitter.domain.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

/**
 * JPA CrudServiceDAO
 * 
 * @author karesti
 * @param <T>, type entity
 * @param <PK>, primarykey, the primary key
 */
@Repository("crudDAO")
public class JPACrudDAO implements CrudDAO
{

    @PersistenceContext
    protected EntityManager entityManager;

    public <T> T create(T t)
    {
        this.entityManager.persist(t);
        return t;
    }

    public <T> T update(T type)
    {
        entityManager.merge(type);
        return type;
    }

    public <T, PK extends Serializable> T find(Class<T> type, PK id)
    {
        return (T) entityManager.find(type, id);
    }

    public <T, PK extends Serializable> void delete(Class<T> type, PK id)
    {
        T ref = (T) entityManager.find(type, id);
        entityManager.remove(ref);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findWithNamedQuery(String queryName)
    {
        return entityManager.createNamedQuery(queryName).getResultList();
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findWithNamedQuery(String queryName, Map<String, Object> params)
    {
        Set<Entry<String, Object>> rawParameters = params.entrySet();
        Query query = entityManager.createNamedQuery(queryName);

        for (Entry<String, Object> entry : rawParameters)
        {
            query.setParameter(entry.getKey(), entry.getValue());

        }
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public <T> T findMaxResultsWithNamedQuery(String queryName, int range)
    {
        Query query = entityManager.createNamedQuery(queryName);
        query.setMaxResults(range);
        return (T) query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findMaxResultsWithNamedQuery(String queryName, Integer start, int range)
    {
        Query query = entityManager.createNamedQuery(queryName);
        if (start != null)
        {
            query.setFirstResult(start);
        }
        query.setMaxResults(range);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public <T> T findUniqueWithNamedQuery(String queryName)
    {
        return (T) entityManager.createNamedQuery(queryName).getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public <T> T findUniqueWithNamedQuery(String queryName, Map<String, Object> params)
    {
        Set<Entry<String, Object>> rawParameters = params.entrySet();
        Query query = entityManager.createNamedQuery(queryName);

        for (Entry<String, Object> entry : rawParameters)
        {
            query.setParameter(entry.getKey(), entry.getValue());

        }
        return (T) query.getSingleResult();
    }
}
