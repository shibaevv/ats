package net.apollosoft.ats.audit.validation;

import java.util.ArrayList;
import java.util.List;

import net.apollosoft.ats.audit.domain.IIssue;
import net.apollosoft.ats.audit.domain.dto.AuditDto;
import net.apollosoft.ats.audit.domain.dto.IssueDto;
import net.apollosoft.ats.audit.service.IssueService;
import net.apollosoft.ats.audit.service.ServiceException;
import net.apollosoft.ats.audit.validation.ValidationException.ValidationMessage;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


public class AuditValidator extends BaseValidator<AuditDto>
{

    @Autowired
    @Qualifier("issueValidator")
    //@Resource(name="issueValidator")
    private IssueValidator issueValidator;

    @Autowired
    @Qualifier("issueService")
    //@Resource(name="issueService")
    private IssueService issueService;

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.validation.BaseValidator#validate(java.lang.Object)
     */
    @Override
    public List<ValidationMessage> validate(AuditDto audit)
    {
        List<ValidationMessage> errors = new ArrayList<ValidationMessage>();
        //
        if (StringUtils.isBlank(audit.getName()))
        {
            errors.add(new ValidationMessage("name", "Report Name is empty"));
        }
        //
        if (audit.getReportType().getReportTypeId() == null)
        {
            errors.add(new ValidationMessage("reportType", "Report Type is not selected"));
        }
        //
        if (audit.getIssuedDate() == null)
        {
            errors.add(new ValidationMessage("issuedDate", "Date Issued is empty"));
        }
        //
        if (audit.getDocument().getDocumentId() == null)
        {
            errors.add(new ValidationMessage("document", "Attachment is empty"));
        }
        // validate issues
        try
        {
            List<? extends IIssue> issues = audit.getIssues();
            if (issues.isEmpty())
            {
                issues = issueService.findByAuditId(audit.getAuditId(), null);
            }
            for (int i = 0; i < issues.size(); i++)
            {
                IssueDto issue = (IssueDto) issues.get(i);
                if (issue.getId() != null)
                {
                    List<ValidationMessage> issueErrors = issueValidator.validate(issue);
                    if (!issueErrors.isEmpty())
                    {
                        for (ValidationMessage issueError : issueErrors)
                        {
                            //update name to include parent collection index
                            issueError.setName("issue." + i + "." + issueError.getName());
                        }
                        errors.addAll(issueErrors);
                    }
                }
            }
        }
        catch (ServiceException e)
        {
            errors.add(new ValidationMessage(null, e.getMessage()));
        }
        // test for empty groupDivisions
        if (audit.getGroupDivisions().isEmpty())
        {
            errors.add(new ValidationMessage("groupDivision", "Group/Division is not selected"));
        }
        //
        return errors;
    }

}