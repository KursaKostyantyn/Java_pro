package sec.kursa;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Serializer {
    public static String serialize (Object obj) throws IllegalArgumentException, IllegalAccessException {
        StringBuffer sb = new StringBuffer();
        Class<?> cls = obj.getClass();
        Field[] fields= cls.getDeclaredFields();
        for(Field field: fields){
            if(field.isAnnotationPresent(Save.class)){
                if(Modifier.isPrivate(field.getModifiers())){
                    field.setAccessible(true);
                }

                String fieldType =field.getType().toString();
                String fieldName = field.getName().toString();
                String fieldValue = field.get(obj).toString();

                sb.append(fieldType + "=" + fieldName + "=" + fieldValue + ";");
            }
        }

        return sb.toString();
    }

}
