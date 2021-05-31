import jdbc.config.DatabaseConnectionManager;
import jdbc.dao.repositories.one_entity_repositories.*;
import jdbc.dao.repositories.relations_repositories.CompaniesProjectsRepository;
import jdbc.dao.repositories.relations_repositories.CustomersProjectsRepository;
import jdbc.dao.repositories.relations_repositories.DevelopersProjectsRepository;
import jdbc.dao.repositories.relations_repositories.DevelopersSkillsRepository;
import jdbc.service.CommandProcessor;
import jdbc.service.services.CompanyService;
import jdbc.service.services.DeveloperService;
import jdbc.service.services.ProjectService;
import jdbc.util.CommandUtil;
import jdbc.util.DefaultUtil;
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
        CompanyService companyService = new CompanyService(companyRepository, projectRepository, companiesProjectsRepository);
        DeveloperService developerService = new DeveloperService(developerRepository, projectRepository, companyRepository,
                skillRepository, developersProjectsRepository, developersSkillsRepository);

        CommandUtil commandUtil = new CommandUtil(projectService, companyService, developerService);
        View view = new Console();
        DefaultUtil defaultUtil = new DefaultUtil();

        CommandProcessor commandProcessor = new CommandProcessor(commandUtil, defaultUtil, view);
        commandProcessor.process();
    }
}
