package sec.kursa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class SelectMenu {
    static EntityManagerFactory emf;
    static EntityManager em;

    public static void select() {
        Scanner sc = new Scanner((System.in));
        try {
            emf = Persistence.createEntityManagerFactory("Restaraunt");
            em = emf.createEntityManager();
            System.out.println("program start");
            TestMenu.addTestDishes(em);
            while (true) {
                System.out.println("1: add dish");
                System.out.println("2: find dish by id");
                System.out.println("3: delete dish by id");
                System.out.println("4: find min max price");
                System.out.println("5: set discount");
                System.out.println("6: find dishes with discount");
                System.out.println("7: find dishes by weight limit");
                String s = sc.nextLine();
                switch (s) {
                    case "1":
                        WorkWithMenu.addDishToMenu(em);
                        break;
                    case "2":
                        WorkWithMenu.findDishById(em);
                        break;
                    case "3":
                        WorkWithMenu.deleteDishById(em);
                        break;
                    case "4":
                        WorkWithMenu.findByMinMaxPrice(em);
                        break;
                    case "5":
                        WorkWithMenu.setDiscount(em);
                        break;
                    case "6":
                        WorkWithMenu.findDishWithDiscount(em);
                        break;
                    case "7":
                        WorkWithMenu.weightLimitation(em);
                        break;
                    default:
                        System.out.println("the end");
                        return;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            sc.close();
            em.close();
            emf.close();
        }

    }
}
