package net.apollosoft.ats.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

import junit.framework.Assert;
import net.apollosoft.ats.BaseTransactionalTest;
import net.apollosoft.ats.audit.dao.AuditDao;
import net.apollosoft.ats.audit.domain.IAudit;
import net.apollosoft.ats.audit.domain.dto.AuditDto;
import net.apollosoft.ats.audit.domain.hibernate.Audit;
import net.apollosoft.ats.audit.domain.refdata.IBusinessStatus.BusinessStatusEnum;
import net.apollosoft.ats.audit.search.ISortBy;
import net.apollosoft.ats.audit.search.ReportSearchCriteria;
import net.apollosoft.ats.audit.service.AuditService;
import net.apollosoft.ats.audit.validation.ValidationException;
import net.apollosoft.ats.dao.AuditDaoTest;
import net.apollosoft.ats.domain.ContentTypeEnum;
import net.apollosoft.ats.domain.hibernate.refdata.ReportType;
import net.apollosoft.ats.domain.refdata.IReportType.ReportTypeEnum;
import net.apollosoft.ats.search.IDir;
import net.apollosoft.ats.search.Pagination;
import net.apollosoft.ats.util.DateUtils;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;


/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class AuditServiceTest extends BaseTransactionalTest
{

    /** id */
    /*public*/static Long ID;

    @Autowired
    @Qualifier("auditService")
    //@Resource(name="auditService")
    private AuditService auditService;

    @Autowired
    @Qualifier("auditDao")
    //@Resource(name="auditDao")
    private AuditDao auditDao;

    @Before
    public void setUp() throws Exception
    {
        try
        {
            Audit entity = ID == null ? auditDao.findByReference(AuditDaoTest.reference) : auditDao
                .findById(ID);
            if (entity == null)
            {
                entity = new Audit();
                entity.setName("AuditDaoTest");
                entity.setReference(AuditDaoTest.reference);
                entity.setDetail("detail-" + System.currentTimeMillis());
                entity.setIssuedDate(DateUtils.getCurrentDate());
                entity.setSource(IAudit.SOURCE_ATS);
                entity.setReportType(new ReportType(new Long(ReportTypeEnum.AUDIT.ordinal())));
                auditDao.save(entity);
                ID = entity.getId();
            }
            else
            {
                ID = entity.getId();
            }
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.AuditService#findAuditByNameLike(java.lang.String)}.
     */
    @Test
    public final void testFindNameLike()
    {
        try
        {
            //fail("Not yet implemented"); // TODO
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.AuditService#find(net.apollosoft.ats.audit.search.AuditSearchCriteria)}.
     */
    @Test
    public final void testFind()
    {
        try
        {
            ReportSearchCriteria criteria = new ReportSearchCriteria();
            Pagination p = new Pagination(25, 10, ISortBy.AUDIT_ISSUED_DATE_SQL, IDir.ASC);
            criteria.setPagination(p);
            List<AuditDto> result = auditService.find(criteria);
            //Assert.assertTrue("No record(s) found.", !result.isEmpty());
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.AuditService#findById(java.lang.Long)}.
     */
    @Test
    public final void testFindById()
    {
        try
        {
            //fail("Not yet implemented"); // TODO
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.AuditService#addGroups(net.apollosoft.ats.audit.domain.hibernate.Audit, java.util.Set)}.
     */
    @Test
    public final void testAddGroups()
    {
        try
        {
            //fail("Not yet implemented"); // TODO
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.AuditService#addDivisions(net.apollosoft.ats.audit.domain.hibernate.Audit, java.util.Set)}.
     */
    @Test
    public final void testAddDivisions()
    {
        try
        {
            //fail("Not yet implemented"); // TODO
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.AuditService#save(net.apollosoft.ats.audit.domain.dto.AuditDto , net.apollosoft.ats.audit.domain.dto.AuditDto
    )}.
     */
    @Test
    @Rollback(value = false)
    public final void testSave()
    {
        try
        {
            Audit entity = auditDao.findById(ID);
            AuditDto audit = new AuditDto(entity);
            //  changed
            auditService.save(audit);
        }
        catch (ValidationException e)
        {
            //not an error
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public final void testExport()
    {
        try
        {
            ReportSearchCriteria criteria = new ReportSearchCriteria();
            criteria.setBusinessStatusId(new Long(BusinessStatusEnum.NOT_ACTIONED.ordinal()));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            auditService.export(criteria, output, ContentTypeEnum.APPLICATION_EXCEL);

        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public final void testPublish()
    {
        try
        //TODO
        {
            //            Audit audit = auditDao.findByReference(AuditDaoTest.reference);
            //            AuditDto auditDto = new AuditDto(audit);
            //            auditService.publish(auditDto);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

}