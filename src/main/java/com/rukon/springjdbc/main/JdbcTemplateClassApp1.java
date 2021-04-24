package com.rukon.springjdbc.main;

import com.rukon.springjdbc.dao.OrganizationDao;
import com.rukon.springjdbc.daoimpl.OrganizationDaoImpl;
import com.rukon.springjdbc.domain.Organization;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class JdbcTemplateClassApp1 {

    public static void main(String[] args) {

        // create the application context
        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans-cp.xml");

        // Create the bean
        OrganizationDao dao = (OrganizationDaoImpl) ctx.getBean("orgDao");

        // Creating seed data
        DaoUtils.createSeedData(dao);

        // List Organizations
        List<Organization> orgs = dao.getAllOrganization();
        DaoUtils.printOrganization(orgs, DaoUtils.readOperation);

        // Create a new organization record
        Organization org = new Organization("Nexxt", 1978, 19152, 2000, "Get Hired!");
        boolean isCreated = dao.create(org);
        DaoUtils.printSuccessFailure(DaoUtils.createOperation, isCreated);
        DaoUtils.printOrganization(dao.getAllOrganization(), DaoUtils.createOperation);

        // Cleanup
        dao.cleanup();
        DaoUtils.printOrganizationCount(dao.getAllOrganization(), DaoUtils.cleanupOperation);

        // Close application context
        ((ClassPathXmlApplicationContext) ctx).close();

    }
}
