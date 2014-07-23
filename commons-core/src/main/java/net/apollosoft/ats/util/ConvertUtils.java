package net.apollosoft.ats.util;

import java.util.ArrayList;
import java.util.List;

import net.apollosoft.ats.domain.DomainException;
import net.apollosoft.ats.domain.IDocument;
import net.apollosoft.ats.domain.ITemplate;
import net.apollosoft.ats.domain.dto.DocumentDto;
import net.apollosoft.ats.domain.dto.TemplateDto;
import net.apollosoft.ats.domain.dto.refdata.UserTypeDto;
import net.apollosoft.ats.domain.dto.security.DivisionDto;
import net.apollosoft.ats.domain.dto.security.GroupDivisionDto;
import net.apollosoft.ats.domain.dto.security.GroupDto;
import net.apollosoft.ats.domain.dto.security.RoleDto;
import net.apollosoft.ats.domain.dto.security.UserGroupDivisionDto;
import net.apollosoft.ats.domain.hibernate.Document;
import net.apollosoft.ats.domain.hibernate.Template;
import net.apollosoft.ats.domain.hibernate.security.Division;
import net.apollosoft.ats.domain.hibernate.security.Group;
import net.apollosoft.ats.domain.hibernate.security.Role;
import net.apollosoft.ats.domain.hibernate.security.UserGroupDivision;
import net.apollosoft.ats.domain.security.IDivision;
import net.apollosoft.ats.domain.security.IGroup;
import net.apollosoft.ats.domain.security.IGroupDivision;
import net.apollosoft.ats.domain.security.IRole;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class ConvertUtils
{

    /** hide ctor. */
    protected ConvertUtils()
    {
    	super();
    }

    /**
     * 
     * @param source
     * @throws DomainException
     */
    public static DivisionDto convert(IDivision source) throws DomainException
    {
        if (source == null)
        {
            return null;
        }
        IGroup group = source.getGroup();
        GroupDto groupDto = group == null ? null : new GroupDto(group);
        //
        DivisionDto divisionDto = new DivisionDto(source);
        divisionDto.setGroup(groupDto);
        return divisionDto;
    }

    /**
     * 
     * @param source
     * @throws DomainException
     */
    public static List<DivisionDto> convertDivision(List<Division> source) throws DomainException
    {
        List<DivisionDto> result = new ArrayList<DivisionDto>();
        //
        for (IDivision division : source)
        {
            DivisionDto divisionDto = convert(division);
            result.add(divisionDto);
        }
        return result;
    }

    /**
     * 
     * @param source
     * @return
     * @throws DomainException
     */
    public static Document convert(DocumentDto source) throws DomainException
    {
        if (source == null)
        {
            return null;
        }
        Document target = new Document();
        BeanUtils.copyProperties(source, target, IDocument.IGNORE);
        target.setDetail(source.getDetail());
        target.setContent(source.getContent());
        return target;
    }

    /**
     * 
     * @param source
     * @return
     * @throws DomainException
     */
    public static DocumentDto convert(Document source) throws DomainException
    {
        if (source == null)
        {
            return null;
        }
        DocumentDto target = new DocumentDto(source);
        BeanUtils.copyProperties(source, target, IDocument.IGNORE);
        target.setDetail(source.getDetail());
        target.setContent(source.getContent());
        return target;
    }

    /**
     * 
     * @param source
     * @throws DomainException
     */
    public static GroupDto convert(IGroup source) throws DomainException
    {
        if (source == null)
        {
            return null;
        }
        GroupDto groupDto = new GroupDto(source);
        return groupDto;
    }

    /**
     * 
     * @param source
     * @throws DomainException
     */
    public static List<GroupDto> convertGroup(List<Group> source) throws DomainException
    {
        List<GroupDto> result = new ArrayList<GroupDto>();
        //
        GroupDto thematic = null;
        for (Group entity : source)
        {
            GroupDto groupDto = convert(entity);
            if (IGroup.GLOBAL_ID.equals(groupDto.getId()))
            {

                thematic = groupDto;
            }
            else
            {
                result.add(groupDto);
            }
        }
        //move Thematic up (must exist)
        if (thematic == null)
        {
            throw new DomainException("No Thematic group found.");
        }
        result.add(0, thematic);
        //
        return result;
    }

    /**
     * 
     * @param source - eg Audit/Action/User GroupDivision
     * @param target
     * @throws DomainException
     */
    public static void convert(IGroupDivision source, GroupDivisionDto target)
        throws DomainException
    {
        BeanUtils.copyProperties(source, target, IGroupDivision.IGNORE);
        target.setGroup(convert(source.getGroup()));
        target.setDivision(convert(source.getDivision()));
    }

    /**
     * 
     * @param source
     * @return
     * @throws DomainException
     */
    public static void convert(UserGroupDivision source, UserGroupDivisionDto target)
        throws DomainException
    {
        BeanUtils.copyProperties(source, target, IGroupDivision.IGNORE);
        target.setGroup(convert(source.getGroup()));
        target.setDivision(convert(source.getDivision()));
        target.setUserType(new UserTypeDto(source.getUserType()));
    }

    /**
     * 
     * @param source
     * @param target
     * @throws DomainException
     */
    public static void convert(RoleDto source, Role target) throws DomainException
    {
        BeanUtils.copyProperties(source, target, IRole.IGNORE);
    }

    public static List<TemplateDto> convertTemplate(List<Template> source) throws DomainException
    {
        List<TemplateDto> result = new ArrayList<TemplateDto>();
        for (Template entity : source)
        {
            TemplateDto dto = convert(entity);
            result.add(dto);
        }
        return result;
    }

    public static TemplateDto convert(Template source) throws DomainException
    {
        TemplateDto result = new TemplateDto(source);
        result.setContent(source.getContent());
        return result;
    }

    public static void convert(TemplateDto source, Template target) throws DomainException
    {
        BeanUtils.copyProperties(source, target, ITemplate.IGNORE);
        target.setContent(source.getContent());
    }

}