package net.apollosoft.ats.audit.domain.dto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import net.apollosoft.ats.audit.domain.IAudit;
import net.apollosoft.ats.audit.domain.IIssue;
import net.apollosoft.ats.audit.search.ISortBy;
import net.apollosoft.ats.domain.DomainException;
import net.apollosoft.ats.domain.IDocument;
import net.apollosoft.ats.domain.dto.DocumentDto;
import net.apollosoft.ats.domain.dto.refdata.ReportTypeDto;
import net.apollosoft.ats.domain.dto.security.GroupDivisionDto;
import net.apollosoft.ats.domain.refdata.IReportType;
import net.apollosoft.ats.domain.security.IGroupDivision;
import net.apollosoft.ats.util.BeanUtils;
import net.apollosoft.ats.util.Formatter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.AutoPopulatingList;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class AuditDto extends PublishableDto<Long> implements IAudit
{

    private Long auditId;

    private String reference;

    private String name;

    private String detail;

    private Date issuedDate;

    private IReportType reportType;

    private IDocument document;

    private Character source;

    private List<IIssue> issues;

    private AutoPopulatingList<IssueDto> issues2;

    private List<IGroupDivision> groupDivisions;

    private AutoPopulatingList<GroupDivisionDto> groupDivisions2;

    private String groupDivisionAll;

    private Integer actionOpen;

    private Integer actionTotal;

    private Integer actionUnpublished;

    private Integer issueUnpublished;

    /**
     * 
     */
    public AuditDto()
    {
        super();
    }

    /**
     * 
     * @param source
     * @throws DomainException
     */
    public AuditDto(IAudit source) throws DomainException
    {
        BeanUtils.copyProperties(source, this, IAudit.IGNORE);
        reportType = source.getReportType() == null ? null : new ReportTypeDto(source
            .getReportType());
        //TODO: re-factor to jsp ${fn:replace(entity.name,'"','\'')}
        name = Formatter.formatJson(name, true);
        detail = Formatter.formatJson(detail, false);
    }

    /**
     * @return the id
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
        return auditId;
    }

    /**
     * @param auditId the auditId to set
     */
    public void setAuditId(Long auditId)
    {
        this.auditId = auditId;
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
     * @return the issuedDate
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

    /**
     * @return the reportType
     */
    public IReportType getReportType()
    {
        if (reportType == null)
        {
            reportType = new ReportTypeDto();
        }
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
        if (document == null)
        {
            document = new DocumentDto();
        }
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
     * @return the source
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
     * 
     * @param entity
     */
    public void add(IssueDto entity)
    {
        int idx = add(getIssues2(), entity);
        entity.setListIndex((byte) ++idx);
        entity.setAudit(this);
    }

    /**
     * @return the issues
     */
    public AutoPopulatingList<IssueDto> getIssues2()
    {
        if (issues2 == null)
        {
            issues2 = new AutoPopulatingList<IssueDto>(IssueDto.class);
        }
        return issues2;
    }

    /**
     * @return the groupDivisionAll
     */
    public String getGroupDivisionAll()
    {
        return groupDivisionAll;
    }

    /**
     * @param groupDivisionAll the groupDivisionAll to set
     */
    public void setGroupDivisionAll(String groupDivisionAll)
    {
        this.groupDivisionAll = groupDivisionAll;
    }

    public List<IGroupDivision> getGroupDivisions()
    {
        if (groupDivisions == null)
        {
            groupDivisions = new ArrayList<IGroupDivision>();
        }
        return groupDivisions;
    }

    /**
     * @param groupDivisions the groupDivisions to set
     */
    public void setGroupDivisions(List<IGroupDivision> groupDivisions)
    {
        this.groupDivisions = groupDivisions;
    }

    /**
     * @return the groupDivisions2
     */
    public AutoPopulatingList<GroupDivisionDto> getGroupDivisions2()
    {
        if (groupDivisions2 == null)
        {
            groupDivisions2 = new AutoPopulatingList<GroupDivisionDto>(GroupDivisionDto.class);
        }
        return groupDivisions2;
    }

    /**
     * @return the actionOpen
     */
    public Integer getActionOpen()
    {
        return actionOpen;
    }

    /**
     * @param actionOpen the actionOpen to set
     */
    public void setActionOpen(Integer actionOpen)
    {
        this.actionOpen = actionOpen;
    }

    /**
     * @return the actionTotal
     */
    public Integer getActionTotal()
    {
        return actionTotal;
    }

    /**
     * @param actionTotal the actionTotal to set
     */
    public void setActionTotal(Integer actionFollow)
    {
        this.actionTotal = actionFollow;
    }

    /**
     * @return the actionUnpublished
     */
    public Integer getActionUnpublished()
    {
        return actionUnpublished;
    }

    /**
     * @param actionUnpublished the actionUnpublished to set
     */
    public void setActionUnpublished(Integer unpublishedActions)
    {
        this.actionUnpublished = unpublishedActions;
    }

    /**
     * @return the issueUnpublished
     */
    public Integer getIssueUnpublished()
    {
        return issueUnpublished;
    }

    /**
     * @param issueUnpublished the issueUnpublished to set
     */
    public void setIssueUnpublished(Integer unpublishedIssues)
    {
        this.issueUnpublished = unpublishedIssues;
    }

    /**
     * @deprecated DO IT IN SQL
     * Collections.sort(list, new AuditDtoComparator(ISortBy.NAME, IDir.ASC));
     */
    public static class AuditDtoComparator implements Comparator<AuditDto>
    {

        /** logger. */
        private static final Log LOG = LogFactory.getLog(AuditDtoComparator.class);

        /** sort */
        private String sort;

        /** dir */
        private String dir;

        /**
         * @param sort (@see ISortBy - single value)
         * @param dir (@see IDir - single value)
         */
        public AuditDtoComparator(String sort, String dir)
        {
            this.sort = sort;
            this.dir = dir;
        }

        /* (non-Javadoc)
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        public int compare(AuditDto o1, AuditDto o2)
        {
            if (ISortBy.ACTION_TOTAL.equals(sort))
            {
                return BeanUtils.compareTo(o1 == null ? null : o1.actionTotal, o2 == null ? null
                    : o2.actionTotal, dir);
            }
            if (ISortBy.ACTION_OPEN.equals(sort))
            {
                return BeanUtils.compareTo(o1 == null ? null : o1.actionOpen, o2 == null ? null
                    : o2.actionOpen, dir);
            }
            //TODO: no sort match throw?
            LOG.error("AuditDtoComparator is not configured for sort=[" + sort + "]");
            return 0;
        }

    }

}