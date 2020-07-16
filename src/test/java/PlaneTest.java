import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PlaneTest {
    @Test
    public void checkCountOfPrivateFields() {
        List<Field> privateFields = new ArrayList<>();
        List<Field> allFields = Arrays.asList(Plane.class.getDeclaredFields());
        for (Field field : allFields) {
            if (Modifier.isPrivate(field.getModifiers())) {
                privateFields.add(field);
            }
        }

        Assert.assertEquals("You should have private fields only!", allFields.size(), privateFields.size());
        Assert.assertTrue("You should have at least 5 fields", privateFields.size() >= 5);
    }

    @Test
    public void checkThatBuilderClassIsPresent() {
        List<Class> planeInnerClasses = Arrays.asList(Plane.class.getClasses());
        Assert.assertFalse("PlaneBuilder class should be present as inner class", planeInnerClasses.isEmpty());
        Optional<Class> planeBuilderClass = planeInnerClasses.stream()
                .filter(Objects::nonNull)
                .filter(c -> c.getSimpleName().equals("PlaneBuilder"))
                .findFirst();
        Assert.assertTrue("PlaneBuilder class should be present", planeBuilderClass.isPresent());

        try {
            planeBuilderClass.get().getMethod("build");
        } catch (NoSuchMethodException e) {
            Assert.fail("Builder class should contain method 'build'");
        }
    }


    @Test
    public void checkThatBuildMethodIsNotPresentInMainClass() {
        try {
            Plane.class.getMethod("build");
        } catch (NoSuchMethodException e) {
            return;
        }
        Assert.fail("Method 'build' should not be present in the Plane.class");
    }

    @Test
    public void checkThatAllFieldsFromPlaneExistsInBuilderAsSetters() {
        List<Field> planeFields = Arrays.asList(Plane.class.getDeclaredFields());

        List<Class> planeInnerClasses = Arrays.asList(Plane.class.getClasses());
        Optional<Class> planeBuilderClass = planeInnerClasses.stream()
                .filter(Objects::nonNull)
                .filter(c -> c.getSimpleName().equals("PlaneBuilder"))
                .findFirst();
        Assert.assertTrue("PlaneBuilder class should be present", planeBuilderClass.isPresent());


        List<Method> builderMethods = Arrays.asList(planeBuilderClass.get().getMethods());
        for (Field f : planeFields) {
            String fieldName = f.getName();
            builderMethods.stream()
                    .filter(m -> m.getName().equalsIgnoreCase("set" + fieldName))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Setter for " + fieldName + " field should be present in builder"));
        }
    }

    @Test
    public void checkNoSettersArePresentInPlaneClass() {
        List<Field> planeFields = Arrays.asList(Plane.class.getDeclaredFields());
        Assert.assertFalse(planeFields.isEmpty());

        List<Method> planerMethods = Arrays.asList(Plane.class.getMethods());
        for (Field f : planeFields) {
            String fieldName = f.getName();
            Optional<Method> method = planerMethods.stream()
                    .filter(m -> m.getName().equalsIgnoreCase("set" + fieldName))
                    .findFirst();
            Assert.assertFalse(String.format("Setter for %s field should NOT be present in Plane class", fieldName), method.isPresent());
        }
    }

    @Test
    public void checkTheOnlyOnePlaneConstructorIsPresent() {
        List<Field> planeFields = Arrays.asList(Plane.class.getDeclaredFields());
        Assert.assertFalse(planeFields.isEmpty());

        List<Constructor<?>> constructors = Arrays.asList(Plane.class.getDeclaredConstructors());
        Assert.assertEquals("You should have only one constructor in the Plane.class", 1, constructors.size());

        int actualParameterCount = constructors.get(0).getParameterCount();

        Assert.assertEquals("Your constructor should have one parameter",
                1, actualParameterCount);

        Class<?> actualConstructorParameterType = constructors.get(0).getParameterTypes()[0];
        Class<?> expectedPlaneBuilderClass = Plane.class.getClasses()[0];
        Assert.assertEquals("Your constructor should have one parameter of PlaneBuilder class",
                expectedPlaneBuilderClass, actualConstructorParameterType);
    }
}