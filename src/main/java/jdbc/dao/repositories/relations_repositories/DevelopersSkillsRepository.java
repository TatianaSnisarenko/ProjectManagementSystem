package jdbc.dao.repositories.relations_repositories;

import jdbc.config.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DevelopersSkillsRepository implements RelationsRepository {
    private final DatabaseConnectionManager connectionManager;

    public DevelopersSkillsRepository(DatabaseConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public boolean delete(int developerId, int skillId) {
        String query = "delete from developers_skills " +
                "where id_developer=? and id_skill=?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, developerId);
            preparedStatement.setInt(2, skillId);
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
    public boolean create(int developerId, int skillId) {
        if (!exists(developerId, skillId)) {
            String query = "insert into developers_skills(id_developer, id_skill) VALUES (?,?)";
            try (Connection connection = connectionManager.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, developerId);
                preparedStatement.setInt(2, skillId);
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
    public boolean exists(int developerId, int skillId) {
        String query = "select id_developer, id_skill from developers_skills " +
                "where id_developer=? and id_skill=?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, developerId);
            preparedStatement.setInt(2, skillId);
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
