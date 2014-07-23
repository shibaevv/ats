package net.apollosoft.ats.audit.domain.hibernate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.apollosoft.ats.audit.domain.IAction;
import net.apollosoft.ats.audit.domain.IComment;
import net.apollosoft.ats.audit.domain.IIssue;
import net.apollosoft.ats.audit.domain.hibernate.refdata.ActionFollowupStatus;
import net.apollosoft.ats.audit.domain.hibernate.refdata.BusinessStatus;
import net.apollosoft.ats.audit.domain.refdata.IActionFollowupStatus;
import net.apollosoft.ats.audit.domain.refdata.IBusinessStatus;
import net.apollosoft.ats.domain.hibernate.security.User;
import net.apollosoft.ats.domain.security.IUser;

import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableMetaData;
import org.compass.annotations.SearchableProperty;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Where;
import org.hibernate.annotations.WhereJoinTable;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Entity
@Table(name = "ats_action")
@org.hibernate.annotations.Entity(mutable = true, optimisticLock = OptimisticLockType.VERSION)
//@Cache(region = "audit", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Searchable(root = true)
public class Action extends Publishable<Long> implements IAction
{

    /** serialVersionUID */
    private static final long serialVersionUID = -6836870076074457628L;

    /** identity persistent field */
    @Id
    @Column(name = "action_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SearchableId
    private Long actionId;

    /** persistent field */
    @Basic()
    @Column(name = "action_ref", nullable = true)
    private String reference;

    /** persistent field */
    @Basic()
    @Column(name = "action_name", nullable = true)
    @SearchableProperty(name = "name")
    @SearchableMetaData(name = "actionName")
    private String name;

    /** persistent field */
    @Basic()
    @Column(name = "action_list_index", nullable = false)
    private BigDecimal listIndex;

    /** nullable persistent field */
    @Lob()
    @Column(name = "action_detail", nullable = true)
    private String detail;

    /** nullable persistent field */
    @Temporal(TemporalType.DATE)
    @Column(name = "action_due_date", nullable = true)
    @SearchableProperty(name = "dueDate", format = "dd-MMM-yyyy") //IDateFormat.DEFAULT_DATE_PATTERN
    private Date dueDate;

    /** nullable persistent field */
    @Temporal(TemporalType.DATE)
    @Column(name = "action_closed_date", nullable = true)
    @SearchableProperty(name = "closedDate", format = "dd-MMM-yyyy") //IDateFormat.DEFAULT_DATE_PATTERN
    private Date closedDate;

    /** nullable persistent field */
    @Temporal(TemporalType.DATE)
    @Column(name = "action_followup_date", nullable = true)
    private Date followupDate;

    /** nullable persistent field */
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = BusinessStatus.class)
    @JoinColumn(name = "business_status_id", nullable = true)
    private IBusinessStatus businessStatus;

