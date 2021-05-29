package jdbc.service.converters;

import jdbc.dao.entity.DeveloperDao;
import jdbc.dto.DeveloperTo;
import jdbc.util.RelationsUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class DeveloperConverter {

    public static DeveloperDao toDeveloperDao(DeveloperTo developerTo) {
        DeveloperDao developerDao = new DeveloperDao();
        developerDao.setIdDeveloper(developerTo.getIdDeveloper());
        developerDao.setName(developerTo.getName());
        developerDao.setAge(developerTo.getAge());
        developerDao.setSex(developerTo.getSex());
        developerDao.setSalary(developerTo.getSalary());
        developerDao.setIdCompany(developerTo.getCompanyDao().getIdCompany());
        return developerDao;
    }

    public static DeveloperTo fromDeveloperDao(DeveloperDao developerDao) {
        DeveloperTo developerTo = new DeveloperTo();
        developerTo.setIdDeveloper(developerDao.getIdDeveloper());
        developerTo.setName(developerDao.getName());
        developerTo.setAge(developerDao.getAge());
        developerTo.setSex(developerDao.getSex());
        developerTo.setSalary(developerDao.getSalary());
        developerTo.setCompanyDao(RelationsUtils.getCompanyForDeveloper(developerDao.getIdCompany()));
        developerTo.setProjects(RelationsUtils.getAllProjectsForDeveloper(developerDao.getIdDeveloper()));
        developerTo.setSkills(RelationsUtils.getAllSkillsForDeveloper(developerDao.getIdDeveloper()));
        return developerTo;
    }


    public static DeveloperDao toDeveloperDao(ResultSet resultSet) throws SQLException {
        DeveloperDao developerDao = new DeveloperDao();
        developerDao.setIdDeveloper(resultSet.getInt("id_developer"));
        developerDao.setName(resultSet.getString("name"));
        developerDao.setAge(resultSet.getInt("age"));
        developerDao.setSex(resultSet.getString("sex"));
        developerDao.setSalary(resultSet.getDouble("salary"));
        developerDao.setIdCompany(resultSet.getInt("id_company"));
        return developerDao;
    }

    public static List<DeveloperTo> allFromDeveloperDao(List<DeveloperDao> developerDaos) {
        return developerDaos.stream()
                .map(DeveloperConverter::fromDeveloperDao)
                .collect(Collectors.toList());
    }
}
