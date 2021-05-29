package jdbc.dao.repositories.relations_repositories;

import jdbc.config.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomersProjectsRepository implements RelationsRepository {
    private final DatabaseConnectionManager connectionManager;

    public CustomersProjectsRepository(DatabaseConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public boolean delete(int customerId, int projectId) {
        String query = "delete from customers_projects cp " +
                "where cp.id_customer=? and cp.id_project=?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customerId);
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
    public boolean create(int customerId, int projectId) {
        if (!exists(customerId, projectId)) {
            String query = "insert into customers_projects(id_customer, id_project) VALUES (?,?)";
            try (Connection connection = connectionManager.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, customerId);
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
    public boolean exists(int customerId, int projectId) {
        String query = "select id_customer, id_project from customers_projects " +
                "where id_customer=? and id_project=?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customerId);
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
