package sec.kursa;


import javax.persistence.*;

@Entity
@Table(name = "restoraunt_menu")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "dishes_name", nullable = false)
    private String name;

    @Column(name = "dishes_price", nullable = false)
    private double price;

    @Column(name = "dishes_weight", nullable = false)
    private double weight;

    @Column(name = "discount_is_present")
    private boolean discount;

    @Column(name = "discount_size")
    private double sizeDiscount;

    public Dish() {
    }

    public Dish(String name, double price, double weight) {
        this.name = name;
        this.price = price;
        this.weight = weight;
    }

    public Dish(String name, double price, double weight, boolean discount) {
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.discount = discount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean isDiscount() {
        return discount;
    }

    public void setDiscount(boolean discount) {
        this.discount = discount;
    }

    public double getSizeDiscount() {
        return sizeDiscount;
    }

    public void setSizeDiscount(double sizeDiscount) {
        this.sizeDiscount = sizeDiscount;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", weight=" + weight +
                ", discount=" + discount +
                ", sizeDiscount=" + sizeDiscount +
                '}';
    }
}
