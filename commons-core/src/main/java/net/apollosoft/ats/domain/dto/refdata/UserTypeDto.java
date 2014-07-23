package net.apollosoft.ats.domain.dto.refdata;

import net.apollosoft.ats.domain.DomainException;
import net.apollosoft.ats.domain.dto.AuditableDto;
import net.apollosoft.ats.domain.refdata.IUserType;
import net.apollosoft.ats.util.BeanUtils;
import net.apollosoft.ats.util.Formatter;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class UserTypeDto extends AuditableDto<Long> implements IUserType
{

    private Long id;

    private String name;

    /**
     * 
     */
    public UserTypeDto()
    {
        super();
    }

    /**
     * 
     * @param source
     * @throws DomainException
     */
    public UserTypeDto(IUserType source) throws DomainException
    {
        BeanUtils.copyProperties(source, this, IUserType.IGNORE);
        //TODO: re-factor to jsp ${fn:replace(entity.name,'"','\'')}
        name = Formatter.formatJson(name, true);
        //detail = Formatter.formatJson(detail, false);
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getUserTypeId();
    }

    /**
     * @return the id
     */
    public Long getUserTypeId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setUserTypeId(Long id)
    {
        this.id = id;
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

}