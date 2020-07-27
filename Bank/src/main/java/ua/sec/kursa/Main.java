package ua.sec.kursa;

import javax.persistence.*;

public class Main {
    public static void main(String[] args) {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("Bank");
            EntityManager em = emf.createEntityManager();
            ForTest.addClient(em);
            ExchangeRate exchangeRate = new ExchangeRate(27.7, 32.05);
//        WorkWithClient.addClient(em);
//        WorkWithClient.findClientByName(em);
            Client client = WorkWithClient.findClientById(em);
            System.out.println(client);
            WorkWithClient.topUpAnAccount(client, 100, Account.Currency.EUR, em);
            System.out.println(client);
            System.out.println("from uah to usd");
            WorkWithClient.exchangeCurrency(client, Account.Currency.UAH, Account.Currency.USD, 10, exchangeRate, em);
            System.out.println(client);
            System.out.println("from uah to eur");
            WorkWithClient.exchangeCurrency(client, Account.Currency.UAH, Account.Currency.EUR, 10, exchangeRate, em);
            System.out.println(client);
            System.out.println("from eur to usd");
            WorkWithClient.exchangeCurrency(client, Account.Currency.EUR, Account.Currency.USD, 10, exchangeRate, em);
            System.out.println(client);
            System.out.println("from eur to uah");
            WorkWithClient.exchangeCurrency(client, Account.Currency.EUR, Account.Currency.UAH, 10, exchangeRate, em);
            System.out.println(client);
            System.out.println("from usd to uah");
            WorkWithClient.exchangeCurrency(client, Account.Currency.USD, Account.Currency.UAH, 10, exchangeRate, em);
            System.out.println(client);
            System.out.println("from usd to uer");
            WorkWithClient.exchangeCurrency(client, Account.Currency.USD, Account.Currency.EUR, 10, exchangeRate, em);
            System.out.println(client);
            System.out.println("Summary client" + client + " = " + WorkWithClient.summuryInUAH(client, exchangeRate));
            client = WorkWithClient.findClientById(em);
            Client client1 = WorkWithClient.findClientById(em);
            System.out.println("Client from = " + client);
            System.out.println("Client to = " + client1);
            WorkWithClient.transactionFromClientToCLient(client.getAccounts().get(1), client1.getAccounts().get(1), 30.0, exchangeRate, em);
            System.out.println("after transaction");
            System.out.println("Client from = " + client);
            System.out.println("Client to = " + client1);


            em.close();
            emf.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {


        }


    }
}
