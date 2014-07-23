package net.apollosoft.ats.audit.dao;

import java.util.List;

import net.apollosoft.ats.audit.domain.hibernate.refdata.ParentRisk;
import net.apollosoft.ats.audit.domain.hibernate.refdata.ParentRiskCategory;


public interface ParentRiskDao extends BaseDao<ParentRisk>
{

    List<ParentRisk> findByCategory(ParentRiskCategory category) throws DaoException;

}
