package net.apollosoft.ats.audit.domain.dto.refdata;

import net.apollosoft.ats.audit.domain.refdata.IParentRisk;
import net.apollosoft.ats.audit.domain.refdata.IParentRiskCategory;
import net.apollosoft.ats.domain.DomainException;
import net.apollosoft.ats.domain.dto.AuditableDto;
import net.apollosoft.ats.util.BeanUtils;
import net.apollosoft.ats.util.Formatter;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class ParentRiskDto extends AuditableDto<Long> implements IParentRisk
{

    private Long id;

    private String name;

    private IParentRiskCategory category;

    /**
     * 
     */
    public ParentRiskDto()
    {
        super();
    }

    /**
     * 
     * @param source
     * @throws DomainException
     */
    public ParentRiskDto(IParentRisk source) throws DomainException
    {
        BeanUtils.copyProperties(source, this, IParentRisk.IGNORE);
        category = source.getCategory() == null ? null : new ParentRiskCategoryDto(source
            .getCategory());
        //TODO: re-factor to jsp ${fn:replace(entity.name,'"','\'')}
        name = Formatter.formatJson(name, true);
        //detail = Formatter.formatJson(detail, false);
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getParentRiskId();
    }

    /**
     * @return the id
     */
    public Long getParentRiskId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setParentRiskId(Long id)
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

    /**
     * @return the category
     */
    public IParentRiskCategory getCategory()
    {
        if(category == null)
        {
            category = new ParentRiskCategoryDto();
        }
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(IParentRiskCategory category)
    {
        this.category = category;
    }

}