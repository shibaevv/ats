package net.apollosoft.ats.audit.service;

import net.apollosoft.ats.audit.validation.ValidationException;
import net.apollosoft.ats.domain.security.IGroupDivision;

public interface GroupDivisionService
{

    void delete(IGroupDivision groupDivision) throws ServiceException, ValidationException;

}
