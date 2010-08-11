package com.tapestwitter.domain.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.tapestwitter.domain.dao.IGenericDAO;

import org.hibernate.Session;

/**
 * Implementation generique servant de base a tous les DAO de l'application.
 * 
 * @author lguerin
 * @param <T>
 * @param <PK>
 */
public class GenericDAOImpl<T, PK extends Serializable> implements IGenericDAO<T, PK>
{

    @PersistenceContext
    protected EntityManager entityManager;

    private Class<T> entityType;

    @SuppressWarnings("unchecked")
    public GenericDAOImpl()
    {
        this.entityType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void create(T o)
    {
        this.entityManager.persist(o);
    }

    public void update(T o)
    {
        this.entityManager.merge(o);
    }

    public void delete(T o)
    {
        this.entityManager.remove(o);
    }

    @SuppressWarnings("unchecked")
    public T findById(PK id)
    {
        if (id == null)
            throw new IllegalArgumentException("Id for " + entityType.getCanonicalName() + " cannot be null.");
        Query query = entityManager.createQuery("from " + this.getEntityType() + " e where e.id=:id");
        query.setParameter("id", id);
        List<T> results = (List<T>) query.getResultList();
        if (results != null && results.size() == 1) { return results.get(0); }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<T> listAll()
    {
        Query query = this.entityManager.createQuery("from " + this.getEntityType());
        List<T> resultList = (List<T>) query.getResultList();
        return resultList;
    }

    protected Session getSession()
    {
        return (Session) entityManager.getDelegate();
    }

    public String getEntityType()
    {
        return entityType.getName();
    }

}
