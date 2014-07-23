package net.apollosoft.ats.audit.domain.hibernate;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.apollosoft.ats.audit.domain.IAudit;
import net.apollosoft.ats.audit.domain.IIssue;
import net.apollosoft.ats.domain.IDocument;
import net.apollosoft.ats.domain.hibernate.Document;
import net.apollosoft.ats.domain.hibernate.refdata.ReportType;
import net.apollosoft.ats.domain.hibernate.security.Division;
import net.apollosoft.ats.domain.hibernate.security.Group;
import net.apollosoft.ats.domain.refdata.IReportType;
import net.apollosoft.ats.domain.security.IDivision;
import net.apollosoft.ats.domain.security.IGroup;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Where;
import org.hibernate.annotations.WhereJoinTable;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Entity
@Table(name = "ats_audit")
@org.hibernate.annotations.Entity(mutable = true, optimisticLock = OptimisticLockType.VERSION)
@NamedQueries(value =
{
    @NamedQuery(name = "audit.countActionOpen", query = "SELECT count(id) FROM Action WHERE issue.audit.id = :auditId AND businessStatus.actionStatus.id = :actionStatusId AND deletedDate IS NULL AND publishedBy IS NOT NULL AND publishedDate IS NOT NULL"),
    @NamedQuery(name = "audit.countActionTotal", query = "SELECT count(id) FROM Action WHERE issue.audit.id = :auditId AND deletedDate IS NULL AND publishedBy IS NOT NULL AND publishedDate IS NOT NULL"),
    @NamedQuery(name = "audit.countIssueUnpublished", query = "SELECT count(id) FROM Issue WHERE audit.id = :auditId AND publishedBy IS NULL AND publishedDate IS NULL AND deletedDate IS NULL"),
    @NamedQuery(name = "audit.countActionUnpublished", query = "SELECT count(id) FROM Action WHERE issue.audit.id = :auditId AND publishedBy IS NULL AND publishedDate IS NULL AND deletedDate IS NULL")
})
//@Cache(region = "audit", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Audit extends Publishable<Long> implements IAudit
{

    /** serialVersionUID */
    private static final long serialVersionUID = 4612659864790749194L;

    /** identity persistent field */
    @Id
    @Column(name = "audit_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long auditId;

    /** persistent field */
    @Basic()
    @Column(name = "audit_ref", nullable = true)
    private String reference;

    /** persistent field */
    @Basic()
    @Column(name = "audit_name", nullable = false)
    private String name;

    /** nullable persistent field */
    @Lob()
    @Column(name = "audit_detail", nullable = true)
    private String detail;

    /** nullable persistent field */
    @Temporal(TemporalType.DATE)
    @Column(name = "audit_issued_date", nullable = true)
    private Date issuedDate;

    /** nullable persistent field */
    @Basic()
    @Column(name = "audit_source", nullable = true)
    private Character source;

    /** nullable persistent field */
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = ReportType.class)
    @JoinColumn(name = "report_type_id", nullable = true)
    private IReportType reportType;

    /** nullable persistent field */
    @ManyToOne(fetch = FetchType.LAZY, cascade =
    {
        CascadeType.PERSIST
    }, targetEntity = Document.class)
    @JoinColumn(name = "document_id", nullable = true)
    private IDocument document;

    @OneToMany(mappedBy = "audit", fetch = FetchType.LAZY, cascade =
    {
        CascadeType.PERSIST
    }, targetEntity = Issue.class)
    @OrderBy("createdDate DESC")
    @Where(clause = "deleted_date IS NULL")
    private List<IIssue> issues;

    @OneToMany(mappedBy = "audit", fetch = FetchType.LAZY, cascade =
    {
        CascadeType.PERSIST
    })
    private List<AuditGroupDivision> groupDivisions;

    /** persistent field */
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Group.class)
    @JoinTable(name = "ats_audit_group_division", joinColumns = @JoinColumn(name = "audit_id", referencedColumnName = "audit_id"), inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "group_id"))
    @WhereJoinTable(clause = "deleted_date IS NULL")
    private List<IGroup> groups;

    /** persistent field */
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Division.class)
    @JoinTable(name = "ats_audit_group_division", joinColumns = @JoinColumn(name = "audit_id", referencedColumnName = "audit_id"), inverseJoinColumns = @JoinColumn(name = "division_id", referencedColumnName = "division_id"))
    @WhereJoinTable(clause = "deleted_date IS NULL")
    private List<IDivision> divisions;

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getAuditId();
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IAudit#getId()
     */
    public Long getAuditId()
    {
        if (auditId != null && auditId.longValue() == 0L)
        {
            auditId = null;
        }
        return auditId;
    }

    /**
     *
     * @param auditId
     */
    public void setAuditId(Long auditId)
    {
        this.auditId = auditId;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IAudit#getReference()
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

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IAudit#getName()
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

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IAudit#getDetail()
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

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IAudit#getIssuedDate()
     */
    public Date getIssuedDate()
    {
        return issuedDate;
    }

    /**
     * @param issuedDate the issuedDate to set
     */
    public void setIssuedDate(Date issuedDate)
    {
        this.issuedDate = issuedDate;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IAudit#getSource()
     */
    public Character getSource()
    {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(Character source)
    {
        this.source = source;
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
     * @return the document
     */
    public IDocument getDocument()
    {
        return document;
    }

    /**
     * @param document the document to set
     */
    public void setDocument(IDocument document)
    {
        this.document = document;
    }

    /**
     * @return the issues
     */
    public List<IIssue> getIssues()
    {
        if (issues == null)
        {
            issues = new ArrayList<IIssue>();
        }
        return issues;
    }

    /**
     * @param issues the issues to set
     */
    public void setIssues(List<IIssue> issues)
    {
        this.issues = issues;
    }

    /**
     * Safe to add item many times
     * @param entity
     */
    public void add(Issue entity)
    {
        int idx = add(getIssues(), entity);
        entity.setListIndex((byte) ++idx);
        entity.setAudit(this);
    }

    /**
     * 
     * @param entity
     */
    public void addGroupDivision(AuditGroupDivision entity)
    {
        add(getGroupDivisions(), entity);
        entity.setAudit(this);
    }

    /**
     * @return the groupDivisions
     */
    public List<AuditGroupDivision> getGroupDivisions()
    {
        if (groupDivisions == null)
        {
            groupDivisions = new ArrayList<AuditGroupDivision>();
        }
        return groupDivisions;
    }

    /**
     * @return the groups
     */
    public List<IGroup> getGroups()
    {
        if (groups == null)
        {
            groups = new ArrayList<IGroup>();
        }
        return groups;
    }

    /**
     * @return the divisions
     */
    public List<IDivision> getDivisions()
    {
        if (divisions == null)
        {
            divisions = new ArrayList<IDivision>();
        }
        return divisions;
    }

}