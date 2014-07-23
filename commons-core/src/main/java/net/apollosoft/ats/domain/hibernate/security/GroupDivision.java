package net.apollosoft.ats.domain.hibernate.security;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import net.apollosoft.ats.domain.hibernate.Auditable;
import net.apollosoft.ats.domain.security.IGroupDivision;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@MappedSuperclass
public abstract class GroupDivision extends Auditable<Long> implements IGroupDivision
{

    /** serialVersionUID */
    private static final long serialVersionUID = 989903490084352757L;

    /** persistent field */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    /** persistent field */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "division_id", nullable = true)
    private Division division;

    public GroupDivision()
    {
        super();
    }

    public GroupDivision(Group group, Division division)
    {
        this.group = group;
        this.division = division;
    }

    /**
     * @return the group
     */
    public Group getGroup()
    {
        return group;
    }

    /**
     * @param group the group to set
     */
    public void setGroup(Group group)
    {
        this.group = group;
    }

    /**
     * @return the division
     */
    public Division getDivision()
    {
        return division;
    }

    /**
     * @param division the division to set
     */
    public void setDivision(Division division)
    {
        this.division = division;
    }

}