package aimprosoft.task.usermanagment.configuration;

import aimprosoft.task.usermanagment.entity.Employee;
import aimprosoft.task.usermanagment.repository.DepartmentDao;
import aimprosoft.task.usermanagment.repository.EmployeeDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class DaoFactoryImpl extends DaoFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(DaoFactoryImpl.class);

    @Override
    public EmployeeDao getEmployeeDao() {
        EmployeeDao employeeDao;
        try {
            Class employeeClass = Class.forName(property.getProperty(EMPLOYEE_DAO));
            employeeDao = (EmployeeDao) employeeClass.newInstance();
            employeeDao.setConnectionFactory(getConnectionFactory());
            LOGGER.info("Instance for EmployeeDau was created  -  " + employeeDao.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return employeeDao;
    }

    @Override
    public DepartmentDao getDepartmentDao() {
        DepartmentDao departmentDao;
        try {
            Class departmentClass = Class.forName(property.getProperty(DEPARTMENT_DAO));
            departmentDao = (DepartmentDao) departmentClass.newInstance();
            departmentDao.setConnectionFactory(getConnectionFactory());
            LOGGER.info("Instance for DepartmentDao was created  -  " + departmentDao.toString());
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return departmentDao;
    }

    public static void main(String[] args) {
        Collection<Employee> employee = new LinkedList<>();
        try {
            employee = DaoFactory.getInstance().getEmployeeDao().findByDepId("Education");
            for (Employee result : employee) {
                System.out.println(result.getFirstName() + "  " + result.getFirstName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
