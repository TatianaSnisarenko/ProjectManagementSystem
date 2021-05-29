package jdbc.service.services;


import jdbc.dao.entity.CustomerDao;
import jdbc.dao.entity.ProjectDao;
import jdbc.dao.repositories.one_entity_repositories.Repository;
import jdbc.dao.repositories.relations_repositories.RelationsRepository;
import jdbc.dto.CustomerTo;
import jdbc.service.converters.CustomerConverter;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerService {
    private Repository<CustomerDao> repository;
    private Repository<ProjectDao> projectRepository;
    private RelationsRepository customersProjectsRepository;

    public CustomerService(Repository<CustomerDao> repository, Repository<ProjectDao> projectRepository, RelationsRepository customersProjectsRepository) {
        this.repository = repository;
        this.projectRepository = projectRepository;
        this.customersProjectsRepository = customersProjectsRepository;
    }

    public CustomerTo create(CustomerTo customerTo) {
        CustomerDao customerDao = CustomerConverter.toCustomerDao(customerTo);
        CustomerDao createdCustomerDa0 = repository.create(customerDao);
        addProjectsAndRelations(customerTo, createdCustomerDa0);
        return CustomerConverter.fromCustomerDao(createdCustomerDa0);
    }

    public CustomerTo findById(int customerId) {
        return CustomerConverter.fromCustomerDao(repository.findById(customerId));
    }

    public CustomerTo update(CustomerTo customerTo) {
        List<ProjectDao> ProjectsToBeDeleted = getProjectsToBeDeleted(customerTo);
        CustomerDao customerDao = CustomerConverter.toCustomerDao(customerTo);
        CustomerDao updatedCustomerDao = repository.update(customerDao);
        addProjectsAndRelations(customerTo, updatedCustomerDao);
        ProjectsToBeDeleted.forEach(projectDao -> customersProjectsRepository.delete(customerDao.getIdCustomer(), projectDao.getIdProject()));
        return CustomerConverter.fromCustomerDao(updatedCustomerDao);
    }

    public CustomerTo deleteById(int customerId) {
        this.findById(customerId).getProjects().stream().
                forEach(projectDao -> customersProjectsRepository.delete(customerId, projectDao.getIdProject()));
        return CustomerConverter.fromCustomerDao(repository.deleteById(customerId));
    }

    public CustomerTo deleteByObject(CustomerTo customerTo) {
        return deleteById(customerTo.getIdCustomer());
    }

    public List<CustomerTo> findAll() {
        return CustomerConverter.allFromCustomerDao(repository.findAll());
    }

    private void addProjectsAndRelations(CustomerTo customerTo, CustomerDao createdCustomerDao) {
        List<ProjectDao> projects = customerTo.getProjects();
        projects.forEach(p -> projectRepository.create(p));
        projects.stream().forEach(p -> customersProjectsRepository.create(createdCustomerDao.getIdCustomer(), p.getIdProject()));
    }

    private List<ProjectDao> getProjectsToBeDeleted(CustomerTo customerTo) {
        List<ProjectDao> projectsOld = this.findById(customerTo.getIdCustomer()).getProjects();
        List<ProjectDao> projectsNew = customerTo.getProjects();
        return projectsOld.stream()
                .filter(projectDao -> !projectsNew.contains(projectDao))
                .collect(Collectors.toList());
    }
}
