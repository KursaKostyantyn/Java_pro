package ua.sec.kursa;

import javax.persistence.*;

@Entity
@Table(name = "Transactions")
public class Transaction {
    public enum operation {
        topUpAnAccount,
        transferToAccount,
        currencyConversion;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;


    public Transaction() {
    }

    public Transaction(Account account) {
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}
