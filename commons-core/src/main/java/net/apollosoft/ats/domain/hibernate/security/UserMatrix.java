package net.apollosoft.ats.domain.hibernate.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.apollosoft.ats.domain.hibernate.Auditable;
import net.apollosoft.ats.domain.hibernate.refdata.ReportType;
import net.apollosoft.ats.domain.refdata.IReportType;
import net.apollosoft.ats.domain.security.IDivision;
import net.apollosoft.ats.domain.security.IGroup;
import net.apollosoft.ats.domain.security.IRole;
import net.apollosoft.ats.domain.security.IUser;
import net.apollosoft.ats.domain.security.IUserMatrix;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Where;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Entity
@Table(name = "ats_user_matrix")
@org.hibernate.annotations.Entity(mutable = true, optimisticLock = OptimisticLockType.VERSION)
@Where(clause = "")
//@Cache(region = "security", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class UserMatrix extends Auditable<Long> implements IUserMatrix
{

    /** serialVersionUID */
    private static final long serialVersionUID = 4997318772473550893L;

    /** identity persistent field */
    @Id
    @Column(name="user_matrix_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userMatrixId;

    /** persistent field */
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false)
    private IUser user;

    /** persistent field */
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Role.class)
    @JoinColumn(name = "role_id", nullable = false)
    private IRole role;

    /** persistent field */
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = ReportType.class)
    @JoinColumn(name = "report_type_id", nullable = false)
    private IReportType reportType;

    /** persistent field */
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Group.class)
    @JoinColumn(name = "group_id", nullable = true)
    private IGroup group;

    /** persistent field */
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Division.class)
    @JoinColumn(name = "division_id", nullable = true)
    private IDivision division;

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getUserMatrixId();
    }

    /**
     * @return the userMatrixId
     */
    public Long getUserMatrixId()
    {
        return userMatrixId;
    }

    /**
     * @param userMatrixId the userMatrixId to set
     */
    public void setUserMatrixId(Long userMatrixId)
    {
        this.userMatrixId = userMatrixId;
    }

    /**
     * @return the user
     */
    public IUser getUser()
    {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(IUser user)
    {
        this.user = user;
    }

    /**
     * @return the role
     */
    public IRole getRole()
    {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(IRole role)
    {
        this.role = role;
    }

    /**
     * @return the reportType
     */
    public IReportType getReportType()
    {
        return reportType;
    }

    /**
     * @param reportType the reportType to set
     */
    public void setReportType(IReportType reportType)
    {
        this.reportType = reportType;
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

}