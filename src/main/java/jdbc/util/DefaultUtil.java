package jdbc.util;


import jdbc.dao.entity.*;
import jdbc.dto.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DefaultUtil {

    private CompanyTo defaultCompanyTo;
    private CustomerTo defaultCustomerTo;
    private DeveloperTo defaultDeveloperTo1;
    private DeveloperTo defaultDeveloperTo2;
    private ProjectTo defaultProjectTo1;
    private ProjectTo defaultProjectTo2;
    private SkillTo defaultSkillTo1;
    private SkillTo defaultSkillTo2;

    public DefaultUtil() {

        defaultCompanyTo = createDefaultCompanyTo();
        defaultCustomerTo = createDefaultCustomerTo();
        defaultDeveloperTo1 = createDefaultDeveloperTo1();
        defaultDeveloperTo2 = createDefaultDeveloperTo2();
        defaultProjectTo1 = createDefaultProjectTo1();
        defaultProjectTo2 = createDefaultProjectTo2();
        defaultSkillTo1 = createDefaultSkillTo1();
        defaultSkillTo2 = createDefaultSkillTo2();
    }

    public CompanyTo getDefaultCompanyTo() {
        return defaultCompanyTo;
    }

    public CustomerTo getDefaultCustomerTo() {
        return defaultCustomerTo;
    }

    public DeveloperTo getDefaultDeveloperTo1() {
        return defaultDeveloperTo1;
    }

    public DeveloperTo getDefaultDeveloperTo2() {
        return defaultDeveloperTo2;
    }

    public ProjectTo getDefaultProjectTo1() {
        return defaultProjectTo1;
    }

    public ProjectTo getDefaultProjectTo2() {
        return defaultProjectTo2;
    }

    public SkillTo getDefaultSkillTo1() {
        return defaultSkillTo1;
    }

    public SkillTo getDefaultSkillTo2() {
        return defaultSkillTo2;
    }

    private DeveloperTo createDefaultDeveloperTo1() {
        DeveloperTo developerTo = new DeveloperTo();
        developerTo.setName("NewDeveloper1");
        developerTo.setSalary(5700);
        developerTo.setSex("male");
        developerTo.setAge(38);
        CompanyDao companyDao = createCompanyDao();
        developerTo.setCompanyDao(companyDao);
        List<ProjectDao> projectDaos = createProjectDaos();
        developerTo.setProjects(projectDaos);
        List<SkillDao> skills = createSkillDaos();
        developerTo.setSkills(skills);
        return developerTo;
    }

    private DeveloperTo createDefaultDeveloperTo2() {
        DeveloperTo developerTo = new DeveloperTo();
        developerTo.setName("NewDeveloper2");
        developerTo.setSalary(5800);
        developerTo.setSex("female");
        developerTo.setAge(35);
        CompanyDao companyDao = createCompanyDao();
        developerTo.setCompanyDao(companyDao);
        List<ProjectDao> projectDaos = createProjectDaos();
        developerTo.setProjects(projectDaos);
        List<SkillDao> skills = createSkillDaos();
        developerTo.setSkills(skills);
        return developerTo;
    }

    private List<SkillDao> createSkillDaos() {
        SkillDao skillDao = new SkillDao();
        skillDao.setLevel("Middle");
        skillDao.setLanguage("Java");
        List<SkillDao> skills = new ArrayList<>();
        skills.add(skillDao);
        return skills;
    }

    private List<ProjectDao> createProjectDaos() {
        ProjectDao projectDao = new ProjectDao();
        projectDao.setName("TestProject");
        projectDao.setDescription("Some description for testProject");
        projectDao.setCost(6700);
        projectDao.setIdProject(100);
        projectDao.setDate(LocalDate.now());

        ProjectDao projectDao2 = new ProjectDao();
        projectDao2.setName("TestProject2");
        projectDao2.setDescription("Some description for testProject2");
        projectDao2.setCost(5700);
        projectDao2.setIdProject(101);
        projectDao2.setDate(LocalDate.now());

        List<ProjectDao> projectDaos = new ArrayList<>();
        projectDaos.add(projectDao);
        projectDaos.add(projectDao2);
        return projectDaos;
    }

    private CompanyDao createCompanyDao() {
        CompanyDao companyDao = new CompanyDao();
        companyDao.setName("CompanyForDeveloper1");
        companyDao.setCity("CityForDeveloper1");
        return companyDao;
    }

    private SkillTo createDefaultSkillTo1() {
        SkillTo skillTo = new SkillTo();
        skillTo.setLevel("Middle");
        skillTo.setLanguage("Java");
        return skillTo;
    }

    private SkillTo createDefaultSkillTo2() {
        SkillTo skillTo = new SkillTo();
        skillTo.setLevel("Middle");
        skillTo.setLanguage("C++");
        return skillTo;
    }

    private ProjectTo createDefaultProjectTo1() {
        ProjectTo projectTo = new ProjectTo();
        projectTo.setName("DefaultProject1");
        projectTo.setDescription("Description of DefaultProject1");
        projectTo.setDate(LocalDate.now());
        projectTo.setCost(5700);
        projectTo.setCustomers(createCustomerDaos());
        projectTo.setCompanies(createCompanyDaos());
        projectTo.setDevelopers(createDeveloperDaos());
        projectTo.setDate(LocalDate.now());
        return projectTo;
    }


    private ProjectTo createDefaultProjectTo2() {
        ProjectTo projectTo = new ProjectTo();
        projectTo.setName("DefaultProject2");
        projectTo.setDescription("Description of DefaultProject2");
        projectTo.setDate(LocalDate.now());
        projectTo.setCost(5700);
        projectTo.setCustomers(createCustomerDaos());
        projectTo.setCompanies(createCompanyDaos());
        projectTo.setDevelopers(createDeveloperDaos());
        projectTo.setDate(LocalDate.now());
        return projectTo;
    }

    private List<DeveloperDao> createDeveloperDaos() {
        List<DeveloperDao> developerDaos = new ArrayList<>();
        DeveloperDao developerDao1 = new DeveloperDao();
        developerDao1.setName("NewDeveloper1");
        developerDao1.setSalary(5800);
        developerDao1.setSex("male");
        developerDao1.setAge(38);
        developerDaos.add(developerDao1);
        DeveloperDao developerDao2 = new DeveloperDao();
        developerDao1.setName("NewDeveloper2");
        developerDao1.setSalary(5700);
        developerDao1.setSex("female");
        developerDao1.setAge(35);
        developerDaos.add(developerDao2);
        return developerDaos;
    }

    private List<CompanyDao> createCompanyDaos() {
        List<CompanyDao> companyDaos = new ArrayList<>();
        companyDaos.add(createCompanyDao());
        return companyDaos;
    }

    private List<CustomerDao> createCustomerDaos() {
        CustomerDao customerDao1 = new CustomerDao();
        customerDao1.setName("CustomerDao1");
        customerDao1.setCity("CustomerDaoCity1");
        CustomerDao customerDao2 = new CustomerDao();
        customerDao2.setName("CustomerDao2");
        customerDao2.setCity("CustomerDaoCity2");
        List<CustomerDao> customerDaos = new ArrayList<>();
        customerDaos.add(customerDao1);
        customerDaos.add(customerDao2);
        return customerDaos;
    }

    private CustomerTo createDefaultCustomerTo() {
        CustomerTo customerTo = new CustomerTo();
        customerTo.setName("NewCustomer");
        customerTo.setCity("NewCity");
        customerTo.setProjects(createProjectDaos());
        return customerTo;
    }

    private CompanyTo createDefaultCompanyTo() {
        CompanyTo companyTo = new CompanyTo();
        companyTo.setName("NewCompany");
        companyTo.setCity("NewCompanyCity");
        companyTo.setProjects(createProjectDaos());
        return companyTo;
    }

}
