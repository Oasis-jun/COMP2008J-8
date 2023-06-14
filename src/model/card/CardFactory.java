package model.card;

import java.util.ArrayList;
import java.util.List;

public class CardFactory {

    public static PropertyCard createOrangeCard(){
        return new PropertyCard("Orange", "img/Orange.jpg", 2, new int[]{1, 3, 5});
    }

    public static PropertyCard createPurleCard() {
        return new PropertyCard("Purple", "img/Purple.jpg", 2, new int[]{1, 2, 4});
    }

    public static PropertyCard createLightBlueCard() {
        return new PropertyCard("Light Blue", "img/Light Blue.jpg", 1, new int[]{1, 2, 3});
    }

    public static PropertyCard createBrownCard() {
        return new PropertyCard("Brown", "img/Brown.jpg", 1, new int[]{1, 2});
    }

    public static PropertyCard createRailroadCard() {
        return new PropertyCard("Railroad", "img/Railroad.jpg", 2, new int[]{1, 2, 3, 4});
    }

    public static PropertyCard createDarkBlueCard() {
        return new PropertyCard("Dark Blue", "img/Dark Blue.jpg", 4, new int[]{3, 8});
    }

    public static PropertyCard createGreenCard() {
        return new PropertyCard("Green", "img/Green.jpg", 4, new int[]{2, 4, 7});
    }

    public static PropertyCard createYellowCard() {
        return new PropertyCard("Yellow", "img/Yellow.jpg", 2, new int[]{1, 2});
    }

    public static PropertyCard createRedCard() {
        return new PropertyCard("Red", "img/Red.jpg", 3, new int[]{2, 3,6});
    }

    public static PropertyCard createUtilityCard() {
        return new PropertyCard("Utility", "img/Utility.jpg", 2, new int[]{1, 2});
    }

    public static Card createPurpleOrangeCard() {
        return new PropertyWildcard("Purple nad Orange","img/Purple_Orange(p).jpg",2,CardFactory.createOrangeCard(),CardFactory.createPurleCard());
    }

    public static Card createRedYellowCard() {
        return new PropertyWildcard("Red abd Yellow","img/Red_Yellow(p).jpg",3,CardFactory.createRedCard(),CardFactory.createYellowCard());
    }

    public static Card createMultiColorCard() {
        return new PropertyWildcard("Multi-color","img/multi-colour(p).jpg",0,createAllColorPropertyCardArray());
    }

    public static Card createLightBlueAndBrownCard() {
        return new PropertyWildcard("Light Blue and Brown","img/Light Blue_Brown(p).jpg",1,createLightBlueCard(),createBrownCard());
    }

    public static Card createLightBlueAndRailroadCard() {
        return new PropertyWildcard("Light Blue and Railroad","img/Light Blue_Railroad(p).jpg",4,createLightBlueCard(),createRailroadCard());
    }

    public static Card createDarkBlueAndGreenCard() {
        return new PropertyWildcard("Dark Blue and Green","img/Dark Blue_Green(p).jpg",4,createDarkBlueCard(),createGreenCard());
    }

    public static Card createRailroadAndGreenCard() {
        return new PropertyWildcard("Railroad and Green","img/Green_Railroad(p).jpg",4,createRailroadCard(),createGreenCard());
    }

    public static Card createUtilityAndRailroadCard() {
        return new PropertyWildcard("Utility and Railroad","img/Utility_Railroad(p).jpg",2,createUtilityCard(),createRailroadCard());
    }

    public static Card createOneMillionCard(){
        return new MoneyCard("1M","img/1M.jpg", 1);
    }

    public static Card createTwoMillionCard(){
        return new MoneyCard("2M","img/2M.jpg", 2);
    }
    public static Card createThreeMillionCard(){
        return new MoneyCard("3M","img/3M.jpg", 3);
    }

    public static Card createFourMillionCard(){
        return new MoneyCard("4M","img/4M.jpg", 4);
    }

    public static Card createFiveMillionCard(){
        return new MoneyCard("5M","img/5M.jpg", 5);
    }

    public static Card createTenMillionCard(){
        return new MoneyCard("10M","img/10M.jpg", 10);
    }

    public static Card createDarkBlueAndGreenRentCard(){
        return new RentCard("Dark Blue and Green Rent","img/Dark Blue_Green.jpg",1,createDarkBlueCard(),createGreenCard());
    }

