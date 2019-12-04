package by.epam.courierexchange.entity;
import java.time.LocalDate;
import java.time.LocalTime;

public class ClientOffer extends Offer {
    private String comment;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public ClientOffer() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
