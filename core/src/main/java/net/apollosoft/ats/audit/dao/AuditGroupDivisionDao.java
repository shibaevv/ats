package net.apollosoft.ats.audit.dao;

import net.apollosoft.ats.audit.domain.hibernate.AuditGroupDivision;

import org.springframework.stereotype.Repository;


@Repository
public interface AuditGroupDivisionDao extends BaseDao<AuditGroupDivision>
{
    /**
     * 
     * @param groupDivision
     * @throws DaoException
     */
    void save(AuditGroupDivision groupDivision) throws DaoException;

    /**
     * 
     * @param groupDivision
     * @throws DaoException
     */
    void delete(AuditGroupDivision groupDivision) throws DaoException;

}