    public static Card createLightBlueAndBrownRentCard(){
        return new RentCard("Light Blue and Brown Rent","img/Light Blue_Brown.jpg",1,createLightBlueCard(),createBrownCard());
    }

    public static Card createPurpleAndOrangeRentCard(){
        return new RentCard("Purple and Orange Rent","img/Purple_Orange.jpg",1,createPurleCard(),createOrangeCard());
    }
    public static Card createRailroadAndUtilityRentCard(){
        return new RentCard("Railroad and Utility Rent","img/Railroad_Utility.jpg",1,createRailroadCard(),createUtilityCard());
    }

    public static Card createRedAndYellowRentCard(){
        return new RentCard("Red and Yellow Rent","img/Red_Yellow.jpg",1,createRedCard(),createYellowCard());
    }

    public static Card createWildRentCard(){
        return new RentCard("Wild Rent","img/Wild Rent.jpg",1,createAllColorPropertyCardArray());
    }

    public static Card createPassGoActionCard(){
        return new ActionCard("Pass Go","img/Pass Go.jpg",1);
    }

    public static Card createDebtCollectorActionCard(){
        return new ActionCard("Debt Collector","img/Debt Collector.jpg",3);
    }

    public static Card createItsMyBirthdayActionCard(){
        return new ActionCard("It's My Birthday","img/It's My Birthday.jpg",2);
    }

    public static Card createDoubleTheRentActionCard(){
        return new ActionCard("Double The Rent","img/Double The Rent.jpg",1);
    }

    public static Card createDealBreakerActionCard(){
        return new ActionCard("Deal Breaker","img/Deal Breaker.jpg",5);
    }

    public static Card createForceDealActionCard(){
        return new ActionCard("Force Deal","img/Force Deal.jpg",3);
    }

    public static Card createHotelActionCard(){
        return new ActionCard("Hotel","img/Hotel.jpg",4);
    }

    public static Card createHouseActionCard(){
        return new ActionCard("House","img/House.jpg",3);
    }


    public static Card createJustSayNoActionCard(){
        return new ActionCard("Just Say No","img/Just Say No.jpg",4);
    }

    public static Card createSlyDealActionCard(){
        return new ActionCard("Sly Deal","img/Sly Deal.jpg",3);
    }


