package com.example.injector;

import com.example.annotations.AutoInjectable;
import com.example.annotations.Configuration;
import com.example.annotations.Default;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * @author  Alexanrd Spaskin
 *
 */
@Configuration(packageName = {"com.example.sorted"})
public class Injector {

    /**
     * list of class objects from the package
     */
    List<Object> objectPoll = new ArrayList<>();

    public Injector(){
        String[] configuration = Injector.class.getAnnotation(Configuration.class).packageName();

        for (String i : configuration){
            // getClassesInPackage() - returns all classes in a package
            List<Class<?>> classList = getClassesInPackage(i);

            for (Class<?> classInPackage : classList){
                try {
                    objectPoll.add(classInPackage.newInstance());
                } catch (IllegalAccessException | InstantiationException e) {
                    continue;
                }
            }
        }

    }

    /**
     * Method fills annotated fields
     * @param object
     * @param <T>
     * @return
     */
    public <T> T inject(T object) {
        if (objectPoll.isEmpty()) return null;
        Class<?> clazz = object.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();

        int pos = -1;
        for (int i = 0; i < declaredFields.length; i++)
            if (declaredFields[i].isAnnotationPresent(AutoInjectable.class)){
                pos = i;
                break;
            }
        if (pos == -1)
            return null;

        Class<?> typeOfTheField = declaredFields[pos].getType();

        try {
            declaredFields[pos].setAccessible(true);
            declaredFields[pos].set(object, findObjectToInject(typeOfTheField));

        } catch (IllegalAccessException | InjectorException e) {
            e.printStackTrace();
        }

        return object;
    }

    /**
     * The method looks for objects that can be embedded in the field
     * @param typeOfTheField type of the field
     * @return Object to be injected into annotation
     * @throws InjectorException
     */
    private Object findObjectToInject(Class<?> typeOfTheField) throws InjectorException {
        List<Object> result = new ArrayList<>();
        for (Object obj : objectPoll){
            Class[] interfaces = obj.getClass().getInterfaces();
            for (Class i : interfaces)
                if (i == typeOfTheField){
                    if (obj.getClass().isAnnotationPresent(Default.class)){
                        return obj;
                    }
                    result.add(obj);
                }
        }

        if (result.size() > 1){
            throw new InjectorException("More than one embeddable instance exists, but no default is selected");
        }
        if (result.size() == 0){
            throw new InjectorException("No embeddable objects found");
        }
        return result.get(0);
    }

    /**
     * Get classes in package
     * @param packageName package name
     * @return An array of classes in the package
     */
    private static final List<Class<?>> getClassesInPackage(String packageName) {
        String path = packageName.replaceAll("\\.", File.separator);
        List<Class<?>> classes = new ArrayList<>();
        String[] classPathEntries = System.getProperty("java.class.path").split(
                System.getProperty("path.separator")
        );

        String name;
        for (String classpathEntry : classPathEntries) {
            if (classpathEntry.endsWith(".jar")) {
                File jar = new File(classpathEntry);
                try {
                    JarInputStream is = new JarInputStream(new FileInputStream(jar));
                    JarEntry entry;
                    while((entry = is.getNextJarEntry()) != null) {
                        name = entry.getName();
                        if (name.endsWith(".class")) {
                            if (name.contains(path) && name.endsWith(".class")) {
                                String classPath = name.substring(0, entry.getName().length() - 6);
                                classPath = classPath.replaceAll("[\\|/]", ".");
                                classes.add(Class.forName(classPath));
                            }
                        }
                    }
                } catch (Exception ex) {
                    continue;
                }
            } else {
                try {
                    File base = new File(classpathEntry + File.separatorChar + path);
                    for (File file : base.listFiles()) {
                        name = file.getName();
                        if (name.endsWith(".class")) {
                            name = name.substring(0, name.length() - 6);
                            classes.add(Class.forName(packageName + "." + name));
                        }
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
        }

        return classes;
    }
}
