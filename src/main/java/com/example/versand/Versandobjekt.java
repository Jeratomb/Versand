package com.example.versand;

import java.time.LocalDate;

public class Versandobjekt {
    public String iD;
    public LocalDate placed;
    public Kunde from;
    public Kunde to;

    public String express;
    public String altLoc;
    public String altLocPlace;
    public String insured;
    public String insuranceType;

    public String packageType;

    public String getiD() {
        return iD;
    }

    public void setiD(String iD) {
        this.iD = iD;
    }

    public LocalDate getPlaced() {
        return placed;
    }

    public void setPlaced(LocalDate placed) {
        this.placed = placed;
    }

    public Kunde getFrom() {
        return from;
    }

    public void setFrom(Kunde from) {
        this.from = from;
    }

    public Kunde getTo() {
        return to;
    }

    public void setTo(Kunde to) {
        this.to = to;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public String getAltLoc() {
        return altLoc;
    }

    public void setAltLoc(String altLoc) {
        this.altLoc = altLoc;
    }

    public String getInsured() {
        return insured;
    }

    public void setInsured(String insured) {
        this.insured = insured;
    }

    public String getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public String getAltLocPlace() {
        return altLocPlace;
    }

    public void setAltLocPlace(String altLocPlace) {
        this.altLocPlace = altLocPlace;
    }

    public Versandobjekt(String iD, LocalDate placed, Kunde from, Kunde to, String express, String altLoc, String altLocPlace, String insured, String insuranceType, String packageType) {
        this.iD = iD;
        this.placed = placed;
        this.from = from;
        this.to = to;
        this.express = express;
        this.altLoc = altLoc;
        this.altLocPlace = altLocPlace;
        this.insured = insured;
        this.insuranceType = insuranceType;
        this.packageType = packageType;
    }

}
