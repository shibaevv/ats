package net.apollosoft.ats.audit.domain.hibernate;

import java.util.ArrayList;
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import net.apollosoft.ats.audit.domain.IAction;
import net.apollosoft.ats.audit.domain.IAudit;
import net.apollosoft.ats.audit.domain.IIssue;
import net.apollosoft.ats.audit.domain.hibernate.refdata.ParentRisk;
import net.apollosoft.ats.audit.domain.hibernate.refdata.Rating;
import net.apollosoft.ats.audit.domain.refdata.IParentRisk;
import net.apollosoft.ats.audit.domain.refdata.IRating;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Where;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Entity
@Table(name = "ats_issue")
@org.hibernate.annotations.Entity(mutable = true, optimisticLock = OptimisticLockType.VERSION)
@NamedQueries(value =
{
    @NamedQuery(name = "issue.countActionOpen", query = "SELECT count(id) FROM Action WHERE issue.id = :issueId AND businessStatus.actionStatus.id = :actionStatusId AND deletedDate IS NULL AND publishedBy IS NOT NULL AND publishedDate IS NOT NULL"),
    @NamedQuery(name = "issue.countActionTotal", query = "SELECT count(id) FROM Action WHERE issue.id = :issueId AND deletedDate IS NULL AND publishedBy IS NOT NULL AND publishedDate IS NOT NULL")
})
//@Cache(region = "audit", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Issue extends Publishable<Long> implements IIssue
{

    /** serialVersionUID */
    private static final long serialVersionUID = -8931097492971951283L;

    /** identity persistent field */
    @Id
    @Column(name = "issue_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long issueId;

    /** persistent field */
    @Basic()
    @Column(name = "issue_ref", nullable = true)
    private String reference;

    /** persistent field */
    @Basic()
    @Column(name = "issue_name", nullable = true)
    private String name;

    /** persistent field */
    @Basic()
    @Column(name = "issue_list_index", nullable = false)
    private Byte listIndex;

    /** nullable persistent field */
    @Lob()
    @Column(name = "issue_detail", nullable = true)
    private String detail;

    /** persistent field */
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Audit.class)
    @JoinColumn(name = "audit_id", nullable = false)
    private IAudit audit;

    /** persistent field */
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = ParentRisk.class)
    @JoinColumn(name = "parent_risk_id", nullable = true)
    private IParentRisk risk;

    /** nullable persistent field */
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Rating.class)
    @JoinColumn(name = "rating_id", nullable = true)
    private IRating rating;

    @OneToMany(mappedBy = "issue", fetch = FetchType.LAZY, targetEntity = Action.class, cascade =
    {
        CascadeType.PERSIST
    })
    @OrderBy("createdDate DESC")
    @Where(clause = "deleted_date IS NULL")
    private List<IAction> actions;

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getIssueId();
    }

    /**
     *
     * @return
     */
    public Long getIssueId()
    {
        if (issueId != null && issueId.longValue() == 0L)
        {
            issueId = null;
        }
        return issueId;
    }

    /**
     *
     * @param issueId
     */
    public void setIssueId(Long issueId)
    {
        this.issueId = issueId;
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
     * @return the risk
     */
    public IParentRisk getRisk()
    {
        return risk;
    }

    /**
     * @param risk the risk to set
     */
    public void setRisk(IParentRisk risk)
    {
        this.risk = risk;
    }

    /**
     * @return the rating
     */
    public IRating getRating()
    {
        return rating;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(IRating rating)
    {
        this.rating = rating;
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
     * @return the audit
     */
    public IAudit getAudit()
    {
        return audit;
    }

    /**
     * @param audit the audit to set
     */
    public void setAudit(IAudit audit)
    {
        this.audit = audit;
    }

    /**
     * @return the actions
     */
    public List<IAction> getActions()
    {
        if (actions == null)
        {
            actions = new ArrayList<IAction>();
        }
        return actions;
    }

    /**
     * @param actions the actions to set
     */
    public void setActions(List<IAction> actions)
    {
        this.actions = actions;
    }

    /**
     * 
     * @param entity
     */
    public void add(Action entity)
    {
        add(getActions(), entity);
        entity.setIssue(this);
    }

    /**
     * @return the listIndex
     */
    public Byte getListIndex()
    {
        return listIndex;
    }

    /**
     * @param listIndex the listIndex to set
     */
    public void setListIndex(Byte listIndex)
    {
        this.listIndex = listIndex;
    }

}