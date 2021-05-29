package jdbc.dao.repositories.one_entity_repositories;

import jdbc.config.DatabaseConnectionManager;
import jdbc.dao.entity.CustomerDao;
import jdbc.service.converters.CustomerConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository implements Repository<CustomerDao> {
    private final DatabaseConnectionManager connectionManager;

    public CustomerRepository(DatabaseConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public CustomerDao deleteById(int idCustomer) {
        String query = "delete from customers where id_customer=?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idCustomer);
            CustomerDao deletedCustomerDao = findById(idCustomer);
            preparedStatement.executeUpdate();
            return deletedCustomerDao;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public CustomerDao deleteByObject(CustomerDao customerDao) {
        return deleteById(customerDao.getIdCustomer());
    }

    @Override
    public CustomerDao update(CustomerDao customerDao) {
        String query = "update customers set name=?, city=? where id_customer=?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(3, customerDao.getIdCustomer());
            setValuesAndExecutePreparedStatement(customerDao, preparedStatement);
            return findById(customerDao.getIdCustomer());
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public CustomerDao create(CustomerDao customerDao) {
        if (!exists(customerDao)) {
            String query = "INSERT INTO customers (name, city) VALUES (?,?)";
            try (Connection connection = connectionManager.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                setValuesAndExecutePreparedStatement(customerDao, preparedStatement);
                customerDao.setIdCustomer(getLastCustomerIndex());
                return customerDao;
            } catch (SQLException ex) {
                ex.printStackTrace();
                return null;
            }
        } else {
            customerDao.setIdCustomer(getIdForExistingCustomer(customerDao));
            return customerDao;
        }
    }

    @Override
    public CustomerDao findById(int customerId) {
        String query = "select id_customer, name, city from customers where id_customer=?";
        CustomerDao customerDao = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                customerDao = CustomerConverter.toCustomerDao(resultSet);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return customerDao;
    }

    @Override
    public List<CustomerDao> findAll() {
        String query = "select id_customer, name, city from customers";
        List<CustomerDao> allcustomers = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                allcustomers.add(CustomerConverter.toCustomerDao(resultSet));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allcustomers;
    }

    private boolean exists(CustomerDao customerDao) {
        return findAll().stream()
                .anyMatch(c -> c.equals(customerDao));
    }

    private int getIdForExistingCustomer(CustomerDao customerDao) {
        return findAll().stream()
                .filter(c -> c.equals(customerDao))
                .findFirst().orElse(new CustomerDao()).getIdCustomer();
    }

    private void setValuesAndExecutePreparedStatement(CustomerDao customerDao, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, customerDao.getName());
        preparedStatement.setString(2, customerDao.getCity());
        preparedStatement.executeUpdate();
    }

    private int getLastCustomerIndex() {
        String query = "select id_customer from customers order by id_customer desc limit 1";
        int index = -1;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                index = resultSet.getInt("id_customer");
            }
            return index;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return index;
        }
    }
}
