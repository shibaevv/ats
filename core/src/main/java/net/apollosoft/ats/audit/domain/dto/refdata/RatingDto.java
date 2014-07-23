package net.apollosoft.ats.audit.domain.dto.refdata;

import net.apollosoft.ats.audit.domain.refdata.IRating;
import net.apollosoft.ats.domain.DomainException;
import net.apollosoft.ats.domain.dto.AuditableDto;
import net.apollosoft.ats.util.BeanUtils;
import net.apollosoft.ats.util.Formatter;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class RatingDto extends AuditableDto<Long> implements IRating
{

    /** All existing Rating */
    public static final RatingDto ALL = new RatingDto(null, "");

    private Long id;

    private String name;

    /**
     * 
     */
    public RatingDto()
    {
        super();
    }

    public RatingDto(Long id, String name)
    {
        this.id = id;
        this.name = name;
    }

    /**
     * 
     * @param source
     * @throws DomainException
     */
    public RatingDto(IRating source) throws DomainException
    {
        BeanUtils.copyProperties(source, this, IRating.IGNORE);
        //TODO: re-factor to jsp ${fn:replace(entity.name,'"','\'')}
        name = Formatter.formatJson(name, true);
        //detail = Formatter.formatJson(detail, false);
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getRatingId();
    }

    /**
     * @return the id
     */
    public Long getRatingId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setRatingId(Long id)
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