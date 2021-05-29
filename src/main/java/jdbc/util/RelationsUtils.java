package jdbc.util;

import jdbc.config.DatabaseConnectionManager;
import jdbc.dao.entity.*;
import jdbc.dao.repositories.one_entity_repositories.CompanyRepository;
import jdbc.service.converters.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelationsUtils {
    private static final DatabaseConnectionManager CONNECTION_MANAGER;

    static {
        PropertiesConfig propertiesConfig = new PropertiesConfig("application.properties");
        CONNECTION_MANAGER = new DatabaseConnectionManager(propertiesConfig);
    }

    public static List<ProjectDao> getAllProjectsForCompany(int companyId) {
        String query = "select p.id_project, p.name, p.description, p.cost from projects p, companies c, companies_projects cp " +
                "where p.id_project=cp.id_project and c.id_company=cp.id_company and c.id_company=?";
        List<ProjectDao> projects = new ArrayList<>();
        try (Connection connection = CONNECTION_MANAGER.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, companyId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                projects.add(ProjectConverter.toProjectDao(resultSet));
            }
            return projects;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public static List<ProjectDao> getAllProjectsForCustomer(int idCustomer) {
        String query = "select p.id_project, p.name, p.description, p.cost from projects p, customers c, customers_projects cp " +
                "where p.id_project=cp.id_project and c.id_customer=cp.id_customer and c.id_customer=?";
        List<ProjectDao> projects = new ArrayList<>();
        try (Connection connection = CONNECTION_MANAGER.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idCustomer);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                projects.add(ProjectConverter.toProjectDao(resultSet));
            }
            return projects;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public static CompanyDao getCompanyForDeveloper(int idCompany) {
        CompanyRepository companyRepository = new CompanyRepository(CONNECTION_MANAGER);
        return companyRepository.findById(idCompany);
    }

    public static List<ProjectDao> getAllProjectsForDeveloper(int idDeveloper) {
        String query = "select p.id_project, p.name, p.description, p.cost from projects p, developers d, developers_projects dp " +
                "where p.id_project=dp.id_project and d.id_developer=dp.id_developer and d.id_developer=?";
        List<ProjectDao> projects = new ArrayList<>();
        try (Connection connection = CONNECTION_MANAGER.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idDeveloper);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                projects.add(ProjectConverter.toProjectDao(resultSet));
            }
            return projects;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public static List<SkillDao> getAllSkillsForDeveloper(int idDeveloper) {
        String query = "select s.id_skill, s.language, s.level from skills s, developers d, developers_skills ds " +
                "where s.id_skill=ds.id_skill and d.id_developer=ds.id_developer and d.id_developer=?";
        List<SkillDao> skills = new ArrayList<>();
        try (Connection connection = CONNECTION_MANAGER.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idDeveloper);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                skills.add(SkillConverter.toSkillDao(resultSet));
            }
            return skills;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public static List<CompanyDao> getAllCompaniesForProject(int idProject) {
        String query = "select c.id_company, c.name, c.city from companies c, projects p, companies_projects cp " +
                "where c.id_company=cp.id_company and p.id_project=cp.id_project and p.id_project=?";
        List<CompanyDao> companies = new ArrayList<>();
        try (Connection connection = CONNECTION_MANAGER.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idProject);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                companies.add(CompanyConverter.toCompanyDao(resultSet));
            }
            return companies;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
        //return null;
    }

    public static List<CustomerDao> getCustomerForProject(int idProject) {
        String query = "select c.id_customer, c.name, c.city from customers c, projects p, customers_projects cp " +
                "where c.id_customer=cp.id_customer and p.id_project=cp.id_project and p.id_project=?";
        List<CustomerDao> customers = new ArrayList<>();
        try (Connection connection = CONNECTION_MANAGER.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idProject);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                customers.add(CustomerConverter.toCustomerDao(resultSet));
            }
            return customers;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public static List<DeveloperDao> getAllDevelopersForProject(int idProject) {
        String query = "select d.id_developer, d.name, d.age, d.sex, d.id_company, d.salary from developers d, projects p, developers_projects dp " +
                "where p.id_project=dp.id_project and d.id_developer=dp.id_developer and p.id_project=?";
        List<DeveloperDao> developerDaos = new ArrayList<>();
        try (Connection connection = CONNECTION_MANAGER.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idProject);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                developerDaos.add(DeveloperConverter.toDeveloperDao(resultSet));
            }
            return developerDaos;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public static List<DeveloperDao> getAllDevelopersWithSkillLanguage(String skillLanguage) {
        String query = "select d.id_developer, d.name, d.age, d.sex, d.id_company, d.salary from developers d, skills s, developers_skills ds " +
                "where d.id_developer = ds.id_developer and s.id_skill=ds.id_skill and s.language=?::language_choice";
        List<DeveloperDao> developerDaos = new ArrayList<>();
        try (Connection connection = CONNECTION_MANAGER.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, skillLanguage);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                developerDaos.add(DeveloperConverter.toDeveloperDao(resultSet));
            }
            return developerDaos;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public static List<DeveloperDao> getAllDevelopersWithSkillLevel(String skillLevel) {
        String query = "select d.id_developer, d.name, d.age, d.sex, d.id_company, d.salary from developers d, skills s, developers_skills ds" +
                " where d.id_developer = ds.id_developer and s.id_skill=ds.id_skill and s.level=?::level_choice";
        List<DeveloperDao> developerDaos = new ArrayList<>();
        try (Connection connection = CONNECTION_MANAGER.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, skillLevel);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                developerDaos.add(DeveloperConverter.toDeveloperDao(resultSet));
            }
            return developerDaos;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}
