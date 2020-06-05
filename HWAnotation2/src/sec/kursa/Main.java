package sec.kursa;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        TextContainer tc= new TextContainer();
        Class<?> cls = tc.getClass();
        Field[] fields = cls.getDeclaredFields();
        boolean check = false;
        for (Field field: fields){
            if(field.isAnnotationPresent(SaveTo.class)){
               check = true;
            }
        }
        if (!check) {
            System.out.println("Error nothing to save");
        }
        Method[] methods = cls.getDeclaredMethods();
        try {
            for (Method method: methods){
                if(method.isAnnotationPresent(Saver.class)){
                    for (Field field:fields){
                        if (field.isAnnotationPresent(SaveTo.class)){
                            SaveTo saveTo = field.getAnnotation(SaveTo.class);
                            field.setAccessible(true);
                            String path =  saveTo.path();
                            String text = (String) field.get(tc);
                            method.invoke(tc,path,text);
                            System.out.println("Done!");
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}
