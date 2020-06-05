package sec.kursa;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

public class DeSerializer {
    public static <T> T deSerializer (String str, Class <T> cls) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException, ClassNotFoundException {
       T res = cls.getDeclaredConstructor().newInstance();
       String[] trio = str.split(";");
       for (String t:trio){
           String[] param = t.split("=");
           if (param.length != 3){
               System.out.println( "Error parametrs");
               break;
           }
           String clazz = param[0];
           String name = param[1];
           String value =  param[2];
           Field field = cls.getDeclaredField(name);
           setFieldValue(field,clazz,name,value,res);

       }

        return res;
    }

    private static <T> void setFieldValue (Field field,String clazz,String name, String value, T res ) throws IllegalAccessException {
        if (Modifier.isPrivate(field.getModifiers())) {
            field.setAccessible(true);
        }
        if (field.isAnnotationPresent(Save.class)){
            if (field.getType() == int.class){
                field.set(res, Integer.parseInt(value));
            } else if (field.getType() == String.class){
                field.set(res,value);
            }
        }
    }
}
