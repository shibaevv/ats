package net.apollosoft.ats.domain.hibernate.refdata;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import net.apollosoft.ats.domain.hibernate.Auditable;
import net.apollosoft.ats.domain.refdata.IReportType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OptimisticLockType;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Entity
@Table(name = "ats_report_type")
@org.hibernate.annotations.Entity(mutable = true, optimisticLock = OptimisticLockType.VERSION)
@Cache(region = "refdata", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class ReportType extends Auditable<Long> implements IReportType
{

    /** serialVersionUID */
    private static final long serialVersionUID = 4717090892053217393L;

    /** identity persistent field */
    @Id
    @Column(name = "report_type_id", nullable = false)
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long reportTypeId;

    /** persistent field */
    @Basic()
    @Column(name = "report_type_name", nullable = false)
    private String name;

    /**
     * 
     */
    public ReportType()
    {
        super();
    }

    /**
     * @param reportTypeId
     */
    public ReportType(Long reportTypeId)
    {
        setReportTypeId(reportTypeId);
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getReportTypeId();
    }

    /**
     *
     * @return
     */
    public Long getReportTypeId()
    {
        if (reportTypeId != null && reportTypeId.longValue() == 0L)
        {
            reportTypeId = null;
        }
        return reportTypeId;
    }

    /**
     *
     * @param reportTypeId
     */
    public void setReportTypeId(Long reportTypeId)
    {
        this.reportTypeId = reportTypeId;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.hibernate.refdata.IReportType#getName()
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