package by.epam.courierexchange.entity;

public enum OfferStatusType {
    ACCEPT("accept"),
    IN_PROCESS("in_process"),
    DENIED("denied"),
    DELETED("deleted"),
    WORKING("working"),
    COMPLETED("completed");

    private String status;

    OfferStatusType(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
