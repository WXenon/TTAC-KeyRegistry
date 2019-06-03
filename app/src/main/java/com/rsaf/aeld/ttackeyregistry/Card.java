package com.rsaf.aeld.ttackeyregistry;

public class Card {
    private String movementKeyBunch;
    private String movementKeyNo;

    public Card(String movementKeyBunch, String movementKeyNo) {
        this.movementKeyBunch = movementKeyBunch;
        this.movementKeyNo = movementKeyNo;
    }

    public String getmovementKeyBunch() {
        return movementKeyBunch;
    }

    public String getmovementKeyNo() {
        return movementKeyNo;
    }

}