    /** nullable persistent field */
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = ActionFollowupStatus.class)
    @JoinColumn(name = "action_followup_status_id", nullable = true)
    private IActionFollowupStatus followupStatus;

    /** persistent field */
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinTable(name = "ats_action_responsible", joinColumns = @JoinColumn(name = "action_id", referencedColumnName = "action_id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    @WhereJoinTable(clause = "deleted_date IS NULL")
    private List<IUser> responsibleUsers;

    @OneToMany(mappedBy = "action", fetch = FetchType.LAZY, cascade =
    {
        CascadeType.PERSIST
    })
    private List<ActionResponsible> actionResponsibles;

    @OneToMany(mappedBy = "action", fetch = FetchType.LAZY, cascade =
    {
        CascadeType.PERSIST
    })
    private List<ActionGroupDivision> groupDivisions;

    /** persistent field */
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Issue.class)
    @JoinColumn(name = "issue_id", nullable = false)
    private IIssue issue;

    @OneToMany(mappedBy = "action", fetch = FetchType.LAZY, targetEntity = Comment.class, cascade =
    {
        CascadeType.PERSIST
    })
    //@IndexColumn(name="comment_list_index",base=1,nullable=false) //@javax.persistence.OrderColumn
    @OrderBy("listIndex")
    @Where(clause = "deleted_date IS NULL AND audit_log = 'N'")
    private List<IComment> comments;

    @OneToMany(mappedBy = "action", fetch = FetchType.LAZY, targetEntity = Comment.class, cascade =
    {
        CascadeType.PERSIST
    })
    //@IndexColumn(name="comment_list_index",base=1,nullable=false) //@javax.persistence.OrderColumn
    @OrderBy("listIndex")
    @Where(clause = "deleted_date IS NULL AND audit_log = 'Y'")
    private List<IComment> logs;

//    @ManyToMany(fetch=FetchType.LAZY)
//    @JoinTable(name="ats_action_tag",
//        joinColumns = @JoinColumn(name="action_id", referencedColumnName="action_id"),
//        inverseJoinColumns = @JoinColumn(name="tag_id", referencedColumnName="tag_id"))
//    @SearchableDynamicProperty
//    private List<Tag> tags;

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getActionId();
    }

    /**
     *
     * @return
     */
    public Long getActionId()
    {
        if (actionId != null && actionId.longValue() == 0L)
        {
            actionId = null;
        }
        return actionId;
    }

    /**
     *
     * @param actionId
     */
    public void setActionId(Long actionId)
    {
        this.actionId = actionId;
    }

    /**
     * @return the reference
     */
    public String getReference()
    {
        return reference;
    }

    /**
     * @param reference the reference to set
     */
    public void setReference(String reference)
    {
        this.reference = reference;
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
     * @return the detail
     */
    public String getDetail()
    {
        return detail;
    }

    /**
     * @param detail the detail to set
     */
    public void setDetail(String detail)
    {
        this.detail = detail;
    }

    /**
     * @return the dueDate
     */
    public Date getDueDate()
    {
        return dueDate;
    }

    /**
     * @param dueDate the dueDate to set
     */
    public void setDueDate(Date dueDate)
    {
        this.dueDate = dueDate;
    }

    /**
     * @return the closedDate
     */
    public Date getClosedDate()
    {
        return closedDate;
    }

    /**
     * @param closedDate the closedDate to set
     */
    public void setClosedDate(Date closedDate)
    {
        this.closedDate = closedDate;
    }

    /**
     * @return the followupDate
     */
    public Date getFollowupDate()
    {
        return followupDate;
    }

    /**
     * @param followupDate the followupDate to set
     */
    public void setFollowupDate(Date followupDate)
    {
        this.followupDate = followupDate;
    }

    /**
     * @return the businessStatus
     */
    public IBusinessStatus getBusinessStatus()
    {
        return businessStatus;
    }

    /**
     * @param businessStatus the businessStatus to set
     */
    public void setBusinessStatus(IBusinessStatus businessStatus)
    {
        this.businessStatus = businessStatus;
    }

    /**
     * @return the followupStatus
     */
    public IActionFollowupStatus getFollowupStatus()
    {
        return followupStatus;
    }

    /**
     * @param followupStatus the followupStatus to set
     */
    public void setFollowupStatus(IActionFollowupStatus followupStatus)
    {
        this.followupStatus = followupStatus;
    }

    /**
     * Calculated method - based on ActionResponsible users (not deleted)
     * @return the responsibleUsers
     */
    public List<IUser> getResponsibleUsers()
    {
        if (responsibleUsers == null)
        {
            responsibleUsers = new ArrayList<IUser>();
        }
        return responsibleUsers;
    }

    /**
     * @return the actionResponsibles
     */
    public List<ActionResponsible> getActionResponsibles()
    {
        if (actionResponsibles == null)
        {
            actionResponsibles = new ArrayList<ActionResponsible>();
        }
        return actionResponsibles;
    }

    public boolean remove(ActionResponsible entity)
    {
        return getActionResponsibles().remove(entity);
    }

    /**
     * @return the groupDivisions
     */
    public List<ActionGroupDivision> getGroupDivisions()
    {
        if (groupDivisions == null)
        {
            groupDivisions = new ArrayList<ActionGroupDivision>();
        }
        return groupDivisions;
    }

    /**
     * @param groupDivisions the groupDivisions to set
     */
    protected void setGroupDivisions(List<ActionGroupDivision> groupDivisions)
    {
        this.groupDivisions = groupDivisions;
    }

    public void add(ActionGroupDivision entity)
    {
        add(getGroupDivisions(), entity);
    }

    public boolean remove(ActionGroupDivision entity)
    {
        return getGroupDivisions().remove(entity);
    }

    /**
     * @return the issue
     */
    public IIssue getIssue()
    {
        return issue;
    }

    /**
     * @param issue the issue to set
     */
    public void setIssue(IIssue issue)
    {
        this.issue = issue;
    }

    /**
     * @return the comments
     */
    public List<IComment> getComments()
    {
        if (comments == null)
        {
            comments = new ArrayList<IComment>();
        }
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    protected void setComments(List<IComment> comments)
    {
        this.comments = comments;
    }

    /**
     * @return the logs
     */
    public List<IComment> getLogs()
    {
        if (logs == null)
        {
            logs = new ArrayList<IComment>();
        }
        return logs;
    }

    /**
     * @param logs the logs to set
     */
    protected void setLogs(List<IComment> logs)
    {
        this.logs = logs;
    }

    /**
     * 
     * @param entity
     */
    public void addComment(Comment entity)
    {
        int idx = add(getComments(), entity);
        entity.setListIndex((byte) ++idx);
        entity.setAuditLog(false);
        entity.setAction(this);
    }

    /**
     * 
     * @param entity
     */
    public void addLog(Comment entity)
    {
        //TODO: refactor this
        if (!this.isPublished())
        {
            return;
        }
        int idx = add(getLogs(), entity);
        entity.setListIndex((byte) ++idx);
        entity.setAuditLog(true);
        entity.setAction(this);
    }

    /**
     * @return the listIndex
     */
    public BigDecimal getListIndex()
    {
        return listIndex;
    }

    /**
     * @param listIndex the listIndex to set
     */
    public void setListIndex(BigDecimal listIndex)
    {
        this.listIndex = listIndex;
    }

//    /**
//     * @return the tags
//     */
//    public List<Tag> getTags()
//    {
//        if (tags == null)
//        {
//            tags = new ArrayList<Tag>();
//        }
//        return tags;
//    }
//
//    /**
//     * @param tags the tags to set
//     */
//    protected void setTags(List<Tag> tags)
//    {
//        this.tags = tags;
//    }

}