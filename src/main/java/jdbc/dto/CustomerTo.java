package jdbc.dto;

import jdbc.dao.entity.ProjectDao;

import java.util.List;
import java.util.Objects;

public class CustomerTo {
    private int idCustomer;
    private String name;
    private String city;
    private List<ProjectDao> projects;

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
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
        return "CustomerTo{" +
                "idCustomer=" + idCustomer +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", projects=" + projects +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerTo that = (CustomerTo) o;
        return Objects.equals(name, that.name) && Objects.equals(city, that.city) && Objects.equals(projects, that.projects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, city, projects);
    }
}
