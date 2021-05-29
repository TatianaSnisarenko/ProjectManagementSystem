package jdbc.dao.repositories.one_entity_repositories;

import jdbc.config.DatabaseConnectionManager;
import jdbc.dao.entity.CompanyDao;
import jdbc.service.converters.CompanyConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyRepository implements Repository<CompanyDao> {
    private final DatabaseConnectionManager connectionManager;

    public CompanyRepository(DatabaseConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public CompanyDao deleteById(int idCompany) {
        String query = "delete from companies where id_company=?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idCompany);
            CompanyDao deletedCompanyDao = findById(idCompany);
            preparedStatement.executeUpdate();
            return deletedCompanyDao;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public CompanyDao deleteByObject(CompanyDao companyDao) {
        return deleteById(companyDao.getIdCompany());
    }

    @Override
    public CompanyDao update(CompanyDao companyDao) {
        String query = "update companies set name=?, city=? where id_company=?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(3, companyDao.getIdCompany());
            setValuesAndExecutePreparedStatement(companyDao, preparedStatement);
            return findById(companyDao.getIdCompany());
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public CompanyDao create(CompanyDao companyDao) {
        if (!exists(companyDao)) {
            String query = "insert into companies (name, city) VALUES (?,?)";
            try (Connection connection = connectionManager.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                setValuesAndExecutePreparedStatement(companyDao, preparedStatement);
                companyDao.setIdCompany(getLastCompanyIndex());
                return companyDao;
            } catch (SQLException ex) {
                ex.printStackTrace();
                return null;
            }
        } else {
            companyDao.setIdCompany(getIdForExistingCompany(companyDao));
            return companyDao;
        }
    }

    @Override
    public CompanyDao findById(int companyId) {
        String query = "select id_company, name, city from companies where id_company=?";
        CompanyDao companyDao = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, companyId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                companyDao = CompanyConverter.toCompanyDao(resultSet);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return companyDao;
    }

    @Override
    public List<CompanyDao> findAll() {
        String query = "select id_company, name, city from companies";
        List<CompanyDao> allCompanies = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                allCompanies.add(CompanyConverter.toCompanyDao(resultSet));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allCompanies;
    }

    private boolean exists(CompanyDao companyDao) {
        return findAll().stream()
                .anyMatch(c -> c.equals(companyDao));
    }

    private int getIdForExistingCompany(CompanyDao companyDao) {
        return findAll().stream()
                .filter(c -> c.equals(companyDao))
                .findFirst().orElse(new CompanyDao()).getIdCompany();
    }


    private void setValuesAndExecutePreparedStatement(CompanyDao companyDao, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, companyDao.getName());
        preparedStatement.setString(2, companyDao.getCity());
        preparedStatement.executeUpdate();
    }

    private int getLastCompanyIndex() {
        String query = "select id_company from companies order by id_company desc limit 1";
        int index = -1;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                index = resultSet.getInt("id_company");
            }
            return index;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return index;
        }
    }
}
