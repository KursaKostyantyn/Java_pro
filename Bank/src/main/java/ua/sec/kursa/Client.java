package ua.sec.kursa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "client_name", nullable = false)
    private String name;

    @Column(name = "client_phone", nullable = false)
    private long phone;

    @Column(name = "client_accounts")
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Account> accounts = new ArrayList<>();


    public Client() {
    }

    public Client(String name, long phone, List<Account> accounts) {
        this.name = name;
        this.phone = phone;
        this.accounts = accounts;
    }

    public Client(String name, long phone) {
        this.name = name;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void addAccount(Account account) {
        account.setClient(this);
        accounts.add(account);
    }


    @Override
    public String toString() {
        StringBuilder ac = new StringBuilder();
        for (Account a : accounts) {
            ac.append(a);
        }
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone=" + phone +
                ", accounts=" + ac.toString() +
                '}';
    }
}
