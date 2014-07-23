package net.apollosoft.ats.audit.domain.dto.refdata;

import net.apollosoft.ats.audit.domain.refdata.IParentRiskCategory;
import net.apollosoft.ats.domain.DomainException;
import net.apollosoft.ats.domain.dto.AuditableDto;
import net.apollosoft.ats.util.BeanUtils;
import net.apollosoft.ats.util.Formatter;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class ParentRiskCategoryDto extends AuditableDto<Long> implements IParentRiskCategory
{

    private Long id;

    private String name;

    /**
     * 
     */
    public ParentRiskCategoryDto()
    {
        super();
    }

    /**
     * 
     * @param source
     * @throws DomainException
     */
    public ParentRiskCategoryDto(IParentRiskCategory source) throws DomainException
    {
        BeanUtils.copyProperties(source, this, IParentRiskCategory.IGNORE);
        //TODO: re-factor to jsp ${fn:replace(entity.name,'"','\'')}
        name = Formatter.formatJson(name, true);
        //detail = Formatter.formatJson(detail, false);
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getParentRiskCategoryId();
    }

    /**
     * @return the id
     */
    public Long getParentRiskCategoryId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setParentRiskCategoryId(Long id)
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