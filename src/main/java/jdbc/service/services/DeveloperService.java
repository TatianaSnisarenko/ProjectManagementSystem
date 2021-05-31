package jdbc.service.services;

import jdbc.dao.entity.CompanyDao;
import jdbc.dao.entity.DeveloperDao;
import jdbc.dao.entity.ProjectDao;
import jdbc.dao.entity.SkillDao;
import jdbc.dao.repositories.one_entity_repositories.Repository;
import jdbc.dao.repositories.relations_repositories.RelationsRepository;
import jdbc.dto.DeveloperTo;
import jdbc.service.converters.CompanyConverter;
import jdbc.service.converters.DeveloperConverter;

import java.util.List;
import java.util.stream.Collectors;

public class DeveloperService {
    private Repository<DeveloperDao> developerRepository;
    private Repository<ProjectDao> projectRepository;
    private Repository<CompanyDao> companyRepository;
    private Repository<SkillDao> skillRepository;
    private RelationsRepository developersProjectsRepository;
    private RelationsRepository developersSkillRepository;

    public DeveloperService(Repository<DeveloperDao> developerRepository,
                            Repository<ProjectDao> projectRepository,
                            Repository<CompanyDao> companyRepository,
                            Repository<SkillDao> skillRepository,
                            RelationsRepository developersProjectsRepository,
                            RelationsRepository developersSkillRepository) {
        this.developerRepository = developerRepository;
        this.projectRepository = projectRepository;
        this.companyRepository = companyRepository;
        this.skillRepository = skillRepository;
        this.developersProjectsRepository = developersProjectsRepository;
        this.developersSkillRepository = developersSkillRepository;
    }

    public DeveloperTo create(DeveloperTo developerTo) {
        addCompanyIfNotExists(developerTo);
        DeveloperDao createdDeveloperDao = developerRepository.create(DeveloperConverter.toDeveloperDao(developerTo));
        addProjectsAndRelations(developerTo, createdDeveloperDao);
        addSkillsAndRelations(developerTo, createdDeveloperDao);
        return DeveloperConverter.fromDeveloperDao(createdDeveloperDao);
    }

    public DeveloperTo createConsole(String name, int age, String sex, int companyId, double salary, DeveloperTo defaultDeveloperTo, CompanyService companyService){
        defaultDeveloperTo.setName(name);
        defaultDeveloperTo.setAge(age);
        defaultDeveloperTo.setSex(sex);
        defaultDeveloperTo.setCompanyDao(CompanyConverter.toCompanyDao(companyService.findById(companyId)));
        defaultDeveloperTo.setSalary(salary);
        return create(defaultDeveloperTo);
    }

    public DeveloperTo findById(int developerId) {
        return DeveloperConverter.fromDeveloperDao(developerRepository.findById(developerId));
    }

    public DeveloperTo update(DeveloperTo developerTo) {
        List<ProjectDao> projectsToBeDeleted = getProjectsToBeDeleted(developerTo);
        DeveloperDao developerDao = DeveloperConverter.toDeveloperDao(developerTo);
        DeveloperDao updatedDeveloperDao = developerRepository.update(developerDao);
        addProjectsAndRelations(developerTo, updatedDeveloperDao);
        projectsToBeDeleted.forEach(projectDao -> developersProjectsRepository.delete(developerTo.getIdDeveloper(), projectDao.getIdProject()));
        addSkillsAndRelations(developerTo, updatedDeveloperDao);
        return DeveloperConverter.fromDeveloperDao(updatedDeveloperDao);
    }

    public DeveloperTo updateConsole(String name, int age, String sex, int companyId, double salary, DeveloperTo defaultDeveloperTo, CompanyService companyService){
        defaultDeveloperTo.setName(name);
        defaultDeveloperTo.setAge(age);
        defaultDeveloperTo.setSex(sex);
        defaultDeveloperTo.setCompanyDao(CompanyConverter.toCompanyDao(companyService.findById(companyId)));
        defaultDeveloperTo.setSalary(salary);
        return update(defaultDeveloperTo);
    }

    public DeveloperTo deleteById(int developerId) {
        deleteRelationsWithProjectsAndSkills(developerId);
        return DeveloperConverter.fromDeveloperDao(developerRepository.deleteById(developerId));
    }

    public DeveloperTo deleteByObject(DeveloperTo developerTo) {
        return deleteById(developerTo.getIdDeveloper());
    }

    public List<DeveloperTo> findAll() {
        List<DeveloperDao> allCompaniesDao = developerRepository.findAll();
        return DeveloperConverter.allFromDeveloperDao(allCompaniesDao);
    }

    private void addProjectsAndRelations(DeveloperTo developerTo, DeveloperDao createdDeveloperDao) {
        List<ProjectDao> projects = developerTo.getProjects();
        projects.forEach(p -> projectRepository.create(p));
        projects.forEach(p -> developersProjectsRepository.create(createdDeveloperDao.getIdDeveloper(), p.getIdProject()));
    }

    private List<ProjectDao> getProjectsToBeDeleted(DeveloperTo developerTo) {
        List<ProjectDao> projectsOld = this.findById(developerTo.getIdDeveloper()).getProjects();
        List<ProjectDao> projectsNew = developerTo.getProjects();
        return projectsOld.stream()
                .filter(projectDao -> !projectsNew.contains(projectDao))
                .collect(Collectors.toList());
    }

    private void addSkillsAndRelations(DeveloperTo developerTo, DeveloperDao createdDeveloperDao) {
        List<SkillDao> skills = developerTo.getSkills();
        skills.forEach(skillDao -> skillRepository.create(skillDao));
        skills.forEach(skillDao -> developersSkillRepository.create(createdDeveloperDao.getIdDeveloper(), skillDao.getIdSkill()));
    }

    private void addCompanyIfNotExists(DeveloperTo developerTo) {
        CompanyDao companyDao = developerTo.getCompanyDao();
        companyRepository.create(companyDao);
    }

    private void deleteRelationsWithProjectsAndSkills(int developerId) {
        DeveloperTo developerTo = findById(developerId);
        developerTo.getProjects().forEach(projectDao -> developersProjectsRepository.delete(developerId, projectDao.getIdProject()));
        developerTo.getSkills().forEach(skillDao -> developersSkillRepository.delete(developerId, skillDao.getIdSkill()));
    }

    public List<Integer> getListOfValidIndexes() {
        return developerRepository.getListOfValidIndexes();
    }
}
