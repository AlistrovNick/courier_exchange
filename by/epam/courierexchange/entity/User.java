package by.epam.courierexchange.entity;

public class User {
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private RoleType role;
    private RoleStatusType status;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public RoleStatusType getStatus() {
        return status;
    }

    public void setStatus(RoleStatusType status) {
        this.status = status;
    }
}
