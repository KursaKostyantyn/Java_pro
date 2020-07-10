package sec.kursa;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.security.sasl.SaslClient;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class WorkWithMenu {

    public static void addDishToMenu(EntityManager em) {
        Scanner sc = new Scanner((System.in));
        try {
            System.out.println("Input dish name");
            String name = sc.nextLine();
            System.out.println("Input dish price");
            double price = sc.nextDouble();
            System.out.println("Input dish weight");
            double weight = sc.nextDouble();
            em.getTransaction().begin();
            try {
                Dish dish = new Dish(name, price, weight);
                em.persist(dish);
                em.getTransaction().commit();
                System.out.println("dish added");
            } catch (Exception ex) {
                System.out.println("something wrong");
                em.getTransaction().rollback();

            }
        } catch (InputMismatchException ex) {
            System.out.println("incorrect input type of column");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void findDishById(EntityManager em) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("input id dish");
            String sId = sc.nextLine();
            long id = Long.parseLong(sId);
            Dish dish = em.find(Dish.class, id);
            if (dish == null) {
                System.out.println("dish not found");
                return;
            }
            System.out.println(dish);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    public static void deleteDishById(EntityManager em) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("input id dish for delete");
            long id = sc.nextLong();
            Dish dish = em.find(Dish.class, id);
            if (dish == null) {
                System.out.println("dish not found");
                return;
            }
            em.getTransaction().begin();
            try {
                em.remove(dish);
                em.getTransaction().commit();
                System.out.println("dish deleted");
            } catch (Exception ex) {
                em.getTransaction().rollback();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void findByMinMaxPrice(EntityManager em) {
        Scanner sc = new Scanner(System.in);
        System.out.println(" input min price");
        double min = sc.nextDouble();
        System.out.println(" input max price");
        double max = sc.nextDouble();
        Query query = em.createQuery(" SELECT d FROM Dish d WHERE d.price >= :min AND d.price <= :max", Dish.class);
        query.setParameter("min", min);
        query.setParameter("max", max);
        System.out.println("min = " + min);
        List<Dish> dishes = query.getResultList();
        System.out.println("list dish size = " + dishes.size());
        for (Dish d : dishes) {
            System.out.println(d);
        }
    }

    public static void setDiscount(EntityManager em) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input dish name");
        String name = sc.nextLine();
        System.out.println("Input discount size");
        double discountSize = sc.nextDouble();
        Query query = em.createQuery("SELECT d FROM Dish d WHERE d.name = :name", Dish.class);
        query.setParameter("name", name);
        Dish dish = null;
        try {
            dish = (Dish) query.getSingleResult();
            System.out.println(dish);
        } catch (NoResultException e) {
            System.out.println("Dish not found");
            return;
        } catch (NonUniqueResultException e) {
            e.printStackTrace();
            System.out.println("Non uniwue result");
            return;
        }
        em.getTransaction().begin();
        try {
            dish.setDiscount(true);
            dish.setSizeDiscount(discountSize);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("something wrong");
            em.getTransaction().rollback();
            return;
        }
    }

    public static void findDishWithDiscount(EntityManager em) {
        Query query = em.createQuery("SELECT d FROM Dish d WHERE d.discount = :discount");
        query.setParameter("discount", true);
        List<Dish> dishList = query.getResultList();
        if (dishList.size() == 0) {
            System.out.println("No dishes with discount");
            return;
        }
        for (Dish d : dishList) {
            System.out.println(d);
        }
    }

    public static List<Dish> getAllDishes(EntityManager em) {
        Query query = em.createQuery("SELECT d FROM Dish d");
        List<Dish> dishList = query.getResultList();
        return dishList;
    }

    public static void weightLimitation(EntityManager em) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input limit siza");
        double limit = sc.nextDouble();
        List<Dish> dishList = WorkWithMenu.getAllDishes(em);
        double sum = 0;
        for (Dish d : dishList) {
            sum += d.getWeight();
            if (sum <= limit) {
                System.out.println(d);
                System.out.println("sum = " + sum);
            }
        }
    }
}
