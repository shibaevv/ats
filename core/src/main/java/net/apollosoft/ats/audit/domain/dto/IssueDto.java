package net.apollosoft.ats.audit.domain.dto;

import java.util.ArrayList;
import java.util.List;

import net.apollosoft.ats.audit.domain.IAction;
import net.apollosoft.ats.audit.domain.IAudit;
import net.apollosoft.ats.audit.domain.IIssue;
import net.apollosoft.ats.audit.domain.dto.refdata.ParentRiskDto;
import net.apollosoft.ats.audit.domain.dto.refdata.RatingDto;
import net.apollosoft.ats.audit.domain.refdata.IParentRisk;
import net.apollosoft.ats.audit.domain.refdata.IRating;
import net.apollosoft.ats.domain.DomainException;
import net.apollosoft.ats.util.BeanUtils;
import net.apollosoft.ats.util.Formatter;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class IssueDto extends PublishableDto<Long> implements IIssue
{

    private Long issueId;

    private String reference;

    private String name;

    private String detail;

    private IRating rating;

    private IParentRisk risk;

    private IAudit audit;

    private List<IAction> actions;

    private Integer actionOpen;

    private Integer actionTotal;

    private Byte listIndex;

    /**
     * 
     */
    public IssueDto()
    {
        super();
    }

    /**
     * 
     * @param source
     * @throws DomainException
     */
    public IssueDto(IIssue source) throws DomainException
    {
        BeanUtils.copyProperties(source, this, IIssue.IGNORE);
        risk = source.getRisk() == null ? null : new ParentRiskDto(source.getRisk());
        rating = source.getRating() == null ? null : new RatingDto(source.getRating());
        //TODO: re-factor to jsp ${fn:replace(entity.name,'"','\'')}
        name = Formatter.formatJson(name, true);
        detail = Formatter.formatJson(detail, false);
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getIssueId();
    }

    /**
     * @return the id
     */
    public Long getIssueId()
    {
        return issueId;
    }

    /**
     * @param issueId the issueId to set
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
     * @return the risk
     */
    public IParentRisk getRisk()
    {
        if (risk == null)
        {
            risk = new ParentRiskDto();
        }
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
        if (rating == null)
        {
            rating = new RatingDto();
        }
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
    public void setActionTotal(Integer actionTotal)
    {
        this.actionTotal = actionTotal;
    }

    /**
     * @param listIndex the listIndex to set
     */
    public Byte getListIndex()
    {
        return listIndex;
    }

    /**
     * @return the listIndex
     */
    public void setListIndex(Byte listIndex)
    {
        this.listIndex = listIndex;
    }

}