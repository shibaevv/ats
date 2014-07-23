package net.apollosoft.ats.audit.domain.dto.refdata;

import net.apollosoft.ats.audit.domain.refdata.IActionStatus;
import net.apollosoft.ats.audit.domain.refdata.IBusinessStatus;
import net.apollosoft.ats.domain.DomainException;
import net.apollosoft.ats.domain.dto.AuditableDto;
import net.apollosoft.ats.util.BeanUtils;
import net.apollosoft.ats.util.Formatter;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class BusinessStatusDto extends AuditableDto<Long> implements IBusinessStatus
{

    /** All - (for UI only) */
    public static final BusinessStatusDto ALL = new BusinessStatusDto(null, "");

    private Long id;

    private String name;

    private IActionStatus actionStatus;

    /**
     * 
     */
    public BusinessStatusDto()
    {
        super();
    }

    public BusinessStatusDto(Long id)
    {
        this.id = id;
    }

    /**
     * @param id
     * @param name
     */
    public BusinessStatusDto(Long id, String name)
    {
        this.id = id;
        this.name = name;
    }

    /**
     * 
     * @param source
     * @throws DomainException
     */
    public BusinessStatusDto(IBusinessStatus source) throws DomainException
    {
        BeanUtils.copyProperties(source, this, IBusinessStatus.IGNORE);
        actionStatus = source.getActionStatus() == null ? null : new ActionStatusDto(source
            .getActionStatus());
        //TODO: re-factor to jsp ${fn:replace(entity.name,'"','\'')}
        name = Formatter.formatJson(name, true);
        //detail = Formatter.formatJson(detail, false);
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getBusinessStatusId();
    }

    /**
     * @return the id
     */
    public Long getBusinessStatusId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setBusinessStatusId(Long id)
    {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the actionStatus
     */
    public IActionStatus getActionStatus()
    {
        if (actionStatus == null)
        {
            actionStatus = new ActionStatusDto();
        }
        return actionStatus;
    }

    /**
     * @param actionStatus the actionStatus to set
     */
    public void setActionStatus(IActionStatus actionStatus)
    {
        this.actionStatus = actionStatus;
    }

    /**
     * @return
     */
    public boolean isNotActioned()
    {
        return id != null && (id == BusinessStatusEnum.NOT_ACTIONED.ordinal());
    }

    /**
     * @return
     */
    public boolean isInProgress()
    {
        return id != null && (id == BusinessStatusEnum.IN_PROGRESS.ordinal());
    }

    /**
     * @return
     */
    public boolean isImplemented()
    {
        return id != null && (id == BusinessStatusEnum.IMPLEMENTED.ordinal());
    }

    /**
     * @return
     */
    public boolean isNoLongerApplicable()
    {
        return id != null && (id == BusinessStatusEnum.NO_LONGER_APPLICABLE.ordinal());
    }

}