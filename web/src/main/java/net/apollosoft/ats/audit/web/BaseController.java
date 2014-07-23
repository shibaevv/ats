package net.apollosoft.ats.audit.web;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

import net.apollosoft.ats.audit.security.UserPreferences;
import net.apollosoft.ats.audit.service.EntityService;
import net.apollosoft.ats.audit.service.SecurityService;
import net.apollosoft.ats.audit.service.ServiceException;
import net.apollosoft.ats.domain.dto.security.GroupDivisionDto;
import net.apollosoft.ats.domain.security.IDivision;
import net.apollosoft.ats.domain.security.IGroup;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.ModelMap;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public abstract class BaseController
{

    /** logger. */
    protected final Log LOG = LogFactory.getLog(getClass());

    private final BeanFactory beanFactory;

    private final EntityService entityService;

    private final SecurityService securityService;

    @Autowired
    public BaseController(@Qualifier("beanFactory") BeanFactory beanFactory)
    {
        this.beanFactory = beanFactory;
        this.entityService = (EntityService) beanFactory.getBean("entityService");
        this.securityService = (SecurityService) beanFactory.getBean("securityService");
    }

    /**
     * @return the userPreferences
     */
    protected UserPreferences getUserPreferences()
    {
        return (UserPreferences) beanFactory.getBean("userPreferences");
    }

    /**
     * @return the securityService
     */
    protected SecurityService getSecurityService()
    {
        return securityService;
    }

    /**
     * @return the entityService
     */
    public EntityService getEntityService()
    {
        return entityService;
    }

    /**
     * 
     * @param model
     * @param selectedName
     * @param availableName
     * @throws ServiceException
     */
    protected void addGroupDivisions(ModelMap model, String selectedName, String availableName)
        throws ServiceException
    {
        UserPreferences prefs = getUserPreferences();
        //
        String[] groupDivisions = prefs.getGroupDivisions();
        boolean initialSearch = groupDivisions == null;
        //eg group/division from high priority role
        Set<GroupDivisionDto> userGroupDivisions = securityService.findUserGroupDivisions(initialSearch ? true : null);
        if (!initialSearch) // indicate that was searched at least once
        {
            Arrays.sort(groupDivisions);
            Iterator<GroupDivisionDto> iter = userGroupDivisions.iterator();
            while (iter.hasNext())
            {
                GroupDivisionDto item = iter.next();
                IGroup g = item.getGroup();
                IDivision d = item.getDivision();
                //groupId,divisionId (can be null)
                String s = "" + (g == null ? "" : g.getId()) + "," + (d == null ? "" : d.getId());
                if (Arrays.binarySearch(groupDivisions, s) < 0)
                {
                    iter.remove();
                }
            }
        }
        Set<GroupDivisionDto> availableGroupDivisions = securityService.findUserGroupDivisions(null);
        availableGroupDivisions.removeAll(userGroupDivisions);
        model.addAttribute(selectedName, userGroupDivisions);
        model.addAttribute(availableName, availableGroupDivisions);
    }

}