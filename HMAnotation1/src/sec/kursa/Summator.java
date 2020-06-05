package sec.kursa;

public class Summator {

    public static int sum (int a, int b){
        int s = a+b;
        return s;
    }

    @Test(a=2, b= 5)
    public  static boolean sumTest (int a, int b){
        int s = a+b;
        boolean res = s == sum(a,b);
        return res;
    }
}
