package com.person.enums;

public class Clothes {

    private String id;

    private Color color;

    public Clothes(){

    }
    public Clothes(String id, Color color) {
        this.id = id;
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
