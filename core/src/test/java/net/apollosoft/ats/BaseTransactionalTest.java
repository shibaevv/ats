package net.apollosoft.ats;

import javax.sql.DataSource;

import net.apollosoft.ats.util.DateUtils;
import net.apollosoft.ats.util.Pair;
import net.apollosoft.ats.util.ThreadLocalUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;


/**
 * Base class for all transactional test classes.
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
//!!! NOT REQUIRED - already set in base class !!!
//@Transactional
//@TestExecutionListeners(TransactionalTestExecutionListener.class)
@ContextConfiguration(locations =
{
    "/net/apollosoft/ats/applicationContext.test.xml",
    "/net/apollosoft/ats/spring/spring-inc-tx.xml"
})
public abstract class BaseTransactionalTest extends AbstractTransactionalJUnit4SpringContextTests
{

    /** logger. */
    protected final Log LOG = LogFactory.getLog(getClass());

    @BeforeTransaction
    public void beforeTransaction() throws Exception
    {
        ThreadLocalUtils.setDate(DateUtils.getCurrentDateTime());
        ThreadLocalUtils.setUser(new Pair<String>("admin", "admin"));
    }

    @AfterTransaction
    public void afterTransaction() throws Exception
    {
        ThreadLocalUtils.clear();
    }

    /* (non-Javadoc)
     * @see org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests#setDataSource(javax.sql.DataSource)
     */
    @Override
    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource)
    {
        super.setDataSource(dataSource);
    }

}