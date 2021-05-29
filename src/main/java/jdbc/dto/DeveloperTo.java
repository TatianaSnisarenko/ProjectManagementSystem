package jdbc.dto;

import jdbc.dao.entity.CompanyDao;
import jdbc.dao.entity.ProjectDao;
import jdbc.dao.entity.SkillDao;

import java.util.List;
import java.util.Objects;

public class DeveloperTo {
    private int idDeveloper;
    private String name;
    private int age;
    private String sex;
    private CompanyDao companyDao;
    private double salary;
    private List<SkillDao> skills;
    private List<ProjectDao> projects;

    public int getIdDeveloper() {
        return idDeveloper;
    }

    public void setIdDeveloper(int idDeveloper) {
        this.idDeveloper = idDeveloper;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public CompanyDao getCompanyDao() {
        return companyDao;
    }

    public void setCompanyDao(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public List<SkillDao> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillDao> skills) {
        this.skills = skills;
    }

    public List<ProjectDao> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectDao> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "DeveloperTo{" +
                "idDeveloper=" + idDeveloper +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", companyDao=" + companyDao +
                ", salary=" + salary +
                ", skills=" + skills +
                ", projects=" + projects +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeveloperTo that = (DeveloperTo) o;
        return age == that.age && Double.compare(that.salary, salary) == 0 && Objects.equals(name, that.name) && Objects.equals(sex, that.sex) && Objects.equals(companyDao, that.companyDao) && Objects.equals(skills, that.skills) && Objects.equals(projects, that.projects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, sex, companyDao, salary, skills, projects);
    }
}
