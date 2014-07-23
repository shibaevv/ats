package net.apollosoft.ats.dao;

import net.apollosoft.ats.BaseTransactionalTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@RunWith(Suite.class)
@SuiteClasses(
{
    AuditDaoTest.class, IssueDaoTest.class, ActionDaoTest.class, CommentDaoTest.class
})
//@org.junit.Ignore
public class AaaDaoTest extends BaseTransactionalTest
{
    //will load tests in correct sequence
}