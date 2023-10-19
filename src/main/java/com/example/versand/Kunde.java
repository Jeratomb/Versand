package com.example.versand;

public class Kunde {
    private String vName;
    private String lName;
    private String street;
    private String streetNr;
    private String plz;
    private String loc;

    public String getvName() {
        return vName;
    }

    public void setvName(String vName) {
        this.vName = vName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNr() {
        return streetNr;
    }

    public void setStreetNr(String streetNr) {
        this.streetNr = streetNr;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }
    public Kunde(String vName, String lName, String street, String streetNr, String plz, String loc) {
        this.vName = vName;
        this.lName = lName;
        this.street = street;
        this.streetNr = streetNr;
        this.plz = plz;
        this.loc = loc;
    }

}
