package aimprosoft.task.usermanagment.repository;

import aimprosoft.task.usermanagment.configuration.ConnectionFactory;

import java.sql.SQLException;
import java.util.Collection;

public interface EntityDao<T> {
    T create(T entity) throws SQLException;

    void update(T entity) throws SQLException;

    void delete(T entity) throws SQLException;

    T find(Long id) throws SQLException;

    Collection<T> findAll() throws SQLException;

    void setConnectionFactory(ConnectionFactory connectionFactory);
}
