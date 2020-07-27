package ua.sec.kursa;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import java.util.List;
import java.util.Scanner;

public class WorkWithClient {

    public static void addClient(EntityManager em) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input client name");
        String name = sc.nextLine();
        System.out.println("Input client phone");
        long phone = sc.nextLong();
        try {
            em.getTransaction().begin();
            Client client = new Client(name, phone);
            em.persist(client);
            em.getTransaction().commit();
            System.out.println(" new Client is added" + client.toString());
        } catch (Exception ex) {
            em.getTransaction().rollback();
            System.out.println("Transaction is rollbacked");
        }

    }

    public static void findClientByName(EntityManager em) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input client name:");
        String name = sc.nextLine();
        Query query = em.createQuery("SELECT c FROM Client c WHERE c.name = :name", Client.class);
        query.setParameter("name", name);
        List<Client> clientList = query.getResultList();
        if (clientList.size() == 0) {
            System.out.println("no client with this name");
        }
        for (Client c : clientList) {
            System.out.println(c);
        }

    }

    public static Client findClientById(EntityManager em) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input client id:");
        Client client = new Client();
        try {
            int id = sc.nextInt();
            client = em.find(Client.class, id);
            if (client == null) {
                System.out.println("Client not found");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return client;
    }

    public static void topUpAnAccount(Client client, long value, Account.Currency currency, EntityManager em) {
        Account account = new Account();
        for (Account ac : client.getAccounts()) {
            if (ac.getCurrency() == currency) {
                account = ac;
            }
        }
        Transaction transaction = new Transaction(account);
        try {
            em.getTransaction().begin();
            System.out.println("accout was " + transaction.getAccount().getAmount());
            double temp = transaction.getAccount().getAmount() + value;
            System.out.println("account now" + temp);
            transaction.getAccount().setAmount(temp);
            em.persist(transaction);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    public static void exchangeCurrency(Client client, Account.Currency from, Account.Currency to, double value, ExchangeRate exchangeRate, EntityManager em) {
        if (from == to) {
            System.out.println("it's the same acoount");
        }
        List<Account> accounts = client.getAccounts();
        Account sender = new Account();
        Account recepient = new Account();
        for (Account ac : accounts) {
            if (ac.getCurrency() == from && ac.getAmount() < value) {
                System.out.println("There is no money");
                return;
            }
            if (ac.getCurrency() == from) {
                sender = ac;
            }
            if (ac.getCurrency() == to) {
                recepient = ac;
            }
        }
        if (from == Account.Currency.UAH) {
            exchangeFromUAH(sender, recepient, exchangeRate, em, to, value);
        }
        if (from == Account.Currency.USD) {
            exchangeFromUSD(sender, recepient, exchangeRate, em, to, value);
        }
        if (from == Account.Currency.EUR) {
            exchangeFromEUR(sender, recepient, exchangeRate, em, to, value);
        }

    }

    private static void exchangeFromUAH(Account sender, Account recepient, ExchangeRate exchangeRate, EntityManager em, Account.Currency to, double value) {
        if (to == Account.Currency.EUR) {
            try {
                em.getTransaction().begin();
                sender.setAmount(sender.getAmount() - value);
                recepient.setAmount(recepient.getAmount() + value / exchangeRate.getEur());
                em.persist(sender);
                em.persist(recepient);
                em.getTransaction().commit();
            } catch (Exception ex) {
                em.getTransaction().rollback();
            }
        }
        if (to == Account.Currency.USD) {
            try {
                em.getTransaction().begin();
                sender.setAmount(sender.getAmount() - value);
                recepient.setAmount(recepient.getAmount() + value / exchangeRate.getUsd());
                em.persist(sender);
                em.persist(recepient);
                em.getTransaction().commit();
            } catch (Exception ex) {
                em.getTransaction().rollback();
            }
        }
    }

    private static void exchangeFromUSD(Account sender, Account recepient, ExchangeRate exchangeRate, EntityManager em, Account.Currency to, double value) {
        if (to == Account.Currency.UAH) {
            try {
                em.getTransaction().begin();
                sender.setAmount(sender.getAmount() - value);
                recepient.setAmount(recepient.getAmount() + value * exchangeRate.getUsd());
                em.persist(sender);
                em.persist(recepient);
                em.getTransaction().commit();
            } catch (Exception ex) {
                em.getTransaction().rollback();
            }
        }
        if (to == Account.Currency.EUR) {
            try {
                em.getTransaction().begin();
                sender.setAmount(sender.getAmount() - value);
                recepient.setAmount(recepient.getAmount() + value * (exchangeRate.getUsd() / exchangeRate.getEur()));
                em.persist(sender);
                em.persist(recepient);
                em.getTransaction().commit();
            } catch (Exception ex) {
                em.getTransaction().rollback();
            }
        }
    }

    private static void exchangeFromEUR(Account sender, Account recepient, ExchangeRate exchangeRate, EntityManager em, Account.Currency to, double value) {
        if (to == Account.Currency.UAH) {
            try {
                em.getTransaction().begin();
                sender.setAmount(sender.getAmount() - value);
                recepient.setAmount(recepient.getAmount() + value * exchangeRate.getEur());
                em.persist(sender);
                em.persist(recepient);
                em.getTransaction().commit();
            } catch (Exception ex) {
                em.getTransaction().rollback();
            }
        }
        if (to == Account.Currency.USD) {
            try {
                em.getTransaction().begin();
                sender.setAmount(sender.getAmount() - value);
                recepient.setAmount(recepient.getAmount() + value * (exchangeRate.getEur() / exchangeRate.getUsd()));
                em.persist(sender);
                em.persist(recepient);
                em.getTransaction().commit();
            } catch (Exception ex) {
                em.getTransaction().rollback();
            }
        }
    }

    public static double summuryInUAH(Client client, ExchangeRate exchangeRate) {
        List<Account> accounts = client.getAccounts();
        double sum = 0;
        for (Account ac : accounts) {
            if (ac.getCurrency() == Account.Currency.UAH) {
                sum = sum + ac.getAmount();
            }
            if (ac.getCurrency() == Account.Currency.EUR) {
                sum = sum + ac.getAmount() * exchangeRate.getEur();
            }
            if (ac.getCurrency() == Account.Currency.USD) {
                sum = sum + ac.getAmount() * exchangeRate.getUsd();
            }

        }
        return sum;
    }

    public static void transactionFromClientToCLient(Account from, Account to, double value, ExchangeRate exchangeRate, EntityManager em) {
        if (from.getAmount() < value) {
            System.out.println("There is no money");
            return;
        }
        if (from.getCurrency() == to.getCurrency()) {
            try {
                em.getTransaction().begin();
                from.setAmount(from.getAmount() - value);
                to.setAmount(to.getAmount() + value);
                em.persist(from);
                em.persist(to);
                em.getTransaction().commit();
            } catch (Exception ex) {
                ex.printStackTrace();
                em.getTransaction().rollback();
            }

        }
        if (from.getCurrency() == Account.Currency.UAH) {
            exchangeFromUAH(from, to, exchangeRate, em, to.getCurrency(), value);
        }
        if (from.getCurrency() == Account.Currency.USD) {
            exchangeFromUSD(from, to, exchangeRate, em, to.getCurrency(), value);
        }
        if (from.getCurrency() == Account.Currency.EUR) {
            exchangeFromEUR(from, to, exchangeRate, em, to.getCurrency(), value);
        }
    }


}




