package jdbc.util;

import jdbc.dao.entity.DeveloperDao;
import jdbc.dto.CompanyTo;
import jdbc.dto.DeveloperTo;
import jdbc.dto.ProjectTo;
import jdbc.service.services.CompanyService;
import jdbc.service.services.DeveloperService;
import jdbc.service.services.ProjectService;

import java.util.List;
import java.util.stream.Collectors;

public class CommandUtil {

    ProjectService projectService;
    CompanyService companyService;
    DeveloperService developerService;

    public CommandUtil(ProjectService projectService, CompanyService companyService, DeveloperService developerService) {
        this.projectService = projectService;
        this.companyService = companyService;
        this.developerService = developerService;
    }

    public double getAllSalariesForProject(int id) {
        return getListOfDevelopersForProject(id).stream()
                .mapToDouble(DeveloperDao::getSalary)
                .sum();
    }

    public List<DeveloperDao> getListOfDevelopersForProject(int id) {
        ProjectTo projectTo = projectService.findById(id);
        return projectTo.getDevelopers();
    }

    public List<DeveloperDao> getListOfJavaDevelopers() {
        return RelationsUtils.getAllDevelopersWithSkillLanguage("Java");
    }

    public List<DeveloperDao> getListOfMiddleDevelopers() {
        return RelationsUtils.getAllDevelopersWithSkillLevel("Middle");
    }

    public List<String> getShortDescriptionOfAllProjects() {
        return projectService.findAll().stream()
                .map(p -> p.getDate().toString() + " - " + p.getName() + " - " + p.getDevelopers().size() + ".")
                .collect(Collectors.toList());
    }

    public CompanyTo consoleCompanyCreate(String name, String city, CompanyTo defaultCompanyTo){
        return companyService.consoleCreate(name, city, defaultCompanyTo);
    }

    public CompanyTo consoleCompanyUpdate(String name, String city, CompanyTo defaultCompanyTo){
        return companyService.consoleUpdate(name, city, defaultCompanyTo);
    }

    public CompanyTo findCompanyById(int id) {
        return companyService.findById(id);
    }

    public List<CompanyTo> findAllCompanies(){
        return companyService.findAll();
    }

    public CompanyTo deleteCompanyById(int id) {
        return companyService.deleteById(id);
    }

    public DeveloperTo createDeveloperConsole(String name, int age, String sex, int companyId, double salary, DeveloperTo defaultDeveloperTo){
        return developerService.createConsole(name, age, sex, companyId, salary, defaultDeveloperTo, companyService);
    }

    public DeveloperTo updateDeveloperConsole(String name, int age, String sex, int companyId, double salary, DeveloperTo defaultDeveloperTo){
        return developerService.updateConsole(name, age, sex, companyId, salary, defaultDeveloperTo, companyService);
    }

    public DeveloperTo findDeveloperById(int id) {
        return developerService.findById(id);
    }

    public List<DeveloperTo> findAllDevelopers() {
        return developerService.findAll();
    }

    public DeveloperTo deleteDeveloperById(int id) {
        return developerService.deleteById(id);
    }

    public List<Integer> getListOfValidIndexesForProject(){
        return projectService.getListOfValidIndexes();
    }

    public List<Integer> getListOfValidIndexesForCompany(){
        return companyService.getListOfValidIndexes();
    }

    public List<Integer> getListOfValidIndexesForDeveloper(){
        return developerService.getListOfValidIndexes();
    }
}
