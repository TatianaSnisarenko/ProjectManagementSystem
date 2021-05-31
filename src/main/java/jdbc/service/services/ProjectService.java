package jdbc.service.services;

import jdbc.dao.entity.CompanyDao;
import jdbc.dao.entity.CustomerDao;
import jdbc.dao.entity.DeveloperDao;
import jdbc.dao.entity.ProjectDao;
import jdbc.dao.repositories.one_entity_repositories.Repository;
import jdbc.dao.repositories.relations_repositories.RelationsRepository;
import jdbc.dto.ProjectTo;
import jdbc.service.converters.ProjectConverter;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectService {
    private Repository<ProjectDao> projectRepository;
    private Repository<DeveloperDao> developerRepository;
    private Repository<CompanyDao> companyRepository;
    private Repository<CustomerDao> customerRepository;
    private RelationsRepository developersProjectsRepository;
    private RelationsRepository companiesProjectsRepository;
    private RelationsRepository customersProjectsRepository;

    public ProjectService(Repository<ProjectDao> repository, Repository<DeveloperDao> developerRepository,
                          Repository<CompanyDao> companyRepository, Repository<CustomerDao> customerRepository,
                          RelationsRepository developersProjectsRepository, RelationsRepository companiesProjectsRepository,
                          RelationsRepository customersProjectsRepository) {
        this.projectRepository = repository;
        this.developerRepository = developerRepository;
        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
        this.developersProjectsRepository = developersProjectsRepository;
        this.companiesProjectsRepository = companiesProjectsRepository;
        this.customersProjectsRepository = customersProjectsRepository;
    }

    public ProjectTo create(ProjectTo projectTo) {
        ProjectDao projectDao = ProjectConverter.toProjectDao(projectTo);
        ProjectDao createdProjectDao = projectRepository.create(projectDao);
        addDeveloperAndRelations(projectTo, createdProjectDao);
        addCompanyAndRelations(projectTo, createdProjectDao);
        addCustomerAndRelations(projectTo, createdProjectDao);
        return ProjectConverter.fromProjectDao(createdProjectDao);
    }

    public ProjectTo findById(int projectId) {
        return ProjectConverter.fromProjectDao(projectRepository.findById(projectId));
    }

    public ProjectTo update(ProjectTo projectTo) {
        List<DeveloperDao> developersToBeDeleted = getDevelopersToBeDeleted(projectTo);
        List<CustomerDao> cutomersToBeDeleted = getCutomersToBeDeleted(projectTo);
        List<CompanyDao> companiesToBeDeleted = getCompaniesToBeDeleted(projectTo);
        ProjectDao projectDao = ProjectConverter.toProjectDao(projectTo);
        ProjectDao updatedProject = projectRepository.update(projectDao);
        addDeveloperAndRelations(projectTo, updatedProject);
        addCompanyAndRelations(projectTo, updatedProject);
        addCustomerAndRelations(projectTo, updatedProject);
        developersToBeDeleted.forEach(developer -> developersProjectsRepository.delete(developer.getIdDeveloper(), projectTo.getIdProject()));
        cutomersToBeDeleted.forEach(customerDao -> customersProjectsRepository.delete(customerDao.getIdCustomer(), projectTo.getIdProject()));
        companiesToBeDeleted.forEach(companyDao -> companiesProjectsRepository.delete(companyDao.getIdCompany(), projectTo.getIdProject()));
        ProjectDao updatedProjectDao = projectRepository.findById(projectDao.getIdProject());
        return ProjectConverter.fromProjectDao(updatedProjectDao);
    }

    public ProjectTo deletedById(int projectId) {
        deleteRelationsWithDevelopersCustomerCompanies(projectId);
        return ProjectConverter.fromProjectDao(projectRepository.deleteById(projectId));
    }

    public ProjectTo deletedByObject(ProjectTo projectTo) {
        return deletedById(projectTo.getIdProject());
    }

    public List<ProjectTo> findAll() {
        return ProjectConverter.allFromProjectDao(projectRepository.findAll());
    }

    private void addCustomerAndRelations(ProjectTo projectTo, ProjectDao createdProjectDao) {
        List<CustomerDao> customers = projectTo.getCustomers();
        customers.forEach(customerDao -> customerRepository.create(customerDao));
        customers.stream().forEach(customerDao -> customersProjectsRepository.create(customerDao.getIdCustomer(), createdProjectDao.getIdProject()));
    }

    private void addCompanyAndRelations(ProjectTo projectTo, ProjectDao createdProjectDao) {
        List<CompanyDao> companies = projectTo.getCompanies();
        companies.forEach(companyDao -> companyRepository.create(companyDao));
        companies.stream().forEach(companyDao -> companiesProjectsRepository.create(companyDao.getIdCompany(), createdProjectDao.getIdProject()));
    }

    private void addDeveloperAndRelations(ProjectTo projectTo, ProjectDao createdProjectDao) {
        List<DeveloperDao> developers = projectTo.getDevelopers();
        developers.forEach(developerDao -> developerRepository.create(developerDao));
        developers.stream().forEach(developerDao -> developersProjectsRepository.create(developerDao.getIdDeveloper(), createdProjectDao.getIdProject()));
    }

    private void deleteRelationsWithDevelopersCustomerCompanies(int projectId) {
        ProjectTo projectTo = findById(projectId);
        projectTo.getDevelopers().forEach(developerDao -> developersProjectsRepository.delete(developerDao.getIdDeveloper(), projectId));
        projectTo.getCompanies().forEach(companyDao -> companiesProjectsRepository.delete(companyDao.getIdCompany(), projectId));
        projectTo.getCustomers().forEach(customerDao -> customersProjectsRepository.delete(customerDao.getIdCustomer(), projectId));
    }

    private List<DeveloperDao> getDevelopersToBeDeleted(ProjectTo projectTo) {
        List<DeveloperDao> developersOld = findById(projectTo.getIdProject()).getDevelopers();
        List<DeveloperDao> developersNew = projectTo.getDevelopers();
        return developersOld.stream()
                .filter(developerDao -> !developersNew.contains(developerDao))
                .collect(Collectors.toList());
    }

    private List<CustomerDao> getCutomersToBeDeleted(ProjectTo projectTo) {
        List<CustomerDao> customersOld = findById(projectTo.getIdProject()).getCustomers();
        List<CustomerDao> customersNew = projectTo.getCustomers();
        return customersOld.stream()
                .filter(developerDao -> !customersNew.contains(developerDao))
                .collect(Collectors.toList());
    }

    private List<CompanyDao> getCompaniesToBeDeleted(ProjectTo projectTo) {
        List<CompanyDao> companiesOld = findById(projectTo.getIdProject()).getCompanies();
        List<CompanyDao> companiesNew = projectTo.getCompanies();
        return companiesOld.stream()
                .filter(developerDao -> !companiesNew.contains(developerDao))
                .collect(Collectors.toList());
    }

    public List<Integer> getListOfValidIndexes() {
        return projectRepository.getListOfValidIndexes();
    }
}
