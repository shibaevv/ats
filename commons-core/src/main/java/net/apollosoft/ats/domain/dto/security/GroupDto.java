package net.apollosoft.ats.domain.dto.security;

import net.apollosoft.ats.domain.DomainException;
import net.apollosoft.ats.domain.dto.AuditableDto;
import net.apollosoft.ats.domain.security.IGroup;
import net.apollosoft.ats.util.BeanUtils;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class GroupDto extends AuditableDto<Long> implements IGroup
{

    /** All - (for UI only) */
    public static final GroupDto ALL = new GroupDto(null, GLOBAL_NAME);

    private Long groupId;

    private String name;

    /**
     * 
     */
    public GroupDto()
    {
        super();
    }
    
    /**
     * @param groupId
     */
    public GroupDto(Long groupId)
    {
        this.groupId = groupId;
    }

    /**
     * @param groupId
     * @param name
     */
    private GroupDto(Long groupId, String name)
    {
        this.groupId = groupId;
        this.name = name;
    }

    /**
     * 
     * @param source
     * @throws DomainException
     */
    public GroupDto(IGroup source) throws DomainException
    {
        BeanUtils.copyProperties(source, this, IGroup.IGNORE);
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
        return getGroupId();
    }

    /**
     * @return the groupId
     */
    public Long getGroupId()
    {
        return groupId;
    }

    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(Long groupId)
    {
        this.groupId = groupId;
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