package net.apollosoft.ats.domain.dto.security;

import net.apollosoft.ats.domain.DomainException;
import net.apollosoft.ats.domain.dto.AuditableDto;
import net.apollosoft.ats.domain.dto.refdata.ReportTypeDto;
import net.apollosoft.ats.domain.refdata.IReportType;
import net.apollosoft.ats.domain.security.IDivision;
import net.apollosoft.ats.domain.security.IGroup;
import net.apollosoft.ats.domain.security.IRole;
import net.apollosoft.ats.domain.security.IUser;
import net.apollosoft.ats.domain.security.IUserMatrix;
import net.apollosoft.ats.util.BeanUtils;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class UserMatrixDto extends AuditableDto<Long> implements IUserMatrix
{

    private Long userMatrixId;

    private IUser user;

    private IRole role;

    private IReportType reportType;

    private IGroup group;

    private IDivision division;

    /**
     * 
     */
    public UserMatrixDto()
    {
        super();
    }

    /**
     * 
     * @param source
     * @throws DomainException
     */
    public UserMatrixDto(IUserMatrix source) throws DomainException
    {
        BeanUtils.copyProperties(source, this, IUserMatrix.IGNORE);
        BeanUtils.copyProperties(source.getUser(), this.getUser(), IUser.IGNORE);
        BeanUtils.copyProperties(source.getRole(), this.getRole(), IRole.IGNORE);
        BeanUtils.copyProperties(source.getReportType(), this.getReportType(), IReportType.IGNORE);
        if (source.getGroup() != null)
        {
            BeanUtils.copyProperties(source.getGroup(), this.getGroup(), IGroup.IGNORE);
        }
        if (source.getDivision() != null)
        {
            BeanUtils.copyProperties(source.getDivision(), this.getDivision(), IDivision.IGNORE);
        }
    }

    /**
     * @return the id
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
        if (user == null)
        {
            user = new UserDto();
        }
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
        if (role == null)
        {
            role = new RoleDto();
        }
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
     * @return the group
     */
    public IGroup getGroup()
    {
        if (group == null)
        {
            group = new GroupDto();
        }
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
        if (division == null)
        {
            division = new DivisionDto();
        }
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
     * @return the GroupDivision
     */
    public GroupDivisionDto getGroupDivision()
    {
        GroupDivisionDto result = new GroupDivisionDto();
        result.setGroup(group);
        result.setDivision(division);
        return result;
    }

}