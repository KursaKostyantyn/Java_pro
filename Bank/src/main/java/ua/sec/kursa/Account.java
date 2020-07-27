package ua.sec.kursa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account {
    public enum Currency {
        UAH,
        USD,
        EUR;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @Column
    private Currency currency;

    @Column
    private double amount;

    @Column
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();

    public Account() {
    }

    public Account(Currency currency, long amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public Account(Client client, Currency currency, long amount) {
        this.client = client;
        this.currency = currency;
        this.amount = amount;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", client=" + client +
                ", currency='" + currency + '\'' +
                ", amount=" + amount +
                '}';
    }
}
