package jdbc.dao.repositories.one_entity_repositories;

import jdbc.config.DatabaseConnectionManager;
import jdbc.dao.entity.ProjectDao;
import jdbc.service.converters.ProjectConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectRepository implements Repository<ProjectDao> {
    private final DatabaseConnectionManager connectionManager;

    public ProjectRepository(DatabaseConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public ProjectDao deleteById(int idProject) {
        String query = "delete from projects where id_project=?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idProject);
            ProjectDao deletedProjectDao = findById(idProject);
            preparedStatement.executeUpdate();
            return deletedProjectDao;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public ProjectDao deleteByObject(ProjectDao projectDao) {
        return deleteById(projectDao.getIdProject());
    }

    @Override
    public ProjectDao update(ProjectDao projectDao) {
        String query = "update projects set name=?, description=?, cost=?, creation_date=? where id_project=?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(5, projectDao.getIdProject());
            setValuesAndExecutePreparedStatement(projectDao, preparedStatement);
            return findById(projectDao.getIdProject());
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public ProjectDao create(ProjectDao projectDao) {
        if (!exists(projectDao)) {
            String query = "insert into projects (name, description, cost, creation_date) VALUES (?,?,?,?)";
            try (Connection connection = connectionManager.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                setValuesAndExecutePreparedStatement(projectDao, preparedStatement);
                projectDao.setIdProject(getLastProjectIndex());
                return projectDao;
            } catch (SQLException ex) {
                ex.printStackTrace();
                return null;
            }
        } else {
            projectDao.setIdProject(getIdForExistingProject(projectDao));
            return projectDao;
        }
    }

    @Override
    public ProjectDao findById(int projectId) {
        String query = "select id_project, name, description, cost, creation_date from projects where id_project=?";
        ProjectDao projectDao = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, projectId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                projectDao = ProjectConverter.toProjectDao(resultSet);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return projectDao;
    }

    @Override
    public List<ProjectDao> findAll() {
        String query = "select id_project, name, description, cost, creation_date from projects";
        List<ProjectDao> allprojects = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                allprojects.add(ProjectConverter.toProjectDao(resultSet));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allprojects;
    }

    @Override
    public List<Integer> getListOfValidIndexes() {
        String query = "select id_project from projects order by id_project";
        List<Integer> indexes = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                indexes.add(resultSet.getInt("id_project"));
            }
            return indexes;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return indexes;
        }
    }

    private boolean exists(ProjectDao projectDao) {
        return findAll().stream()
                .anyMatch(p -> p.equals(projectDao));
    }

    private int getIdForExistingProject(ProjectDao projectDao) {
        return findAll().stream()
                .filter(p -> p.equals(projectDao))
                .findFirst().orElse(new ProjectDao()).getIdProject();
    }

    private void setValuesAndExecutePreparedStatement(ProjectDao projectDao, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, projectDao.getName());
        preparedStatement.setString(2, projectDao.getDescription());
        preparedStatement.setDouble(3, projectDao.getCost());
        preparedStatement.setDate(4, java.sql.Date.valueOf(projectDao.getDate()));
        preparedStatement.executeUpdate();
    }

    private int getLastProjectIndex() {
        String query = "select id_project from projects order by id_project desc limit 1";
        int index = -1;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                index = resultSet.getInt("id_project");
            }
            return index;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return index;
        }
    }
}
