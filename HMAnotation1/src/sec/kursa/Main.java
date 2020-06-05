package sec.kursa;

import java.lang.reflect.Method;

public class Main {
    public static void main (String arg[]){
        try{
            Class <?> cls = Summator.class;
            Method[] methods = cls.getDeclaredMethods();
            for (Method method: methods){
                if (method.isAnnotationPresent(Test.class)){
                    Test temp = method.getAnnotation(Test.class);
                    Boolean bool = (Boolean) method.invoke(null, temp.a(),temp.b());
                    System.out.println (bool);
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
