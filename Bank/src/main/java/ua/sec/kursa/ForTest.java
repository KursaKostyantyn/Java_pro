package ua.sec.kursa;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class ForTest {
    public static void addClient(EntityManager em) {
        List<Client> clients = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            clients.add(new Client("Vasya" + i, 380981234567L + i, makeAccounts()));
        }
        try {
            em.getTransaction().begin();
            for (Client c : clients) {
                em.persist(c);
            }
            em.getTransaction().commit();
            for (Client c : clients) {
                System.out.println("Id new client = " + c.getId());
            }
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }

    }

    public static List<Account> makeAccounts() {
        List<Account> ac = new ArrayList<>();
        int a = 100;
        for (Account.Currency c : Account.Currency.values()) {
            ac.add(new Account(c, a));
            a += 100;
        }
        return ac;
    }


}
