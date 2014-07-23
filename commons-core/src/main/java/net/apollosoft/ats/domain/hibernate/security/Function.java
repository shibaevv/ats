package net.apollosoft.ats.domain.hibernate.security;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.apollosoft.ats.domain.hibernate.Auditable;
import net.apollosoft.ats.domain.security.IFunction;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Entity
@Table(name = "ats_function")
@org.hibernate.annotations.Entity(mutable = true, optimisticLock = OptimisticLockType.VERSION)
@Where(clause = "")
@Cache(region = "security", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Function extends Auditable<Long> implements IFunction
{

    /** serialVersionUID */
    private static final long serialVersionUID = 2761797607007375974L;

    /** identity persistent field */
    @Id
    @Column(name = "function_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long functionId;

    /** persistent field */
    @Basic()
    @Column(name = "function_name", nullable = false)
    private String name;

    /** persistent field */
    @Basic()
    @Column(name = "function_detail", nullable = true)
    private String detail;

    /** persistent field */
    @Basic()
    @Column(name = "function_module", nullable = false)
    private String module;

    /** persistent field */
    @Basic()
    @Column(name = "function_query", nullable = false)
    private String query;

    /** persistent field */
    @Basic()
    @Column(name = "home", nullable = false)
    @Type(type = "yes_no")
    private boolean home;

    /**
     * 
     */
    public Function()
    {
        super();
    }

    /**
     * @param functionId
     */
    public Function(Long functionId)
    {
        setFunctionId(functionId);
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getFunctionId();
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.hibernate.security.IFunction#getFunctionId()
     */
    public Long getFunctionId()
    {
        return functionId;
    }

    /**
     *
     * @param functionId
     */
    public void setFunctionId(Long functionId)
    {
        this.functionId = functionId;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.hibernate.security.IFunction#getName()
     */
    public String getName()
    {
        return this.name;
    }

    public void setName(String login)
    {
        this.name = login;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.security.IFunction#getDetail()
     */
    public String getDetail()
    {
        return detail;
    }

    /**
     * @param detail the detail to set
     */
    public void setDetail(String detail)
    {
        this.detail = detail;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.security.IFunction#getModule()
     */
    public String getModule()
    {
        return module;
    }

    /**
     * @param module the module to set
     */
    public void setModule(String module)
    {
        this.module = module;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.security.IFunction#getQuery()
     */
    public String getQuery()
    {
        return query;
    }

    /**
     * @param query the query to set
     */
    public void setQuery(String query)
    {
        this.query = query;
    }

    /**
     * @return the home
     */
    public boolean isHome()
    {
        return home;
    }

    /**
     * @param home the home to set
     */
    public void setHome(boolean home)
    {
        this.home = home;
    }

}