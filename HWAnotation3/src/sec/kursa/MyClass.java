package sec.kursa;

public class MyClass {
        private int a;

    public MyClass(int a) {
        this.a = a;
    }

    public MyClass() {
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    @Override
    public String toString() {
        return ""+a;
    }
}
