package aimprosoft.task.usermanagment.entity;

public class Department {
    private String name;
    private String city;
    private Long id;

    public Department(String name, String city, Long id) {
        this.name = name;
        this.city = city;
        this.id = id;
    }

    public Department(Long id) {
        this.id = id;
    }

    public Department() {
    }

    public Department(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
