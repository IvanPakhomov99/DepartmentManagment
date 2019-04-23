package aimprosoft.task.usermanagment.datatest;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TestDataGenerator {
    private static final String JDBC_URL = "jdbc:postgresql://localhost/dep_db";
    private static final String JDBC_USERNAME = "ivan";
    private static final String JDBC_PASSWORD = "Vania99";

    private static final String[] fullName = {"Ivan Pakhomov", "Polina Bilous", "Daniil Pilipetc", "Marina Pakhomova", "Mikhail Yuminov", "Yurii Pakhomov", "Galina Timoshenko"};
    private static final String[] depName = {"first", "second", "third"};
    private static final String[] CITIES = {"Kharkiv", "Kiyv", "Odessa"};
    private static final Date[] birthday = {
            Date.valueOf("1999-07-07"), Date.valueOf("1999-08-18"),
            Date.valueOf("1998-04-17"), Date.valueOf("1966-05-15"),
            Date.valueOf("1999-09-23"), Date.valueOf("1966-05-25"),
            Date.valueOf("1988-07-18")
    };

    private static final String[] email = {
            "ivan.pakhomov@nure.ua", "polina.bilous@nure.ua",
            "daniil.pilipetc@nure.ua", "marina.pakhomova@gmail.com",
            "mikhail.yuminov@nure.ua", "yurii.pakhomov@gmail.com",
            "galina.timoshenko@gmail.com"
    };


    private static final Random r = new Random();
    private static int idDepartment = 0;
    private static Date birthDay = null;

    public static void main(String[] args) throws Exception {
        List<Department> departments = loadDepartments();
        List<Employee> employees = loadEmployees();
        try (Connection c = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
            c.setAutoCommit(false);
            clearDb(c);
            for (Department p : departments) {
                createDepartment(c, p);
                System.out.println("Created " + p.name + " department");
            }
            for (Employee e : employees) {
                createEmployees(c, e);
                System.out.println("Created profile for " + e.firstName + " " + e.lastName);
            }
            c.commit();
            System.out.println("Data generated successful");
        }
    }

    private static void clearDb(Connection c) throws SQLException {
        Statement st = c.createStatement();
        st.executeUpdate("delete from department");
        st.executeQuery("select setval('department_seq', 1, false)");
        st.executeQuery("select setval('employee_seq', 1, false)");
        System.out.println("Db cleared");
    }

    private static class Department {
        private final String name;
        private final String city;

        private Department(String name, String city) {
            super();
            this.name = name;
            this.city = city;
        }

        @Override
        public String toString() {
            return String.format("Department [Name=%s, City=%s]", name, city);
        }
    }

    private static final class Employee {
        private final String firstName;
        private final String lastName;
        private final String email;
        private final Date birthday;
        private final int certificates;
        private final int depId;

        private Employee(String firstName, String lastName, String email, Date birthday, int certificates, int depId) {
            super();
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.birthday = birthday;
            this.certificates = certificates;
            this.depId = depId;
        }

        @Override
        public String toString() {
            return String.format("Profile [firstName=%s, lastName=%s]", firstName, lastName);
        }
    }

    private static void createDepartment(Connection c, Department department) throws SQLException, IOException {
        insertDepartmentData(c, department);
    }

    private static void insertDepartmentData(Connection c, Department department) throws SQLException, IOException {
        PreparedStatement ps = c.prepareStatement("insert into department values (nextval('department_seq'),?,?)");
        ps.setString(1, department.name);
        ps.setString(2, department.city);
        ps.executeUpdate();
        ps.close();
        idDepartment++;
    }

    private static List<Department> loadDepartments() {
        ArrayList<Department> list = new ArrayList<>(depName.length);
        int i = 0;
        for (String depName : depName) {
            list.add(new Department(depName, CITIES[i]));
            i++;
        }
        Collections.sort(list, (o1, o2) -> {
            int nameCompare = o1.name.compareTo(o2.name);
            if (nameCompare != 0) {
                return nameCompare;
            } else {
                return o1.city.compareTo(o2.city);
            }
        });
        return list;
    }

    private static void createEmployees(Connection c, Employee e) throws IOException, SQLException {
        insertEmployeesData(c, e);
    }

    private static void insertEmployeesData(Connection c, Employee e) throws SQLException, IOException {
        PreparedStatement statement = c.prepareStatement("insert into employee values (nextval('employee_seq'), ?, ?, ?, ?, ?, ?)");
        statement.setString(1, e.firstName);
        statement.setString(2, e.lastName);
        statement.setString(3, e.email);
        statement.setDate(4, e.birthday);
        statement.setInt(5, e.certificates);
        statement.setInt(6, e.depId);
        statement.executeUpdate();
        statement.close();
    }

    private static List<Employee> loadEmployees() {
        ArrayList<Employee> list = new ArrayList<>(fullName.length);
        int first = 0;
        int second = 1;
        int index = 0;
        for (String firstName : fullName) {
            String[] names = firstName.split(" ");
            list.add(new Employee(names[first], names[second], email[index], birthday[index], r.nextInt(10) + 1, r.nextInt(3) + 1));
            if (index < 7) {
                index++;
            }
        }
        Collections.sort(list, (o1, o2) -> {
            int firstNameCompare = o1.firstName.compareTo(o2.firstName);
            if (firstNameCompare != 0) {
                return firstNameCompare;
            } else {
                return o1.lastName.compareTo(o2.lastName);
            }
        });
        return list;
    }
}
