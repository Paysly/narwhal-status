package com.narwhal.health.backend.dao;

import com.narwhal.basics.core.rest.exceptions.api.BadRequestException;
import com.narwhal.basics.core.rest.guice.SubModule;
import com.narwhal.basics.core.rest.testutils.BaseDaoTest;
import com.narwhal.basics.core.rest.utils.DateUtils;
import com.narwhal.health.backend.guice.BackendModule;
import com.narwhal.health.backend.model.HealthCheck;
import com.narwhal.health.backend.types.HealthCheckType;
import com.narwhal.health.backend.types.HealthStatusType;
import com.narwhal.health.backend.types.ServerType;
import org.junit.Test;

import java.util.*;

import static com.narwhal.basics.core.rest.utils.DateUtils.decreaseOneDay;
import static com.narwhal.basics.core.rest.utils.DateUtils.getDayAMonthAgo;
import static org.junit.Assert.assertEquals;


public class HealthCheckDaoObjectifyImplTest extends BaseDaoTest<HealthCheck, HealthCheckDao> {


    public HealthCheckDaoObjectifyImplTest() {
        super(HealthCheck.class, HealthCheckDao.class);
    }

    @Override
    public SubModule getGuiceSubModule() {
        return new BackendModule();
    }

    @Override
    public HealthCheck newInstance() {
        Calendar cal = Calendar.getInstance();
        Date date = com.narwhal.basics.core.rest.utils.DateUtils.todayNoTime();
        cal.setTime(date);
        cal.set(Calendar.MILLISECOND, 0);
        //
        Calendar calMin = Calendar.getInstance();
        Date dateMin = new Date();
        calMin.set(Calendar.MINUTE, dateMin.getMinutes());
        //
        Calendar calHour = Calendar.getInstance();
        Date dateHour = new Date();
        calHour.set(Calendar.HOUR_OF_DAY, dateHour.getHours());
        //
        HealthCheck healthCheck = new HealthCheck();
        healthCheck.setStatusType(HealthStatusType.ONLINE);
        healthCheck.setHealthCheckType(HealthCheckType.ONE_HOUR);
        healthCheck.init(ServerType.ADMIN_PRODUCTION, cal.getTime().getTime(), calHour.get(Calendar.HOUR_OF_DAY), calMin.get(Calendar.MINUTE));
        //
        return healthCheck;
    }

    @Test(expected = BadRequestException.class)
    public void test_getByTypeFiveMinutes_null_serverType() {
        dao.getByTypeFiveMinutes(null, new Date().getTime(), 5);
    }

    @Test
    public void test_getByTypeFiveMinutes_success() {
        Date date = new Date();
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        // OK
        HealthCheck object1 = new HealthCheck();
        object1.setId("1");
        object1.setStatusType(HealthStatusType.UNKNOWN);
        object1.setServerType(ServerType.ADMIN_PRODUCTION);
        object1.setHealthCheckType(HealthCheckType.FIVE_MINUTES);
        object1.setHour(5);
        object1.setDate(cal.getTime().getTime());
        // different server type
        HealthCheck object2 = new HealthCheck();
        object2.setId("2");
        object2.setStatusType(HealthStatusType.UNKNOWN);
        object2.setServerType(ServerType.NOTIFICATION);
        object2.setHealthCheckType(HealthCheckType.FIVE_MINUTES);
        object2.setHour(5);
        object2.setDate(cal.getTime().getTime());
        // different health check type
        HealthCheck object3 = new HealthCheck();
        object3.setId("3");
        object3.setStatusType(HealthStatusType.UNKNOWN);
        object3.setServerType(ServerType.ADMIN_PRODUCTION);
        object3.setHealthCheckType(HealthCheckType.ONE_HOUR);
        object3.setHour(5);
        object3.setDate(cal.getTime().getTime());
        // different hour
        HealthCheck object4 = new HealthCheck();
        object4.setId("4");
        object4.setStatusType(HealthStatusType.UNKNOWN);
        object4.setServerType(ServerType.ADMIN_PRODUCTION);
        object4.setHealthCheckType(HealthCheckType.FIVE_MINUTES);
        object4.setHour(7);
        object4.setDate(cal.getTime().getTime());
        // OK
        HealthCheck object5 = new HealthCheck();
        object5.setId("5");
        object5.setStatusType(HealthStatusType.UNKNOWN);
        object5.setServerType(ServerType.ADMIN_PRODUCTION);
        object5.setHealthCheckType(HealthCheckType.FIVE_MINUTES);
        object5.setHour(5);
        object5.setDate(cal.getTime().getTime());
        // different date
        HealthCheck object6 = new HealthCheck();
        object6.setId("6");
        object6.setStatusType(HealthStatusType.UNKNOWN);
        object6.setServerType(ServerType.ADMIN_PRODUCTION);
        object6.setHealthCheckType(HealthCheckType.FIVE_MINUTES);
        object6.setHour(5);
        object6.setDate(DateUtils.dateWithNoTime(getDayAMonthAgo()).getTime());
        //
        dao.save(object1);
        dao.save(object2);
        dao.save(object3);
        dao.save(object4);
        dao.save(object5);
        dao.save(object6);
        //
        List<HealthCheck> expected = new ArrayList<>();
        expected.add(object1);
        expected.add(object5);
        //
        List<HealthCheck> result = dao.getByTypeFiveMinutes(ServerType.ADMIN_PRODUCTION, cal.getTime().getTime(), 5);
        //
        assertEquals(expected, result);
        assertEquals(2, result.size());
    }

    @Test(expected = BadRequestException.class)
    public void test_getByTypeOneHour_null_serverType() {
        dao.getByTypeOneHour(null, new Date().getTime());
    }

