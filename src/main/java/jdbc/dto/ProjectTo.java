package jdbc.dto;

import jdbc.dao.entity.CompanyDao;
import jdbc.dao.entity.CustomerDao;
import jdbc.dao.entity.DeveloperDao;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class ProjectTo {
    private int idProject;
    private String name;
    private String description;
    private double cost;
    private LocalDate date;
    private List<DeveloperDao> developers;
    private List<CompanyDao> companies;
    private List<CustomerDao> customers;

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public List<DeveloperDao> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<DeveloperDao> developers) {
        this.developers = developers;
    }

    public List<CompanyDao> getCompanies() {
        return companies;
    }

    public void setCompanies(List<CompanyDao> companies) {
        this.companies = companies;
    }

    public List<CustomerDao> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerDao> customers) {
        this.customers = customers;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ProjectTo{" +
                "idProject=" + idProject +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                ", date=" + date +
                ", developers=" + developers +
                ", companies=" + companies +
                ", customers=" + customers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectTo projectTo = (ProjectTo) o;
        return Double.compare(projectTo.cost, cost) == 0 && Objects.equals(name, projectTo.name) && Objects.equals(description, projectTo.description) && Objects.equals(date, projectTo.date) && Objects.equals(developers, projectTo.developers) && Objects.equals(companies, projectTo.companies) && Objects.equals(customers, projectTo.customers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, cost, date, developers, companies, customers);
    }
}
