package aimprosoft.task.usermanagment.configuration;

import aimprosoft.task.usermanagment.repository.DepartmentDao;
import aimprosoft.task.usermanagment.repository.EmployeeDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public abstract class DaoFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(DaoFactory.class);

    static final String EMPLOYEE_DAO = "dao.Employee";
    static final String DEPARTMENT_DAO = "dao.Department";
    private static final String ROOT_PATH = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("application.properties"))
            .getPath().replaceAll("%20", " ");
    final static String DAO_FACTORY = "dao.factory";

    static Properties property;
    private static DaoFactory instance;

    static {
        property = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(ROOT_PATH);
            property.load(fis);
            LOGGER.info("Application properties file were successful downloaded!");
        } catch (FileNotFoundException e) {
            LOGGER.error("Error: Failed to get application properties file!", e);
        } catch (IOException e) {
            LOGGER.error("Error: IO Exception!", e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    LOGGER.error("Error: Failed to close file!", e);
                }
            }
        }
    }

    /*public static Properties getRequiredProperty() throws IOException {
        property = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(ROOT_PATH);
            property.load(fis);
        } catch (FileNotFoundException e) {
            LOGGER.error("ОШИБКА: Файл свойств отсуствует!", e);
        } catch (IOException e) {
            LOGGER.error("ОШИБКА: IO Ошибка!", e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    LOGGER.error("ОШИБКА: Неудачная попытка закрыть файл!", e);
                }
            }
        }
        return property;
    }*/

    public static synchronized DaoFactory getInstance() {
        if (instance == null) {
            Class factoryClass;
            try {
                factoryClass = Class.forName(property
                        .getProperty(DAO_FACTORY));
                instance = (DaoFactory) factoryClass.newInstance();
                LOGGER.info("Instance for DaoFactory was created  -  " + factoryClass.toString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    ConnectionFactory getConnectionFactory() {
        LOGGER.info("Creating ConnectionFactory...");
        return new ConnectionFactoryImpl(property);
    }

    public abstract DepartmentDao getDepartmentDao();

    public abstract EmployeeDao getEmployeeDao();
}
