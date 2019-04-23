package aimprosoft.task.usermanagment.repository.impl;

import aimprosoft.task.usermanagment.configuration.ConnectionFactory;
import aimprosoft.task.usermanagment.configuration.DaoFactory;
import aimprosoft.task.usermanagment.entity.Department;
import aimprosoft.task.usermanagment.entity.Employee;
import aimprosoft.task.usermanagment.exception.DataBaseException;
import aimprosoft.task.usermanagment.repository.EmployeeDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;

public class EmployeeDaoImpl implements EmployeeDao {
    private static final String SELECT_ALL_QUERY = "SELECT id, first_name, last_name, email, birth_day, salary, dep_name FROM employee";
    private static final String INSERT_QUERY = "INSERT INTO employee (first_name, last_name, email, birth_day, salary, dep_name) VALUES(?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE employee SET first_name = ?, last_name = ?, email = ?, birth_day = ?, salary =?, dep_name = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM employee WHERE id = ?";
    private static final String FIND_BY_ID_QUERY = "SELECT id, first_name, last_name, email, birth_day, salary, dep_name FROM employee WHERE id = ?";
    private static final String FIND_ALL_BY_DEP_NAME_QUERY = "SELECT id, first_name, last_name, email, birth_day, salary, dep_name FROM employee WHERE dep_name = ?";
    private static final String FIND_DEPARTMENT_BY_ID_QUERY = "SELECT id, name, city FROM department WHERE name = ?";
    //  private static final String SELECT_BY_NAMES = "SELECT id, name, city FROM department WHERE name = ?";

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDaoImpl.class);

    private ConnectionFactory connectionFactory;

    public EmployeeDaoImpl() {
    }

    public EmployeeDaoImpl(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Employee create(Employee employee) throws SQLException {
        boolean rowCreated;
        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY);
            setPS(employee, preparedStatement);
            PreparedStatement departmentPreparedStatement = connection.prepareStatement(FIND_DEPARTMENT_BY_ID_QUERY);
            departmentPreparedStatement.setString(1, employee.getDepName());
            if (departmentPreparedStatement.execute()) {
                rowCreated = preparedStatement.executeUpdate() > 0;
                if (rowCreated) {
                    LOGGER.info("New employee was successful added to DB!");
                }
            }
        } catch (DataBaseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Employee employee) throws SQLException {
        boolean rowUpdated;
        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(UPDATE_QUERY);
            setPS(employee, preparedStatement);
            preparedStatement.setLong(7, employee.getId());
            PreparedStatement departmentPreparedStatement = connection.prepareStatement(FIND_DEPARTMENT_BY_ID_QUERY);
            departmentPreparedStatement.setString(1, employee.getDepName());
            if (departmentPreparedStatement.execute()) {
                rowUpdated = preparedStatement.executeUpdate() > 0;
                if (rowUpdated) {
                    LOGGER.info("Employee was successful updated");
                }
            }
            connection.close();
            preparedStatement.close();
            departmentPreparedStatement.close();
        } catch (DataBaseException e) {
            e.printStackTrace();
        }
    }

    private void setPS(Employee employee, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, employee.getFirstName());
        preparedStatement.setString(2, employee.getLastName());
        preparedStatement.setString(3, employee.getEmail());
        preparedStatement.setDate(4, employee.getBirthday());
        preparedStatement.setInt(5, employee.getSalary());
        preparedStatement.setString(6, employee.getDepName());
    }

    @Override
    public void delete(Employee employee) throws SQLException {
        boolean rowDeleted;
        try {
            Connection connection;
            connection = connectionFactory.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY);
            preparedStatement.setLong(1, employee.getId());
            rowDeleted = preparedStatement.executeUpdate() > 0;
            if (rowDeleted) {
                LOGGER.info("Employee was successful deleted");
            }
            connection.close();
            preparedStatement.close();
        } catch (DataBaseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Employee find(Long id) throws SQLException {
        try {
            Employee employee = null;
            Connection connection;
            connection = connectionFactory.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_QUERY);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                employee = new Employee(id);
                resultSetting(resultSet, employee);
            }
            connection.close();
            preparedStatement.close();
            resultSet.close();
            return employee;
        } catch (DataBaseException e) {
            e.printStackTrace();
            throw new SQLException("Error: It's not able to get information from DB");
        }
    }

    @Override
    public Collection<Employee> findAll() throws SQLException {
        Collection<Employee> result = new LinkedList<>();
        try {
            Connection connection;
            connection = connectionFactory.createConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);
            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId(resultSet.getLong("id"));
                resultSetting(resultSet, employee);
                LOGGER.info("List of employees was successful received");
                result.add(employee);
            }
            connection.close();
            statement.close();
            resultSet.close();
        } catch (DataBaseException e) {
            throw new SQLException(e);
        }
        return result;
    }

    @Override
    public Collection<Employee> findByDepId(String depName) throws SQLException{
        Collection<Employee> result = new LinkedList<>();
        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_DEP_NAME_QUERY);
            preparedStatement.setString(1, depName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId(resultSet.getLong("id"));
                resultSetting(resultSet, employee);
                result.add(employee);
            }
            connection.close();
            preparedStatement.close();
            resultSet.close();
        } catch (DataBaseException e) {
            throw new SQLException(e);
        }
        return result;
    }

    private void resultSetting(ResultSet resultSet, Employee employee) throws SQLException {
        employee.setFirstName(resultSet.getString("first_name"));
        employee.setLastName(resultSet.getString("last_name"));
        employee.setBirthday(resultSet.getDate("birth_day"));
        employee.setEmail(resultSet.getString("email"));
        employee.setSalary(resultSet.getInt("salary"));
        employee.setDepName(resultSet.getString("dep_name"));
    }
}
