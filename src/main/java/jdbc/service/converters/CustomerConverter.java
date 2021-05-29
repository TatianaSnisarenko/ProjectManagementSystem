package jdbc.service.converters;

import jdbc.dao.entity.CustomerDao;
import jdbc.dto.CustomerTo;
import jdbc.util.RelationsUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerConverter {

    public static CustomerDao toCustomerDao(CustomerTo customerTo) {
        CustomerDao customerDao = new CustomerDao();
        customerDao.setIdCustomer(customerTo.getIdCustomer());
        customerDao.setName(customerTo.getName());
        customerDao.setCity(customerTo.getCity());
        return customerDao;
    }

    public static CustomerTo fromCustomerDao(CustomerDao customerDao) {
        CustomerTo customerTo = new CustomerTo();
        customerTo.setIdCustomer(customerDao.getIdCustomer());
        customerTo.setName(customerDao.getName());
        customerTo.setCity(customerDao.getCity());
        customerTo.setProjects(RelationsUtils.getAllProjectsForCustomer(customerDao.getIdCustomer()));
        return customerTo;
    }

    public static CustomerDao toCustomerDao(ResultSet resultSet) throws SQLException {
        CustomerDao customerDao = new CustomerDao();
        customerDao.setIdCustomer(resultSet.getInt("id_customer"));
        customerDao.setName(resultSet.getString("name"));
        customerDao.setCity(resultSet.getString("city"));
        return customerDao;
    }

    public static List<CustomerTo> allFromCustomerDao(List<CustomerDao> customerDaos) {
        return customerDaos.stream()
                .map(CustomerConverter::fromCustomerDao)
                .collect(Collectors.toList());
    }

}
