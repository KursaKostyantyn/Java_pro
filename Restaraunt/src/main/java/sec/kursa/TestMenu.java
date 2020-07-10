package sec.kursa;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class TestMenu {
    private static List<Dish> dishes = new ArrayList<>();

    private static void fillList() {
        for (int i = 0; i < 10; i++) {
            dishes.add(new Dish("name" + i, i + 1.0, i + 100.0));
        }
    }

    private static void fillDiscountList() {
        for (int i = 10; i < 15; i++) {
            dishes.add(new Dish("name" + i, i + 1.0, i + 100.0, true));
        }
    }

    public static void addTestDishes(EntityManager em) {
        fillList();
        fillDiscountList();
        em.getTransaction().begin();
        try {
            for (Dish d : dishes) {
                em.persist(d);
                System.out.println("dish" + d + " added");
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }

    }

}
