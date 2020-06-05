package sec.kursa;

public class Test {
    @Save
    private int a;
    @Save
    private String b;
    private char d;

    public Test() {
    }

    public Test(int a, String b,  char d) {
        this.a = a;
        this.b = b;
        this.d = d;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }


    public char getD() {
        return d;
    }

    public void setD(char d) {
        this.d = d;
    }

    @Override
    public String toString() {
        return "Test{" +
                "a=" + a +
                ", b='" + b + '\'' +
                ", d=" + d +
                '}';
    }
}
