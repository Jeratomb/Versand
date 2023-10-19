package com.example.versand;

import java.time.LocalDate;

public class Versandobjekt {
    public String iD;
    public LocalDate placed;
    public Kunde from;
    public Kunde to;

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

    public Versandobjekt(String iD, LocalDate placed, Kunde from, Kunde to) {
        this.iD = iD;
        this.placed = placed;
        this.from = from;
        this.to = to;
    }
}
