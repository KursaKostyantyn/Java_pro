package ua.sec.kursa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class ExchangeRate {
    @Id
    @Column
    private int id;

    @Column
    private double usd;

    @Column
    private double eur;

    public ExchangeRate() {
    }

    public ExchangeRate(double usd, double eur) {
        this.usd = usd;
        this.eur = eur;
    }

    public int getId() {
        return id;
    }

    public double getUsd() {
        return usd;
    }

    public void setUsd(double usd) {
        this.usd = usd;
    }

    public double getEur() {
        return eur;
    }

    public void setEur(double eur) {
        this.eur = eur;
    }
}
