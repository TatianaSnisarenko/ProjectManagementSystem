package jdbc.dao.repositories.one_entity_repositories;

import jdbc.config.DatabaseConnectionManager;
import jdbc.dao.entity.DeveloperDao;
import jdbc.service.converters.DeveloperConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeveloperRepository implements Repository<DeveloperDao> {
    private final DatabaseConnectionManager connectionManager;

    public DeveloperRepository(DatabaseConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public DeveloperDao deleteById(int idDeveloper) {
        String query = "delete from developers where id_developer=?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idDeveloper);
            DeveloperDao deleteDeveloperDao = findById(idDeveloper);
            preparedStatement.executeUpdate();
            return deleteDeveloperDao;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public DeveloperDao deleteByObject(DeveloperDao developerDao) {
        return deleteById(developerDao.getIdDeveloper());
    }

    @Override
    public DeveloperDao update(DeveloperDao developerDao) {
        String query = "update developers set name=?, age=?, sex=?::sex_choice, id_company=?, salary=? where id_developer=?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(6, developerDao.getIdDeveloper());
            setValuesAndExecutePreparedStatement(developerDao, preparedStatement);
            return findById(developerDao.getIdDeveloper());
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public DeveloperDao create(DeveloperDao developerDao) {
        if (!exists(developerDao)) {
            String query = "INSERT INTO developers (name, age, sex, id_company, salary) VALUES (?,?,?::sex_choice,?,?)";
            try (Connection connection = connectionManager.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                setValuesAndExecutePreparedStatement(developerDao, preparedStatement);
                developerDao.setIdDeveloper(getLastDeveloperIndex());
                return developerDao;
            } catch (SQLException ex) {
                ex.printStackTrace();
                return null;
            }
        } else {
            developerDao.setIdDeveloper(getIdForExistingDeveloper(developerDao));
            return developerDao;
        }
    }

    @Override
    public DeveloperDao findById(int developerId) {
        String query = "select id_developer, name, age, sex, id_company, salary from developers where id_developer=?";
        DeveloperDao developerDao = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, developerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                developerDao = DeveloperConverter.toDeveloperDao(resultSet);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return developerDao;
    }

    @Override
    public List<DeveloperDao> findAll() {
        String query = "select id_developer, name, age, sex, id_company, salary from developers";
        List<DeveloperDao> allDeveloperDaos = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                allDeveloperDaos.add(DeveloperConverter.toDeveloperDao(resultSet));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allDeveloperDaos;
    }

    @Override
    public List<Integer> getListOfValidIndexes() {
        String query = "select id_developer from developers order by id_developer";
        List<Integer> indexes = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                indexes.add(resultSet.getInt("id_developer"));
            }
            return indexes;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return indexes;
        }
    }

    private boolean exists(DeveloperDao developerDao) {
        return findAll().stream()
                .anyMatch(d -> d.equals(developerDao));
    }

    private int getIdForExistingDeveloper(DeveloperDao developerDao) {
        return findAll().stream()
                .filter(d -> d.equals(developerDao))
                .findFirst().orElse(new DeveloperDao()).getIdDeveloper();
    }

    private void setValuesAndExecutePreparedStatement(DeveloperDao developerDao, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, developerDao.getName());
        preparedStatement.setInt(2, developerDao.getAge());
        preparedStatement.setString(3, developerDao.getSex());
        preparedStatement.setInt(4, developerDao.getIdCompany());
        preparedStatement.setDouble(5, developerDao.getSalary());
        preparedStatement.executeUpdate();
    }

    private int getLastDeveloperIndex() {
        String query = "select id_developer from developers order by id_developer desc limit 1";
        int index = -1;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                index = resultSet.getInt("id_developer");
            }
            return index;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return index;
        }
    }
}
