package by.epam.courierexchange.entity;

public class User {
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private RoleType role;
    private UserStatusType status;

    public User() {
    }

    public User(int id, String email, String firstName, String lastName, RoleType role, UserStatusType status) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.status = status;
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

    public UserStatusType getStatus() {
        return status;
    }

    public void setStatus(UserStatusType status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equalsIgnoreCase(user.email) &&
                firstName.equalsIgnoreCase(user.firstName) &&
                lastName.equalsIgnoreCase(user.lastName) &&
                role == user.role &&
                status == user.status;
    }

    @Override
    public int hashCode() {
        return 11 * email.hashCode() +
                7 * firstName.hashCode() +
                5 * lastName.hashCode() +
                3 * role.hashCode() +
                2 * status.hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role +
                ", status=" + status +
                '}';
    }
}
