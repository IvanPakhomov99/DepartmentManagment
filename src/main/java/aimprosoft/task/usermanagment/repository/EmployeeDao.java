package aimprosoft.task.usermanagment.repository;

import aimprosoft.task.usermanagment.entity.Employee;

import java.sql.SQLException;
import java.util.Collection;

public interface EmployeeDao extends EntityDao<Employee> {
    Collection<Employee> findByDepId(String depName) throws SQLException;
}