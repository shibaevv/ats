package net.apollosoft.ats.domain.dto.security;

import net.apollosoft.ats.domain.DomainException;
import net.apollosoft.ats.domain.dto.AuditableDto;
import net.apollosoft.ats.domain.security.IDivision;
import net.apollosoft.ats.domain.security.IGroup;
import net.apollosoft.ats.util.BeanUtils;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class DivisionDto extends AuditableDto<Long> implements IDivision
{

    /** All - within selected group (for UI only) */
    public static final DivisionDto ALL = new DivisionDto(null, ALL_NAME);

    private Long divisionId;

    private String name;

    private IGroup group;

    /**
     * 
     */
    public DivisionDto()
    {
        super();
    }

    public DivisionDto(Long divisionId)
    {
        this.divisionId = divisionId;
    }

    private DivisionDto(Long divisionId, String name)
    {
        this.divisionId = divisionId;
        this.name = name;
    }

    /**
     * 
     * @param source
     * @throws DomainException
     */
    public DivisionDto(IDivision source) throws DomainException
    {
        BeanUtils.copyProperties(source, this, IDivision.IGNORE);
        //TODO: re-factor to jsp ${fn:replace(entity.name,'"','\'')}
        //name = Formatter.formatJson(name);
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.Base#toString()
     */
    @Override
    public String toString()
    {
        return name;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getDivisionId();
    }

    /**
     * @return the divisionId
     */
    public Long getDivisionId()
    {
        return divisionId;
    }

    /**
     * @param divisionId the divisionId to set
     */
    public void setDivisionId(Long divisionId)
    {
        this.divisionId = divisionId;
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
     * @return the group
     */
    public IGroup getGroup()
    {
        return group;
    }

    /**
     * @param group the group to set
     */
    public void setGroup(IGroup group)
    {
        this.group = group;
    }

}