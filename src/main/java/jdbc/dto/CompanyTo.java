package jdbc.dto;

import jdbc.dao.entity.ProjectDao;

import java.util.List;
import java.util.Objects;

public class CompanyTo {

    private int idCompany;
    private String name;
    private String city;
    private List<ProjectDao> projects;

    public int getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(int idCompany) {
        this.idCompany = idCompany;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<ProjectDao> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectDao> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "CompanyTo{" +
                "idCompany=" + idCompany +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", projects=" + projects +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyTo companyTo = (CompanyTo) o;
        return Objects.equals(name, companyTo.name) && Objects.equals(city, companyTo.city) && Objects.equals(projects, companyTo.projects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, city, projects);
    }
}
