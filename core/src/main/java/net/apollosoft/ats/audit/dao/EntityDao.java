package net.apollosoft.ats.audit.dao;

import java.util.List;

import net.apollosoft.ats.audit.domain.hibernate.refdata.ActionFollowupStatus;
import net.apollosoft.ats.audit.domain.hibernate.refdata.ActionStatus;
import net.apollosoft.ats.audit.domain.hibernate.refdata.BusinessStatus;
import net.apollosoft.ats.audit.domain.hibernate.refdata.Rating;
import net.apollosoft.ats.audit.domain.refdata.IParentRisk;
import net.apollosoft.ats.domain.hibernate.refdata.ReportType;

import org.springframework.stereotype.Repository;


/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Repository
public interface EntityDao extends BaseDao//<BaseModel>
{

    /**
     * 
     * @param name
     * @return
     * @throws DaoException
     */
    ActionStatus findActionStatusByName(String name) throws DaoException;

    /**
     * 
     * @param name
     * @return
     * @throws DaoException
     */
    ActionFollowupStatus findActionFollowupStatusByName(String name) throws DaoException;

    /**
     * 
     * @return
     * @throws DaoException
     */
    List<ActionStatus> findAllActionStatus() throws DaoException;

    /**
     * 
     * @return
     * @throws DaoException
     */
    List<ActionFollowupStatus> findAllFollowupStatus() throws DaoException;

    /**
     * Find by unique key.
     * @param name
     * @return
     * @throws DaoException
     */
    Rating findRatingByName(String name) throws DaoException;

    /**
     * Find by unique key.
     * @param name
     * @return
     * @throws DaoException
     */
    BusinessStatus findBusinessStatusByName(String name) throws DaoException;

    /**
     * 
     * @return
     * @throws DaoException
     */
    List<Rating> findAllRatings() throws DaoException;

    /**
     * 
     * @return
     * @throws DaoException
     */
    List<ReportType> findAllReportTypes() throws DaoException;

    /**
     * 
     * @param name
     * @param category
     * @return
     * @throws DaoException
     */
    IParentRisk findRiskByNameCategory(String name, String category) throws DaoException;

}