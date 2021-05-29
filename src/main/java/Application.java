import jdbc.config.DatabaseConnectionManager;
import jdbc.dao.repositories.one_entity_repositories.*;
import jdbc.dao.repositories.relations_repositories.CompaniesProjectsRepository;
import jdbc.dao.repositories.relations_repositories.CustomersProjectsRepository;
import jdbc.dao.repositories.relations_repositories.DevelopersProjectsRepository;
import jdbc.dao.repositories.relations_repositories.DevelopersSkillsRepository;
import jdbc.service.CommandProcessor;
import jdbc.service.services.ProjectService;
import jdbc.util.CommandUtil;
import jdbc.util.PropertiesConfig;
import view.Console;
import view.View;

public class Application {
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

        ProjectService projectService = new ProjectService(projectRepository, developerRepository, companyRepository, customerRepository,
                developersProjectsRepository,companiesProjectsRepository, customersProjectsRepository);

        CommandUtil commandUtil = new CommandUtil(projectService);
        View view = new Console();

        CommandProcessor commandProcessor = new CommandProcessor(commandUtil, view);
        commandProcessor.process();
    }
}
