package com.rukon.springjdbc.main;

import com.rukon.springjdbc.dao.OrganizationDao;
import com.rukon.springjdbc.domain.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JdbcTemplateClassApp1 {

    @Autowired
    private OrganizationDao dao;

    @Autowired
    private DaoUtils daoUtils;

    public void actionMethod() {
        // Creating seed data
        daoUtils.createSeedData(dao);

        // List Organizations
        List<Organization> orgs = dao.getAllOrganization();
        daoUtils.printOrganizations(orgs, daoUtils.readOperation);

        // Create a new organization record
        Organization org = new Organization("Nexxt", 1978, 19152, 2000, "Get Hired!");
        boolean isCreated = dao.create(org);
        daoUtils.printSuccessFailure(daoUtils.createOperation, isCreated);
        daoUtils.printOrganizations(dao.getAllOrganization(), daoUtils.createOperation);

        // Get single organization
        Organization org2 = dao.getOrganization(1);
        daoUtils.printOrganization(org2, daoUtils.readOperation);

        // Update a slogan
        Organization org3 = dao.getOrganization(2);
        org3.setSlogan("We build ** awesome driving machine");
        boolean isUpdated = dao.update(org3);
        daoUtils.printSuccessFailure(daoUtils.updateOperation, isUpdated);
        daoUtils.printOrganization(dao.getOrganization(2), daoUtils.readOperation);

        // Delete an organization
        boolean isDeleted = dao.delete(dao.getOrganization(3));
        daoUtils.printSuccessFailure(daoUtils.deleteOperation, isDeleted);
        daoUtils.printOrganizations(dao.getAllOrganization(), daoUtils.createOperation);

        // Cleanup
        dao.cleanup();
        daoUtils.printOrganizationCount(dao.getAllOrganization(), daoUtils.cleanupOperation);

    }

    public static void main(String[] args) {

        // create the application context
        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans-cp.xml");
        JdbcTemplateClassApp1 mainApp = ctx.getBean(JdbcTemplateClassApp1.class);

        mainApp.actionMethod();

        // Create the bean
//        OrganizationDao dao = (OrganizationDaoImpl) ctx.getBean("orgDao");


        // Close application context
        ((ClassPathXmlApplicationContext) ctx).close();

    }
}
