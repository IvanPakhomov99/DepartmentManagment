package aimprosoft.task.usermanagment.repository.impl;

import aimprosoft.task.usermanagment.configuration.ConnectionFactory;
import aimprosoft.task.usermanagment.entity.Department;
import aimprosoft.task.usermanagment.repository.DepartmentDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;

public class DepartmentDaoImpl implements DepartmentDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentDaoImpl.class);
    private static final String SELECT_ALL_QUERY = "SELECT id, name, city FROM department";
    private static final String INSERT_QUERY = "INSERT INTO department (name, city) VALUES(?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT id, name, city FROM department WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE department SET name = ?, city = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM department WHERE id = ?";
    /*private static final String SELECT_BY_NAMES = "SELECT id, name, city FROM department WHERE name = ?";
    private static final String SELECT_BY_CITY = "SELECT id, name, city FROM department WHERE city = ?";*/
    private ConnectionFactory connectionFactory;

    public DepartmentDaoImpl() {

    }

    public DepartmentDaoImpl(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Department create(Department department) throws SQLException {
        boolean rowCreated = false;
        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, department.getName());
            preparedStatement.setString(2, department.getCity());
            rowCreated = preparedStatement.executeUpdate() > 0;
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    department.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
            if (rowCreated) {
                LOGGER.info("New department was successful added to DB!");
            }
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }
        return department;
    }

    @Override
    public void update(Department department) throws SQLException {
        boolean rowUpdated = false;
        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, department.getName());
            preparedStatement.setString(2, department.getCity());
            preparedStatement.setLong(3, department.getId());
            rowUpdated = preparedStatement.executeUpdate() > 0;
            if (rowUpdated) {
                LOGGER.info("Successful updated department");
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }
    }

    @Override
    public void delete(Department department) throws SQLException {
        boolean rowDeleted = false;
        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY);
            preparedStatement.setLong(1, department.getId());
            rowDeleted = preparedStatement.executeUpdate() > 0;
            if (rowDeleted) {
                LOGGER.info("Department was successful deleted" + department.getId());
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }
    }

    @Override
    public Department find(Long id) throws SQLException {
        try {
            Department department = null;
            Connection connection = connectionFactory.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_QUERY);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                department = new Department();
                department.setId(resultSet.getLong("id"));
                department.setName(resultSet.getString("name"));
                department.setCity(resultSet.getString("city"));
            }
            resultSet.close();
            connection.close();
            preparedStatement.close();
            return department;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error: It's not able to get information from DB");
        }
    }

    @Override
    public Collection<Department> findAll() throws SQLException {
        Collection<Department> result = new LinkedList<>();
        try {
            Connection connection = connectionFactory.createConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);
            setDepartment(result, resultSet);
            resultSet.close();
            connection.close();
            statement.close();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return result;
    }

    /*@Override
    public Collection<Department> find(String Name) throws SQLException {
        Collection<Department> result = new LinkedList<>();
        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_NAMES);
            preparedStatement.setString(1, Name);
            ResultSet resultSet = preparedStatement.executeQuery();
            setDepartment(result, resultSet);
        } catch (SQLException | DataBaseException e) {
            e.printStackTrace();
        }
        return result;
    }*/

    private void setDepartment(Collection<Department> result, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Department department = new Department();
            department.setId(resultSet.getLong(1));
            department.setName(resultSet.getString(2));
            department.setCity(resultSet.getString(3));
            result.add(department);
        }
    }
}
