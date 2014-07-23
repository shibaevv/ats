package net.apollosoft.ats.audit.validation;

import java.util.ArrayList;
import java.util.List;

import net.apollosoft.ats.audit.dao.AuditDao;
import net.apollosoft.ats.audit.dao.DaoException;
import net.apollosoft.ats.audit.domain.hibernate.Audit;
import net.apollosoft.ats.audit.domain.hibernate.AuditGroupDivision;
import net.apollosoft.ats.audit.validation.ValidationException.ValidationMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class AuditGroupDivisionValidator extends BaseValidator<AuditGroupDivision>
{

    @Autowired
    @Qualifier("auditDao")
    //@Resource(name="auditDao")
    private AuditDao auditDao;

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.validation.BaseValidator#validate(java.lang.Object)
     */
    @Override
    public List<ValidationMessage> validate(AuditGroupDivision entity)
    {
        List<ValidationMessage> errors = new ArrayList<ValidationMessage>();
        try
        {
            if (entity != null)
            {
                Audit audit = entity.getAudit();
                Long auditId = audit.getAuditId();
                Long groupId = entity.getGroup().getGroupId();
                Long divisionId = entity.getDivision() == null ? null : entity.getDivision().getDivisionId();
                // test for groupId
                if (groupId == null)
                {
                    errors.add(new ValidationMessage("groupDivision", "Group is not selected"));
                }
                // test for unique
                AuditGroupDivision uniqueEntity = auditDao.findByUniqueKey(auditId, groupId,
                    divisionId);
                if (uniqueEntity != null) // && !uniqueEntity.equals(entity)
                {
                    errors.add(new ValidationMessage("groupDivision", "Not Unique Group/Division"));
                }
                // test for empty groupDivisions (will be tested in AuditValidator)
                //if (audit.getGroupDivisions().isEmpty())
                //{
                //    errors.add(new ValidationMessage("auditGroupDivision",
                //        "There must be at least one Group/Division"));
                //}
            }
        }
        catch (DaoException e)
        {
            errors.add(new ValidationMessage(null, e.getMessage()));
        }
        return errors;
    }

}