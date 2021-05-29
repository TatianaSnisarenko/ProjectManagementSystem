package jdbc.service.converters;

import jdbc.dao.entity.CompanyDao;
import jdbc.dto.CompanyTo;
import jdbc.util.RelationsUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyConverter {

    public static CompanyDao toCompanyDao(CompanyTo companyTo) {
        CompanyDao companyDao = new CompanyDao();
        companyDao.setIdCompany(companyTo.getIdCompany());
        companyDao.setName(companyTo.getName());
        companyDao.setCity(companyTo.getCity());
        return companyDao;
    }

    public static CompanyTo fromCompanyDao(CompanyDao companyDao) {
        CompanyTo companyTo = new CompanyTo();
        companyTo.setIdCompany(companyDao.getIdCompany());
        companyTo.setName(companyDao.getName());
        companyTo.setCity(companyDao.getCity());
        companyTo.setProjects(RelationsUtils.getAllProjectsForCompany(companyDao.getIdCompany()));
        return companyTo;
    }

    public static CompanyDao toCompanyDao(ResultSet resultSet) throws SQLException {
        CompanyDao companyDao = new CompanyDao();
        companyDao.setIdCompany(resultSet.getInt("id_company"));
        companyDao.setName(resultSet.getString("name"));
        companyDao.setCity(resultSet.getString("city"));
        return companyDao;
    }

    public static List<CompanyTo> allFromCompanyDao(List<CompanyDao> companyDaos) {
        return companyDaos.stream()
                .map(CompanyConverter::fromCompanyDao)
                .collect(Collectors.toList());
    }
}