    @Test
    public void test_getByTypeOneHour_success() {
        Date date = new Date();
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        // OK
        HealthCheck object1 = new HealthCheck();
        object1.setId("1-OK");
        object1.setStatusType(HealthStatusType.UNKNOWN);
        object1.setServerType(ServerType.ADMIN_PRODUCTION);
        object1.setHealthCheckType(HealthCheckType.ONE_HOUR);
        object1.setDate(cal.getTime().getTime());
        // different server type
        HealthCheck object2 = new HealthCheck();
        object2.setId("2");
        object2.setStatusType(HealthStatusType.UNKNOWN);
        object2.setServerType(ServerType.NOTIFICATION);
        object2.setHealthCheckType(HealthCheckType.ONE_HOUR);
        object2.setDate(cal.getTime().getTime());
        // different health check type
        HealthCheck object3 = new HealthCheck();
        object3.setId("3");
        object3.setStatusType(HealthStatusType.UNKNOWN);
        object3.setServerType(ServerType.ADMIN_PRODUCTION);
        object3.setHealthCheckType(HealthCheckType.FIVE_MINUTES);
        object3.setDate(cal.getTime().getTime());
        // different date and health check type
        HealthCheck object4 = new HealthCheck();
        object4.setId("4");
        object4.setStatusType(HealthStatusType.UNKNOWN);
        object4.setServerType(ServerType.ADMIN_PRODUCTION);
        object4.setHealthCheckType(HealthCheckType.FIVE_MINUTES);
        object4.setDate(DateUtils.dateWithNoTime(getDayAMonthAgo()).getTime());
        // OK
        HealthCheck object5 = new HealthCheck();
        object5.setId("5-OK");
        object5.setStatusType(HealthStatusType.UNKNOWN);
        object5.setServerType(ServerType.ADMIN_PRODUCTION);
        object5.setHealthCheckType(HealthCheckType.ONE_HOUR);
        object5.setDate(cal.getTime().getTime());
        // different date
        HealthCheck object6 = new HealthCheck();
        object6.setId("6");
        object6.setStatusType(HealthStatusType.UNKNOWN);
        object6.setServerType(ServerType.ADMIN_PRODUCTION);
        object6.setHealthCheckType(HealthCheckType.ONE_HOUR);
        object6.setDate(DateUtils.dateWithNoTime(getDayAMonthAgo()).getTime());
        //
        dao.save(object1);
        dao.save(object2);
        dao.save(object3);
        dao.save(object4);
        dao.save(object5);
        dao.save(object6);
        //
        List<HealthCheck> expected = new ArrayList<>();
        expected.add(object1);
        expected.add(object5);
        //
        List<HealthCheck> result = dao.getByTypeOneHour(ServerType.ADMIN_PRODUCTION, cal.getTime().getTime());
        //
        assertEquals(expected, result);
        assertEquals(2, result.size());
    }

    @Test(expected = BadRequestException.class)
    public void test_getByTypeOneDay_null_serverType() {
        dao.getByTypeOneDay(null);
    }

    @Test
    public void test_getByTypeOneDay_success() {
        // OK
        HealthCheck object1 = new HealthCheck();
        object1.setId("1-OK");
        object1.setStatusType(HealthStatusType.UNKNOWN);
        object1.setServerType(ServerType.ADMIN_PRODUCTION);
        object1.setHealthCheckType(HealthCheckType.ONE_DAY);
        object1.setDate(new Date().getTime());
        // different server type
        HealthCheck object2 = new HealthCheck();
        object2.setId("2");
        object2.setStatusType(HealthStatusType.UNKNOWN);
        object2.setServerType(ServerType.NOTIFICATION);
        object2.setHealthCheckType(HealthCheckType.ONE_DAY);
        // different health check type
        HealthCheck object3 = new HealthCheck();
        object3.setId("3");
        object3.setStatusType(HealthStatusType.UNKNOWN);
        object3.setServerType(ServerType.ADMIN_PRODUCTION);
        object3.setHealthCheckType(HealthCheckType.FIVE_MINUTES);
        // too old
        HealthCheck object4 = new HealthCheck();
        object4.setId("4");
        object4.setStatusType(HealthStatusType.UNKNOWN);
        object4.setServerType(ServerType.ADMIN_PRODUCTION);
        object4.setHealthCheckType(HealthCheckType.ONE_DAY);
        object4.setDate(DateUtils.decreaseOneDay(decreaseOneDay(getDayAMonthAgo())).getTime());
        // OK
        HealthCheck object5 = new HealthCheck();
        object5.setId("5-OK");
        object5.setStatusType(HealthStatusType.UNKNOWN);
        object5.setServerType(ServerType.ADMIN_PRODUCTION);
        object5.setHealthCheckType(HealthCheckType.ONE_DAY);
        object5.setDate(new Date().getTime());
        // OK
        HealthCheck object6 = new HealthCheck();
        object6.setId("6-OK");
        object6.setStatusType(HealthStatusType.UNKNOWN);
        object6.setServerType(ServerType.ADMIN_PRODUCTION);
        object6.setHealthCheckType(HealthCheckType.ONE_DAY);
        object6.setDate(new Date().getTime());
        //
        dao.save(object1);
        dao.save(object2);
        dao.save(object3);
        dao.save(object4);
        dao.save(object5);
        dao.save(object6);
        //
        List<HealthCheck> expected = new ArrayList<>();
        expected.add(object1);
        expected.add(object5);
        expected.add(object6);
        //
        List<HealthCheck> result = dao.getByTypeOneDay(ServerType.ADMIN_PRODUCTION);
        //
        assertEquals(expected, result);
        assertEquals(3, result.size());
    }
}
