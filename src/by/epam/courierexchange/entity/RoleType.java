package by.epam.courierexchange.entity;

public enum RoleType {
    ADMIN("admin"),
    COURIER("courier"),
    CLIENT("client");

    private String role;

    RoleType(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
