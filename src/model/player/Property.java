package model.player;

import model.card.PropertyCard;

import java.util.ArrayList;
import java.util.List;

public class Property {

    private String property;
    private int maxSetNum;
    private int rentPrice;

    private int hotelNum;

    private List<PropertyCard> propertyCardList;

    public Property(String property, int maxSetNum, int rentPrice) {
        this.property = property;
        this.rentPrice = rentPrice;
        this.maxSetNum = maxSetNum;
        propertyCardList= new ArrayList<>();
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public int getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(int rentPrice) {
        this.rentPrice = rentPrice;
    }
    public int getSetNumber() {
        return propertyCardList.size();
    }
    public int getMaxSetNum() {
        return maxSetNum;
    }

    public void setMaxSetNum(int maxSetNum) {
        this.maxSetNum = maxSetNum;
    }

    public List<PropertyCard> getPropertyCardList() {
        return propertyCardList;
    }
    @Override
    public String toString() {
        return property;
    }

    public void increaseSet(PropertyCard card) {
        if (propertyCardList.size()< maxSetNum){
            setRentPrice(card.getRentRule()[propertyCardList.size()]);
        }
        propertyCardList.add(card);
    }

    public void reduceProperty(PropertyCard card) {
        propertyCardList.remove(card);
        if (propertyCardList.size()< maxSetNum){
            if (propertyCardList.size()>0){
                setRentPrice(card.getRentRule()[propertyCardList.size()]);
            }else {
                setRentPrice(0);
            }
        }
    }
    public PropertyCard  reduceProperty() {
        PropertyCard card = propertyCardList.remove(0);
        if (propertyCardList.size()< maxSetNum){
            if (propertyCardList.size()>0){
                setRentPrice(card.getRentRule()[propertyCardList.size()]);
            }else {
                setRentPrice(0);
            }
        }
        return card;
    }
}
