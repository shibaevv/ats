package net.apollosoft.ats.domain.dto.refdata;

import net.apollosoft.ats.domain.DomainException;
import net.apollosoft.ats.domain.dto.AuditableDto;
import net.apollosoft.ats.domain.refdata.IReportType;
import net.apollosoft.ats.util.BeanUtils;
import net.apollosoft.ats.util.Formatter;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class ReportTypeDto extends AuditableDto<Long> implements IReportType
{

    /** ALL */
    public static final ReportTypeDto ALL = new ReportTypeDto(null, "");

    private Long id;

    private String name;

    /**
     * 
     */
    public ReportTypeDto()
    {
        super();
    }

    public ReportTypeDto(Long id)
    {
        this.id = id;
    }

    private ReportTypeDto(Long id, String name)
    {
        this.id = id;
        this.name = name;
    }

    /**
     * 
     * @param source
     * @throws DomainException
     */
    public ReportTypeDto(IReportType source) throws DomainException
    {
        BeanUtils.copyProperties(source, this, IReportType.IGNORE);
        //TODO: re-factor to jsp ${fn:replace(entity.name,'"','\'')}
        name = Formatter.formatJson(name, true);
        //detail = Formatter.formatJson(detail, false);
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getReportTypeId();
    }

    /**
     * @return the id
     */
    public Long getReportTypeId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setReportTypeId(Long id)
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