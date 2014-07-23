package net.apollosoft.ats.audit.domain.dto.refdata;

import net.apollosoft.ats.audit.domain.refdata.IActionStatus;
import net.apollosoft.ats.domain.DomainException;
import net.apollosoft.ats.domain.dto.AuditableDto;
import net.apollosoft.ats.util.BeanUtils;
import net.apollosoft.ats.util.Formatter;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class ActionStatusDto extends AuditableDto<Long> implements IActionStatus
{

    /** All - (for UI only) */
    public static final ActionStatusDto ALL = new ActionStatusDto(null, "All");

    /** OPEN - (for UI only) */
    public static final ActionStatusDto OPEN = new ActionStatusDto(ActionStatusEnum.OPEN.ordinal(), "Open");

    private Long id;

    private String name;

    /**
     * 
     */
    public ActionStatusDto()
    {
        super();
    }

    public ActionStatusDto(Long id)
    {
        this.id = id;
    }

    /**
     * @param id
     * @param name
     */
    public ActionStatusDto(Long id, String name)
    {
        this.id = id;
        this.name = name;
    }

    /**
     * @param id
     * @param name
     */
    private ActionStatusDto(Number id, String name)
    {
        this.id = id == null ? null : new Long(id.longValue());
        this.name = name;
    }

    /**
     * 
     * @param source
     * @throws DomainException
     */
    public ActionStatusDto(IActionStatus source) throws DomainException
    {
        BeanUtils.copyProperties(source, this, IActionStatus.IGNORE);
        //TODO: re-factor to jsp ${fn:replace(entity.name,'"','\'')}
        name = Formatter.formatJson(name, true);
        //detail = Formatter.formatJson(detail, false);
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getActionStatusId();
    }

    /**
     * @return the id
     */
    public Long getActionStatusId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setActionStatusId(Long id)
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
     * @return
     */
    public boolean isClosed()
    {
        return id != null && (id == ActionStatusEnum.CLOSED.ordinal());
    }

    /**
     * @return
     */
    public boolean isOpen()
    {
        return id != null && (id == ActionStatusEnum.OPEN.ordinal());
    }

}