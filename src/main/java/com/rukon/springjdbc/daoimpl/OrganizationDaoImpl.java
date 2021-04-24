package com.rukon.springjdbc.daoimpl;

import com.rukon.springjdbc.dao.OrganizationDao;
import com.rukon.springjdbc.domain.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository("orgDao")
public class OrganizationDaoImpl implements OrganizationDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource datasource) {
        jdbcTemplate = new JdbcTemplate(datasource);

    }

    public boolean create(Organization org) {
        String sqlQuery = "INSERT INTO organization (company_name, year_of_incorporation, postal_code, employee_count, slogan) " +
                "VALUES(?, ?, ?, ?, ?)";
        Object[] args = new Object[] { org.getCompanyName(), org.getYearOfIncorporation(), org.getPostalCode(),
                org.getEmployeeCount(), org.getSlogan()};
        return jdbcTemplate.update(sqlQuery, args) == 1;
    }

    public Organization getOrganization(Integer id) {
        return null;
    }

    public List<Organization> getAllOrganization() {
        String sqlQuery = "SELECT * FROM organization";
        List<Organization> orgList = jdbcTemplate.query(sqlQuery, new OrganizationRowMapper());

        return orgList;
    }

    public boolean delete(Organization org) {
        return false;
    }

    public boolean update(Organization org) {
        return false;
    }

    public void cleanup() {
        String sqlQuery = "TRUNCATE TABLE organization";
        jdbcTemplate.execute(sqlQuery);
    }
}
