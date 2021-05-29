package jdbc.service.converters;

import jdbc.dao.entity.ProjectDao;
import jdbc.dto.ProjectTo;
import jdbc.util.RelationsUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectConverter {
    public static ProjectDao toProjectDao(ProjectTo projectTo) {
        ProjectDao projectDao = new ProjectDao();
        projectDao.setIdProject(projectTo.getIdProject());
        projectDao.setName(projectTo.getName());
        projectDao.setDescription(projectTo.getDescription());
        projectDao.setCost(projectTo.getCost());
        return projectDao;
    }

    public static ProjectTo fromProjectDao(ProjectDao projectDao) {
        ProjectTo projectTo = new ProjectTo();
        projectTo.setIdProject(projectDao.getIdProject());
        projectTo.setName(projectDao.getName());
        projectTo.setDescription(projectDao.getDescription());
        projectTo.setCost(projectDao.getCost());
        projectTo.setCompanies(RelationsUtils.getAllCompaniesForProject(projectDao.getIdProject()));
        projectTo.setCustomers(RelationsUtils.getCustomerForProject(projectDao.getIdProject()));
        projectTo.setDevelopers(RelationsUtils.getAllDevelopersForProject(projectDao.getIdProject()));
        return projectTo;
    }

    public static ProjectDao toProjectDao(ResultSet resultSet) throws SQLException {
        ProjectDao projectDao = new ProjectDao();
        projectDao.setIdProject(resultSet.getInt("id_project"));
        projectDao.setName(resultSet.getString("name"));
        projectDao.setDescription(resultSet.getString("description"));
        projectDao.setCost(resultSet.getDouble("cost"));
        return projectDao;
    }

    public static List<ProjectTo> allFromProjectDao(List<ProjectDao> projectDaos) {
        return projectDaos.stream()
                .map(ProjectConverter::fromProjectDao)
                .collect(Collectors.toList());
    }
}
