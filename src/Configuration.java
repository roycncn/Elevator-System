import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by test on 2016/11/16.
 */
public abstract class Configuration<T> {
    ArrayList<T> content;
    HashMap<String, T> index;

    public Configuration () {

    }

    public Configuration (File configFile, Field indexField, Class<?> componentClass) {
        content = new ArrayList<T>();
        index = new HashMap<String, T>();

        try {
            Gson gson = new Gson();
            JsonReader jsonReader = new JsonReader(new FileReader(configFile));
            T[] contentObjs = gson.fromJson(jsonReader, componentClass);
            for (T contentObj : contentObjs) {
                content.add(contentObj);
                index.put(String.valueOf(runGetter(indexField, contentObj)), contentObj);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Object runGetter(Field field, Object o) {
        // Find the correct method
        for (Method method : o.getClass().getMethods()) {
            if ((method.getName().startsWith("get")) && (method.getName().length() == (field.getName().length() + 3))) {
                if (method.getName().toLowerCase().endsWith(field.getName().toLowerCase())) {
                    // invoke method
                    try {
                        return method.invoke(o);
                    }
                    catch (InvocationTargetException | IllegalAccessException e) {
                        System.out.println("Could not determine method: " + method.getName());
                    }
                }
            }
        }
        return null;
    }

    private static Class<?> getListComponentClass(String listName) throws NoSuchFieldException {
        Field listField = Configuration.class.getDeclaredField(listName);
        ParameterizedType listType = (ParameterizedType) listField.getGenericType();
        return (Class<?>) listType.getActualTypeArguments()[0];
    }
}
