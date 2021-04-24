package com.rukon.springjdbc.daoimpl;

import com.rukon.springjdbc.dao.OrganizationDao;
import com.rukon.springjdbc.domain.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class OrganizationDaoImpl implements OrganizationDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource datasource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(datasource);

    }

    public boolean create(Organization org) {
        SqlParameterSource beanParams = new BeanPropertySqlParameterSource(org);
        String sqlQuery = "INSERT INTO organization (company_name, year_of_incorporation, postal_code, employee_count, slogan) " +
                "VALUES(:companyName, :yearOfIncorporation, :postalCode, :employeeCount, :slogan)";

        return namedParameterJdbcTemplate.update(sqlQuery, beanParams) == 1;
//        Object[] args = new Object[] { org.getCompanyName(), org.getYearOfIncorporation(), org.getPostalCode(),
//                org.getEmployeeCount(), org.getSlogan()};
//        return jdbcTemplate.update(sqlQuery, args) == 1;
    }

    public Organization getOrganization(Integer id) {
        SqlParameterSource params = new MapSqlParameterSource("ID", id);
        String sqlQuery = "SELECT id, company_name, year_of_incorporation, postal_code, employee_count, slogan FROM organization where id=:ID";
        return namedParameterJdbcTemplate.queryForObject(sqlQuery, params, new OrganizationRowMapper());
//        Object[] arg = new Object[] {id};
//        Organization org = jdbcTemplate.queryForObject(sqlQuery, arg, new OrganizationRowMapper());
//        return org;
    }

    public List<Organization> getAllOrganization() {
        String sqlQuery = "SELECT * FROM organization";
        List<Organization> orgList = namedParameterJdbcTemplate.query(sqlQuery, new OrganizationRowMapper());

        return orgList;
    }

    public boolean delete(Organization org) {
        SqlParameterSource params = new MapSqlParameterSource("ID", org.getId());
        String sqlQuery = "DELETE FROM organization where id = :ID";
//        Object[] arg = new Object[] {org.getId()};
        return namedParameterJdbcTemplate.update(sqlQuery, params) == 1;
    }

    public boolean update(Organization org) {
        SqlParameterSource beanParams = new BeanPropertySqlParameterSource(org);
        String sqlQuery = "UPDATE organization SET slogan = :slogan WHERE id = :id";
        return namedParameterJdbcTemplate.update(sqlQuery, beanParams) == 1;
//        Object[] args = new Object[]{org.getSlogan(), org.getId()};
//        return jdbcTemplate.update(sqlQuery, args) == 1;
    }

    public void cleanup() {
        String sqlQuery = "TRUNCATE TABLE organization";
        namedParameterJdbcTemplate.getJdbcOperations().execute(sqlQuery);
    }
}
