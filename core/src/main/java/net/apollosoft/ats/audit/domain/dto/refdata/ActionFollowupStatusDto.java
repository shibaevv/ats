package net.apollosoft.ats.audit.domain.dto.refdata;

import net.apollosoft.ats.audit.domain.refdata.IActionFollowupStatus;
import net.apollosoft.ats.domain.DomainException;
import net.apollosoft.ats.domain.dto.AuditableDto;
import net.apollosoft.ats.util.BeanUtils;
import net.apollosoft.ats.util.Formatter;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class ActionFollowupStatusDto extends AuditableDto<Long> implements IActionFollowupStatus
{

    /** All - (for UI only) */
    public static final ActionFollowupStatusDto ALL = new ActionFollowupStatusDto(null, "All");

    private Long id;

    private String name;

    /**
     * 
     */
    public ActionFollowupStatusDto()
    {
        super();
    }

    /**
     * 
     * @param source
     * @throws DomainException
     */
    public ActionFollowupStatusDto(IActionFollowupStatus source) throws DomainException
    {
        BeanUtils.copyProperties(source, this, IActionFollowupStatus.IGNORE);
        //TODO: re-factor to jsp ${fn:replace(entity.name,'"','\'')}
        name = Formatter.formatJson(name, true);
        //detail = Formatter.formatJson(detail, false);
    }

    /**
     * @param actionFollowupStatusId
     * @param name
     */
    private ActionFollowupStatusDto(Long id, String name)
    {
        this.id = id;
        this.name = name;
    }

    /**
     * 
     * @param source
     * @throws DomainException
     */
    //    public ActionStatusDto(IActionStatus source) throws DomainException
    //    {
    //        BeanUtils.copyProperties(source, this, IActionStatus.IGNORE);
    //        //TODO: re-factor to jsp ${fn:replace(entity.name,'"','\'')}
    //        name = Formatter.formatJson(name);
    //    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getActionFollowupStatusId();
    }

    /**
     * @return the id
     */
    public Long getActionFollowupStatusId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setActionFollowupStatusId(Long id)
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

}