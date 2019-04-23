package aimprosoft.task.usermanagment.entity;

import org.joda.time.LocalDate;
import org.joda.time.Years;

import java.sql.Date;

public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Date birthday;
    private int salary;
    private String depName;

    public Employee() {
    }

    public Employee(Long id) {
        this.id = id;
    }

    public Employee(String firstName, String lastName, String email, Date birthday, int salary, String depName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthday = birthday;
        this.salary = salary;
        this.depName = depName;
    }

    public Employee(Long id, String firstName, String lastName, String email, Date birthday, int salary, String depName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthday = birthday;
        this.salary = salary;
        this.depName = depName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public int getAge() {
        LocalDate birthday = new LocalDate(this.birthday);
        LocalDate now = new LocalDate();
        Years age = Years.yearsBetween(birthday, now);
        return age.getYears();
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }
}
