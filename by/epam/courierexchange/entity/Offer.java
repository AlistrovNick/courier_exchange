package by.epam.courierexchange.entity;

import java.util.ArrayList;
import java.util.List;

public class Offer {
    private int id;
    private int dealId;
    private User user;
    private List<String> goods;
    private OfferStatusType status;

    public Offer() {
        goods = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDealId() {
        return dealId;
    }

    public void setDealId(int dealId) {
        this.dealId = dealId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getGoods() {
        return goods;
    }

    public void setGoods(List<String> goods) {
        this.goods = goods;
    }

    public OfferStatusType getStatus() {
        return status;
    }

    public void setStatus(OfferStatusType status) {
        this.status = status;
    }
}
