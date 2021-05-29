package jdbc;

import jdbc.config.DatabaseConnectionManager;
import jdbc.dao.repositories.one_entity_repositories.*;
import jdbc.dao.repositories.relations_repositories.CompaniesProjectsRepository;
import jdbc.dao.repositories.relations_repositories.CustomersProjectsRepository;
import jdbc.dao.repositories.relations_repositories.DevelopersProjectsRepository;
import jdbc.dao.repositories.relations_repositories.DevelopersSkillsRepository;
import jdbc.util.PropertiesConfig;

public class Main {
    public static void main(String[] args) {
        PropertiesConfig propertiesConfig = new PropertiesConfig("application.properties");
        DatabaseConnectionManager cm = new DatabaseConnectionManager(propertiesConfig);

        ProjectRepository projectRepository = new ProjectRepository(cm);
        CompanyRepository companyRepository = new CompanyRepository(cm);
        CustomerRepository customerRepository = new CustomerRepository(cm);
        DeveloperRepository developerRepository = new DeveloperRepository(cm);
        SkillRepository skillRepository = new SkillRepository(cm);
        CompaniesProjectsRepository companiesProjectsRepository = new CompaniesProjectsRepository(cm);
        CustomersProjectsRepository customersProjectsRepository = new CustomersProjectsRepository(cm);
        DevelopersProjectsRepository developersProjectsRepository = new DevelopersProjectsRepository(cm);
        DevelopersSkillsRepository developersSkillsRepository = new DevelopersSkillsRepository(cm);


    }
}