    public static List<Card> createMonopolyDrawCards(){
        List<Card> cards = new ArrayList<>();
        cards.add(CardFactory.createTenMillionCard());
        cards.add(CardFactory.createLightBlueAndBrownCard());
        cards.add(CardFactory.createLightBlueAndRailroadCard());
        cards.add(CardFactory.createDarkBlueAndGreenCard());
        cards.add(CardFactory.createRailroadAndGreenCard());
        cards.add(CardFactory.createUtilityAndRailroadCard());

        for (int i = 0; i < 2; i++) {
            cards.add(CardFactory.createBrownCard());
            cards.add(CardFactory.createDarkBlueCard());
            cards.add(CardFactory.createFiveMillionCard());
            cards.add(CardFactory.createDarkBlueAndGreenRentCard());
            cards.add(CardFactory.createRedAndYellowRentCard());
            cards.add(CardFactory.createPurpleAndOrangeRentCard());
            cards.add(CardFactory.createLightBlueAndBrownRentCard());
            cards.add(CardFactory.createLightBlueAndBrownRentCard());
            cards.add(CardFactory.createRailroadAndUtilityRentCard());
            cards.add(CardFactory.createPurpleOrangeCard());
            cards.add(CardFactory.createRedYellowCard());
            cards.add(CardFactory.createMultiColorCard());
            cards.add(CardFactory.createDoubleTheRentActionCard());

        }

        for (int i = 0; i < 3; i++) {
            cards.add(CardFactory.createFourMillionCard());
            cards.add(CardFactory.createThreeMillionCard());
            cards.add(CardFactory.createGreenCard());
            cards.add(CardFactory.createLightBlueCard());
            cards.add(CardFactory.createOrangeCard());
            cards.add(CardFactory.createPurleCard());
            cards.add(CardFactory.createUtilityCard());
            cards.add(CardFactory.createYellowCard());
            cards.add(CardFactory.createWildRentCard());
            cards.add(CardFactory.createDebtCollectorActionCard());
            cards.add(CardFactory.createItsMyBirthdayActionCard());
            cards.add(CardFactory.createForceDealActionCard());
            cards.add(CardFactory.createHotelActionCard());
            cards.add(CardFactory.createHouseActionCard());
            cards.add(CardFactory.createJustSayNoActionCard());
            cards.add(CardFactory.createSlyDealActionCard());

        }
        for (int i = 0; i < 4; i++) {
            cards.add(CardFactory.createRailroadCard());
        }
        for (int i = 0; i < 5; i++) {
            cards.add(CardFactory.createTwoMillionCard());
        }
        for (int i = 0; i < 6; i++) {
            cards.add(CardFactory.createOneMillionCard());
        }
        for (int i = 0; i < 10; i++) {
            cards.add(CardFactory.createPassGoActionCard());
        }
        return cards;
    }

// create the master card
    public static PropertyCard[] createAllColorPropertyCardArray(){
        return new PropertyCard[]{CardFactory.createOrangeCard(),CardFactory.createPurleCard(),CardFactory.createLightBlueCard(),CardFactory.createBrownCard(),CardFactory.createRailroadCard(),CardFactory.createDarkBlueCard(),CardFactory.createGreenCard(),CardFactory.createYellowCard(),CardFactory.createRedCard(),CardFactory.createUtilityCard()};
    }

// this is for test
    public static List<Card> createTestCard(){
        List<Card> cards = new ArrayList<>();
        cards.add(CardFactory.createTenMillionCard());
        cards.add(CardFactory.createLightBlueAndBrownCard());
        cards.add(CardFactory.createLightBlueAndRailroadCard());
        cards.add(CardFactory.createDarkBlueAndGreenCard());
        cards.add(CardFactory.createRailroadAndGreenCard());
        cards.add(CardFactory.createUtilityAndRailroadCard());
        for (int i = 0; i < 200; i++) {
           cards.add(CardFactory.createGreenCard());
           cards.add(CardFactory.createDarkBlueAndGreenRentCard());
           cards.add(CardFactory.createItsMyBirthdayActionCard());
           cards.add(createDoubleTheRentActionCard());
           cards.add(createJustSayNoActionCard());
        }
        for (int i = 0; i < 2; i++) {
            cards.add(CardFactory.createBrownCard());
            cards.add(CardFactory.createDarkBlueCard());
            cards.add(CardFactory.createFiveMillionCard());
            cards.add(CardFactory.createDarkBlueAndGreenRentCard());
            cards.add(CardFactory.createRedAndYellowRentCard());
            cards.add(CardFactory.createPurpleAndOrangeRentCard());
            cards.add(CardFactory.createLightBlueAndBrownRentCard());
            cards.add(CardFactory.createLightBlueAndBrownRentCard());
            cards.add(CardFactory.createRailroadAndUtilityRentCard());
            cards.add(CardFactory.createPurpleOrangeCard());
            cards.add(CardFactory.createRedYellowCard());
            cards.add(CardFactory.createMultiColorCard());
            cards.add(CardFactory.createDoubleTheRentActionCard());

        }

        for (int i = 0; i < 3; i++) {
            cards.add(CardFactory.createFourMillionCard());
            cards.add(CardFactory.createThreeMillionCard());
            cards.add(CardFactory.createGreenCard());
            cards.add(CardFactory.createLightBlueCard());
            cards.add(CardFactory.createOrangeCard());
            cards.add(CardFactory.createPurleCard());
            cards.add(CardFactory.createUtilityCard());
            cards.add(CardFactory.createYellowCard());
            cards.add(CardFactory.createWildRentCard());
            cards.add(CardFactory.createDebtCollectorActionCard());
            cards.add(CardFactory.createItsMyBirthdayActionCard());
            cards.add(CardFactory.createForceDealActionCard());
            cards.add(CardFactory.createHotelActionCard());
            cards.add(CardFactory.createHouseActionCard());
            cards.add(CardFactory.createJustSayNoActionCard());
            cards.add(CardFactory.createSlyDealActionCard());

        }
        for (int i = 0; i < 4; i++) {
            cards.add(CardFactory.createRailroadCard());
        }
        for (int i = 0; i < 5; i++) {
            cards.add(CardFactory.createTwoMillionCard());
        }
        for (int i = 0; i < 6; i++) {
            cards.add(CardFactory.createOneMillionCard());
        }
        for (int i = 0; i < 10; i++) {
            cards.add(CardFactory.createPassGoActionCard());
        }
        return cards;
    }

}
