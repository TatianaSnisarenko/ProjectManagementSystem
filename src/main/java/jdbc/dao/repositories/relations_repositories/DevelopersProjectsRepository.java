package jdbc.dao.repositories.relations_repositories;

import jdbc.config.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DevelopersProjectsRepository implements RelationsRepository {
    private final DatabaseConnectionManager connectionManager;

    public DevelopersProjectsRepository(DatabaseConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public boolean delete(int developerId, int projectId) {
        String query = "delete from developers_projects " +
                "where id_developer=? and id_project=?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, developerId);
            preparedStatement.setInt(2, projectId);
            int rows = preparedStatement.executeUpdate();
            if (rows == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean create(int developerId, int projectId) {
        if (!exists(developerId, projectId)) {
            String query = "insert into developers_projects(id_developer, id_project) VALUES (?,?)";
            try (Connection connection = connectionManager.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, developerId);
                preparedStatement.setInt(2, projectId);
                int rows = preparedStatement.executeUpdate();
                if (rows == 1) {
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean exists(int developerId, int projectId) {
        String query = "select id_developer, id_project from developers_projects " +
                "where id_developer=? and id_project=?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, developerId);
            preparedStatement.setInt(2, projectId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
