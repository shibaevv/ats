package net.apollosoft.ats.audit.dao;

import java.io.Serializable;
import java.util.List;

import net.apollosoft.ats.domain.hibernate.Auditable;


/**
 * DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public interface BaseDao<T>
{

    /**
     * Delete (could be logically) the object from the database.
     * @param entity
     * @throws DaoException
     */
    void delete(Auditable<?> entity) throws DaoException;

    /**
     * UnDelete (could be logically) the object from the database.
     * @param entity
     * @throws DaoException
     */
    void unDelete(Auditable<?> entity) throws DaoException;

    /**
     * Finder:
     * @return
     * @throws DaoException
     */
    List<T> findAll() throws DaoException;

    /**
     * Finder:
     * @param id The search criteria (primary key of the object).
     * @return
     * @throws DaoException
     */
    T findById(Serializable id) throws DaoException;

    /**
     * Insert/**
     * @param entity The object to save.
     * @throws DaoException
     */
    void save(T entity) throws DaoException;

    /**
     * Insert/**
     * @param entity The object to save.
     * @throws DaoException
     */
    void merge(T entity) throws DaoException;

}