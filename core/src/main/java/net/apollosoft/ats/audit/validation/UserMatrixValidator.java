package net.apollosoft.ats.audit.validation;

import java.util.ArrayList;
import java.util.List;

import net.apollosoft.ats.audit.dao.UserMatrixDao;
import net.apollosoft.ats.audit.validation.ValidationException.ValidationMessage;
import net.apollosoft.ats.domain.dto.refdata.ReportTypeDto;
import net.apollosoft.ats.domain.dto.security.DivisionDto;
import net.apollosoft.ats.domain.dto.security.GroupDto;
import net.apollosoft.ats.domain.dto.security.RoleDto;
import net.apollosoft.ats.domain.dto.security.UserDto;
import net.apollosoft.ats.domain.dto.security.UserMatrixDto;
import net.apollosoft.ats.domain.hibernate.security.UserMatrix;
import net.apollosoft.ats.domain.security.IDivision;
import net.apollosoft.ats.domain.security.IGroup;
import net.apollosoft.ats.domain.security.IUserMatrix;
import net.apollosoft.ats.domain.security.IRole.RoleEnum;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 * TODO: read message by key from Resource
 */
public class UserMatrixValidator extends BaseValidator<IUserMatrix>
{

    @Autowired
    @Qualifier("userMatrixDao")
    //@Resource(name="userMatrixDao")
    private UserMatrixDao userMatrixDao;

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.validation.BaseValidator#validate(java.lang.Object)
     */
    @Override
    public List<ValidationMessage> validate(IUserMatrix dto)
    {
        List<ValidationMessage> errors = new ArrayList<ValidationMessage>();
        try
        {
            String userId = dto.getUser().getId();
            Long userMatrixId = dto.getUserMatrixId();
            UserMatrix entity = userMatrixDao.findById(userMatrixId);
            if (StringUtils.isBlank(userId))
            {
                errors.add(new ValidationMessage("userMatrix", "No User selected"));
            }
            if (userMatrixId != null && entity == null)
            {
                errors.add(new ValidationMessage("userMatrix", "No userMatrixId: " + userMatrixId));
            }
            //
            // group can be null
            Long groupId = dto.getGroup().getId();
            //
            // division can be null
            Long divisionId = dto.getDivision().getId();
            //
            // role validation
            Long roleId = dto.getRole().getId();
            if (roleId == null)
            {
                errors.add(new ValidationMessage("roleId", "No Role selected"));
            }
            else
            {
                if (RoleEnum.REPORT_OWNER.ordinal() == roleId)
                {
                    if (groupId != null)
                    {
                        errors.add(new ValidationMessage("groupId", "Group must be '"
                            + IGroup.GLOBAL_NAME + "'"));
                    }
                    if (divisionId != null)
                    {
                        errors.add(new ValidationMessage("divisionId", "Division must be '"
                            + IDivision.ALL_NAME + "'"));
                    }
                }
                else if (RoleEnum.REPORT_TEAM.ordinal() == roleId)
                {
                    if (groupId != null)
                    {
                        errors.add(new ValidationMessage("groupId", "Group must be '"
                            + IGroup.GLOBAL_NAME + "'"));
                    }
                    if (divisionId != null)
                    {
                        errors.add(new ValidationMessage("divisionId", "Division must be '"
                            + IDivision.ALL_NAME + "'"));
                    }
                }
                else if (RoleEnum.OVERSIGHT_TEAM.ordinal() == roleId)
                {
                    if (groupId == null)
                    {
                        errors.add(new ValidationMessage("groupId",
                            "Must be a valid group (including '" + IGroup.GLOBAL_NAME + "')"));
                    }
                }
            }
            //
            // reportType validation
            Long reportTypeId = dto.getReportType().getId();
            if (reportTypeId == null)
            {
                errors.add(new ValidationMessage("reportTypeId", "No Report Type selected"));
            }
            else
            {
                List<ReportTypeDto> allowedReportTypes = getSecurityService().findUserReportTypes();
                if (!allowedReportTypes.contains(dto.getReportType()))
                {
                    errors.add(new ValidationMessage("reportTypeId", "Report Type is not allowed"));
                }
            }
            //
            // test for unique
            UserMatrixDto userMatrixDto = new UserMatrixDto();
            userMatrixDto.setUser(new UserDto(userId));
            userMatrixDto.setRole(new RoleDto(roleId));
            userMatrixDto.setReportType(new ReportTypeDto(reportTypeId));
            userMatrixDto.setGroup(new GroupDto(groupId));
            userMatrixDto.setDivision(new DivisionDto(divisionId));
            UserMatrix uniqueEntity = userMatrixDao.findByUniqueKey(userMatrixDto);
            if (uniqueEntity != null)
            {
                //check if it is not the same id
                if (!uniqueEntity.getUserMatrixId().equals(dto.getUserMatrixId()))
                {
                    errors.add(new ValidationMessage("user", "Not Unique UserMatrix"));
                    errors.add(new ValidationMessage("userMatrix", "Not Unique UserMatrix"));
                }
            }
        }
        catch (Exception e)
        {
            errors.add(new ValidationMessage("userMatrix", e.getMessage()));
        }
        return errors;
    }

}