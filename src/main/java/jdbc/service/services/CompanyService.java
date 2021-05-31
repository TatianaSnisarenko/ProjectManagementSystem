package jdbc.service.services;

import jdbc.dao.entity.CompanyDao;
import jdbc.dao.entity.ProjectDao;
import jdbc.dao.repositories.one_entity_repositories.CompanyRepository;
import jdbc.dao.repositories.one_entity_repositories.ProjectRepository;
import jdbc.dao.repositories.one_entity_repositories.Repository;
import jdbc.dao.repositories.relations_repositories.CompaniesProjectsRepository;
import jdbc.dao.repositories.relations_repositories.RelationsRepository;
import jdbc.dto.CompanyTo;
import jdbc.service.converters.CompanyConverter;

import java.util.List;
import java.util.stream.Collectors;

public class CompanyService {
    private Repository<CompanyDao> companyRepository;
    private Repository<ProjectDao> projectRepository;
    private RelationsRepository companiesProjectsRepository;

    public CompanyService(Repository<CompanyDao> companyRepository, Repository<ProjectDao> projectRepository,
                          RelationsRepository companiesProjectsRepository) {
        this.companyRepository = companyRepository;
        this.projectRepository = projectRepository;
        this.companiesProjectsRepository = companiesProjectsRepository;
    }

    public CompanyTo create(CompanyTo companyTo) {
        CompanyDao companyDao = CompanyConverter.toCompanyDao(companyTo);
        CompanyDao createdCompanyDao = companyRepository.create(companyDao);
        addProjectsAndRelations(companyTo, createdCompanyDao);
        return CompanyConverter.fromCompanyDao(createdCompanyDao);
    }

    public CompanyTo consoleCreate(String name, String city, CompanyTo defaultCompanyTo){
        defaultCompanyTo.setName(name);
        defaultCompanyTo.setCity(city);
        return create(defaultCompanyTo);
    }


    public CompanyTo findById(int companyId) {
        return CompanyConverter.fromCompanyDao(companyRepository.findById(companyId));
    }

    public CompanyTo update(CompanyTo companyTo) {
        List<ProjectDao> projectsToBeDeleted = getProjectsToBeDeleted(companyTo);
        CompanyDao companyDao = CompanyConverter.toCompanyDao(companyTo);
        CompanyDao updatedCompanyDao = companyRepository.update(companyDao);
        addProjectsAndRelations(companyTo, updatedCompanyDao);
        projectsToBeDeleted.forEach(projectDao -> companiesProjectsRepository.delete(companyTo.getIdCompany(), projectDao.getIdProject()));
        return CompanyConverter.fromCompanyDao(updatedCompanyDao);
    }

    public CompanyTo consoleUpdate(String name, String city, int id){
        CompanyTo companyTo = findById(id);
        companyTo.setName(name);
        companyTo.setCity(city);
        return update(companyTo);
    }

    public CompanyTo deleteById(int companyId) {
        this.findById(companyId).getProjects().stream().
                forEach(projectDao -> companiesProjectsRepository.delete(companyId, projectDao.getIdProject()));
        return CompanyConverter.fromCompanyDao(companyRepository.deleteById(companyId));
    }

    public CompanyTo deleteByObject(CompanyTo companyTo) {
        return deleteById(companyTo.getIdCompany());
    }

    public List<CompanyTo> findAll() {
        return CompanyConverter.allFromCompanyDao(companyRepository.findAll());
    }

    private void addProjectsAndRelations(CompanyTo companyTo, CompanyDao createdCompanyDao) {
        List<ProjectDao> projects = companyTo.getProjects();
        projects.forEach(p -> projectRepository.create(p));
        projects.stream().forEach(p -> companiesProjectsRepository.create(createdCompanyDao.getIdCompany(), p.getIdProject()));
    }

    private List<ProjectDao> getProjectsToBeDeleted(CompanyTo companyTo) {
        List<ProjectDao> projectsOld = findById(companyTo.getIdCompany()).getProjects();
        List<ProjectDao> projectsNew = companyTo.getProjects();
        return projectsOld.stream()
                .filter(projectDao -> !projectsNew.contains(projectDao))
                .collect(Collectors.toList());
    }

    public List<Integer> getListOfValidIndexes() {
        return companyRepository.getListOfValidIndexes();
    }
}
