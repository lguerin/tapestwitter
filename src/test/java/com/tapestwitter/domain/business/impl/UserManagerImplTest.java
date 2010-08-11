/**
 * 
 */
package com.tapestwitter.domain.business.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import org.unitils.UnitilsTestNG;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;
import org.unitils.mock.MockUnitils;
import org.unitils.mock.annotation.Dummy;

import com.tapestwitter.components.utils.DateIntervalUtilsTest;
import com.tapestwitter.domain.dao.IUserDAO;
import com.tapestwitter.domain.model.User;

/**
 * classe de test pour le service {@link UserManagerImpl}
 * 
 * @author ldoin
 */
public class UserManagerImplTest extends UnitilsTestNG
{
    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DateIntervalUtilsTest.class);

    @TestedObject
    private UserManagerImpl userManager;

    @InjectIntoByType
    private Mock<IUserDAO> mockUserDao;

    /**
     * user utilise pour les test
     */
    @Dummy
    private User dummyUser;

    @AfterTest
    public void tearDown()
    {
        MockUnitils.assertNoMoreInvocations();

        if (LOGGER.isDebugEnabled())
        {
            MockUnitils.logFullScenarioReport();
        }
    }

    @Test
    public void testFindByUsername()
    {
        String userName = "ldoin";
        mockUserDao.returns(dummyUser).findByUsername(userName);

        User actualUser = userManager.findByUsername(userName);
        Assert.assertEquals(actualUser, dummyUser);
        mockUserDao.assertInvoked().findByUsername(userName);
    }

    @Test
    public void testIsAvailableName()
    {
        String userName = "ldoin";

        mockUserDao.onceReturns(null).findByUsername(userName);
        Assert.assertTrue(userManager.isAvailableName(userName));
        mockUserDao.assertInvoked().findByUsername(userName);

        mockUserDao.onceReturns(dummyUser).findByUsername(userName);
        Assert.assertFalse(userManager.isAvailableName(userName));
        mockUserDao.assertInvoked().findByUsername(userName);

    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIsAvailableName2()
    {
        mockUserDao.raises(IllegalArgumentException.class).findByUsername(null);
        Assert.assertTrue(userManager.isAvailableName(null));
        mockUserDao.assertInvoked().findByUsername(null);
    }

}
