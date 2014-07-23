package net.apollosoft.ats.domain.dto.security;

import net.apollosoft.ats.domain.DomainException;
import net.apollosoft.ats.domain.dto.AuditableDto;
import net.apollosoft.ats.domain.security.IDivision;
import net.apollosoft.ats.domain.security.IGroup;
import net.apollosoft.ats.domain.security.IGroupDivision;
import net.apollosoft.ats.util.BeanUtils;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class GroupDivisionDto extends AuditableDto<Long> implements IGroupDivision,
    Comparable<GroupDivisionDto>
{

    /** persistent field */
    private Long id;

    /** persistent field */
    private IGroup group;

    /** persistent field */
    private IDivision division;

    /**
     * 
     */
    public GroupDivisionDto()
    {
        super();
    }

    /**
     * 
     * @param source
     * @throws DomainException
     */
    public GroupDivisionDto(IGroupDivision source) throws DomainException
    {
        BeanUtils.copyProperties(source, this, IGroupDivision.IGNORE);
        group = source.getGroup() == null ? null : new GroupDto(source.getGroup());
        division = source.getDivision() == null ? null : new DivisionDto(source.getDivision());
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id)
    {
        this.id = id;
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

    /**
     * @return the division
     */
    public IDivision getDivision()
    {
        return division;
    }

    /**
     * @param division the division to set
     */
    public void setDivision(IDivision division)
    {
        this.division = division;
    }

    /**
     * For UI layer
     * format: Group Division
     * @return
     */
    public String getName()
    {
        return division != null ? (group.getName() + " " + division.getName())
            : (group != null ? group.getName() + " All" : "");
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object other)
    {
        if (this == other)
        {
            return true; // super implementation
        }
        if (other != null)
        {
            IGroupDivision castOther = (IGroupDivision) other;
            if (id != null && castOther.getId() != null)
            {
                return new EqualsBuilder().append(id, castOther.getId()).isEquals();
            }
            return new EqualsBuilder().append(group, castOther.getGroup()).append(division,
                castOther.getDivision()).isEquals();
        }
        return false;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        return new HashCodeBuilder().append(group).append(division).toHashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringBuilder(this).append("group", group).append("division", division)
            .toString();
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(GroupDivisionDto o)
    {
        return BeanUtils.compareTo(o.getName(), getName());
    }

}