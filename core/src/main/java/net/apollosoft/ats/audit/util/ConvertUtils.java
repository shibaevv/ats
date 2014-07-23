package net.apollosoft.ats.audit.util;

import java.util.ArrayList;
import java.util.List;

import net.apollosoft.ats.audit.domain.IAction;
import net.apollosoft.ats.audit.domain.IAudit;
import net.apollosoft.ats.audit.domain.IComment;
import net.apollosoft.ats.audit.domain.IIssue;
import net.apollosoft.ats.audit.domain.dto.ActionDto;
import net.apollosoft.ats.audit.domain.dto.AuditDto;
import net.apollosoft.ats.audit.domain.dto.CommentDto;
import net.apollosoft.ats.audit.domain.dto.IssueDto;
import net.apollosoft.ats.audit.domain.dto.PublishableDto;
import net.apollosoft.ats.audit.domain.dto.refdata.ParentRiskCategoryDto;
import net.apollosoft.ats.audit.domain.dto.refdata.ParentRiskDto;
import net.apollosoft.ats.audit.domain.hibernate.Action;
import net.apollosoft.ats.audit.domain.hibernate.ActionGroupDivision;
import net.apollosoft.ats.audit.domain.hibernate.Audit;
import net.apollosoft.ats.audit.domain.hibernate.AuditGroupDivision;
import net.apollosoft.ats.audit.domain.hibernate.Comment;
import net.apollosoft.ats.audit.domain.hibernate.Issue;
import net.apollosoft.ats.audit.domain.hibernate.Publishable;
import net.apollosoft.ats.audit.domain.hibernate.refdata.ActionFollowupStatus;
import net.apollosoft.ats.audit.domain.hibernate.refdata.BusinessStatus;
import net.apollosoft.ats.audit.domain.hibernate.refdata.ParentRisk;
import net.apollosoft.ats.audit.domain.hibernate.refdata.ParentRiskCategory;
import net.apollosoft.ats.audit.domain.hibernate.refdata.Rating;
import net.apollosoft.ats.audit.domain.refdata.IParentRisk;
import net.apollosoft.ats.audit.domain.refdata.IParentRiskCategory;
import net.apollosoft.ats.domain.DomainException;
import net.apollosoft.ats.domain.dto.DocumentDto;
import net.apollosoft.ats.domain.dto.security.GroupDivisionDto;
import net.apollosoft.ats.domain.dto.security.UserDto;
import net.apollosoft.ats.domain.hibernate.refdata.ReportType;
import net.apollosoft.ats.domain.hibernate.security.Division;
import net.apollosoft.ats.domain.hibernate.security.Group;
import net.apollosoft.ats.domain.hibernate.security.User;
import net.apollosoft.ats.domain.security.IGroupDivision;
import net.apollosoft.ats.util.BeanUtils;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public final class ConvertUtils extends net.apollosoft.ats.util.ConvertUtils
{

    /** hide ctor. */
    private ConvertUtils()
    {
    	super();
    }

    public static void convert(AuditDto source, Audit target) throws DomainException
    {
        BeanUtils.copyProperties(source, target, IAudit.IGNORE);
        convertPublishable(source, target);
        if (source.getReportType().getId() != null)
        {
            target.setReportType(new ReportType(source.getReportType().getId()));
        }
        if (source.getDocument() != null && source.getDocument().getContent() != null)
        {
            target.setDocument(convert((DocumentDto) source.getDocument()));
        }
    }

    public static void convert(ActionDto source, Action target) throws DomainException
    {
        source.setPublishedBy(target.getPublishedBy());
        source.setPublishedDate(target.getPublishedDate());

        BeanUtils.copyProperties(source, target, IAction.IGNORE);
        convertPublishable(source, target);
        target.setIssue(convert((IssueDto) source.getIssue()));
        if (source.getBusinessStatus().getId() != null)
        {
            target.setBusinessStatus(new BusinessStatus(source.getBusinessStatus().getId()));
        }
        if (source.getFollowupStatus().getId() != null)
        {
            target.setFollowupStatus(new ActionFollowupStatus(source.getFollowupStatus().getId()));
        }
    }

    public static ActionDto convert(Action source) throws DomainException
    {
        if (source == null)
        {
            return null;
        }
        ActionDto target = new ActionDto(source);
        convertPublishable(source, target);
        return target;
    }

    private static Issue convert(IssueDto source) throws DomainException
    {
        if (source == null)
        {
            return null;
        }
        Issue target = new Issue();
        BeanUtils.copyProperties(source, target, IIssue.IGNORE);
        target.setAudit(source.getAudit());
        convertPublishable(source, target);
        return target;
    }

    public static ActionGroupDivision convert(IGroupDivision source) throws DomainException
    {
        if (source == null)
        {
            return null;
        }
        ActionGroupDivision target = new ActionGroupDivision();
        BeanUtils.copyProperties(source, target, IGroupDivision.IGNORE);
        target.setGroup(new Group(source.getGroup().getGroupId()));
        target.setDivision(new Division(source.getDivision().getDivisionId()));
        return target;
    }

    public static void convert(ActionGroupDivision source, GroupDivisionDto target)
        throws DomainException
    {
        BeanUtils.copyProperties(source, target, IGroupDivision.IGNORE);
        target.setGroup(convert(source.getGroup()));
        target.setDivision(convert(source.getDivision()));
    }

    public static void convert(IssueDto source, Issue target) throws DomainException
    {
        BeanUtils.copyProperties(source, target, IIssue.IGNORE);
        convertPublishable(source, target);
        target.setRisk(source.getRisk().getId() == null ? null : new ParentRisk(source.getRisk().getId()));
        target.setRating(source.getRating().getId() == null ? null : new Rating(source.getRating().getId()));
    }

    public static void convert(GroupDivisionDto source, AuditGroupDivision target)
        throws DomainException
    {
        BeanUtils.copyProperties(source, target, IGroupDivision.IGNORE);
        target.setGroup(new Group(source.getGroup().getGroupId()));
        target.setDivision(new Division(source.getDivision().getDivisionId()));
    }

    public static void convert(GroupDivisionDto source, ActionGroupDivision target)
        throws DomainException
    {
        BeanUtils.copyProperties(source, target, IGroupDivision.IGNORE);
        target.setGroup(new Group(source.getGroup().getGroupId()));
        target.setDivision(new Division(source.getDivision().getDivisionId()));
    }

    private static void convert(CommentDto source, Comment target) throws DomainException
    {
        BeanUtils.copyProperties(source, target, IIssue.IGNORE);
    }

    /**
     * 
     * @param source List<IssueDto>
     * @param target List<Issue>
     * @throws DomainException
     */
    public static void convert(List<IIssue> source, Audit entity) throws DomainException
    {
        for (IIssue dto : source)
        {
            if (dto.getId() == null)
            {
                Issue issue = new Issue();
                convert((IssueDto) dto, issue);
                entity.add(issue);
            }
            else
            {
                int index = entity.getIssues().indexOf(dto);
                if (index >= 0)
                {
                    Issue issue = (Issue) entity.getIssues().get(index);
                    convert((IssueDto) dto, issue);
                }
            }
        }
    }

    public static void convertPublishable(PublishableDto<?> source, Publishable<?> target)
        throws DomainException
    {
        if (!target.isPublished() || source.isPublished())
        {
            target.setPublishedBy(source.getPublishedBy() == null ? null : new User(source
                .getPublishedBy().getId()));
            target.setPublishedDate(source.getPublishedDate());
        }
    }

    public static void convertPublishable(Publishable<?> source, PublishableDto<?> target)
        throws DomainException
    {
        if (source.isPublished())
        {
            target.setPublishedBy(new UserDto(source.getPublishedBy()));
            target.setPublishedDate(source.getPublishedDate());
        }
    }

    public static void convertGroupDivisions(List<IGroupDivision> source, Audit entity)
        throws DomainException
    {
        for (IGroupDivision dto : source)
        {
            AuditGroupDivision auditGroupDivision = new AuditGroupDivision();
            convert((GroupDivisionDto) dto, auditGroupDivision);
            entity.addGroupDivision(auditGroupDivision);
        }
    }

    /**
     * 
     * @param comments
     * @param entity
     */
    public static void convert(List<IComment> source, Action entity) throws DomainException
    {
        for (IComment dto : source)
        {
            if (dto.getId() == null)
            {
                Comment comment = new Comment();
                convert((CommentDto) dto, comment);
                entity.addComment(comment);
            }
            else
            {
                int index = entity.getComments().indexOf(dto);
                Comment comment = (Comment) entity.getComments().get(index);
                convert((CommentDto) dto, comment);
            }
        }
    }

    public static void convertActionGroupDivisions(List<IGroupDivision> source, Action entity)
        throws DomainException
    {
        for (IGroupDivision dto : source)
        {
            ActionGroupDivision actionGroupDivision = new ActionGroupDivision();
            convert((GroupDivisionDto) dto, actionGroupDivision);
            entity.getGroupDivisions().add(actionGroupDivision);
        }
    }

    /**
     * 
     * @param source
     * @throws DomainException
     */
    public static List<ParentRiskCategoryDto> convertParentRiskCategory(
        List<ParentRiskCategory> source) throws DomainException
    {
        List<ParentRiskCategoryDto> result = new ArrayList<ParentRiskCategoryDto>();
        //
        for (ParentRiskCategory entity : source)
        {
            ParentRiskCategoryDto parentRiskCategoryDto = convert(entity);
            result.add(parentRiskCategoryDto);
        }

        return result;
    }

    private static ParentRiskCategoryDto convert(IParentRiskCategory entity) throws DomainException
    {
        ParentRiskCategoryDto parentRiskCategoryDto = new ParentRiskCategoryDto(entity);
        return parentRiskCategoryDto;
    }

    public static List<ParentRiskDto> convertParentRisk(List<ParentRisk> source)
        throws DomainException
    {
        List<ParentRiskDto> result = new ArrayList<ParentRiskDto>();
        for (ParentRisk entity : source)
        {
            ParentRiskDto dto = convert(entity);
            result.add(dto);
        }
        return result;
    }

    public static ParentRiskDto convert(IParentRisk source) throws DomainException
    {
        ParentRiskDto dto = new ParentRiskDto(source);
        return dto;
    }

}