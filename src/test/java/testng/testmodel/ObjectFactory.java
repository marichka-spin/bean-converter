package testng.testmodel;

public class ObjectFactory {

    public static VerySimpleTestClass createTestObjectWithoutChildren() {
        return createVerySimpleTestClass(new VerySimpleTestClass());
    }

    public static VerySimpleTestClassV2 createTestObjectWithoutConvertTargetAnnotation() {
        VerySimpleTestClassV2 classWithoutAnnotation = new VerySimpleTestClassV2();
        classWithoutAnnotation.setStringProp("stringProp");
        classWithoutAnnotation.setBooleanProp(true);
        classWithoutAnnotation.setIntProp(2);
        return classWithoutAnnotation;
    }

    public static VerySimpleTestClassV3 createTestObjectWithExcludeProperty() {
        VerySimpleTestClassV3 classWithExcludeProperty =
                (VerySimpleTestClassV3) createVerySimpleTestClass(new VerySimpleTestClassV3());
        classWithExcludeProperty.setExcludeProp("Exclude Property");
        return classWithExcludeProperty;
    }

    public static VerySimpleTestClassV3 createTestObjectWithSkipProperty() {
        VerySimpleTestClassV3 classWithSkipProperty =
                (VerySimpleTestClassV3) createVerySimpleTestClass(new VerySimpleTestClassV3());
        classWithSkipProperty.setSkipProp("Skip Property");
        return classWithSkipProperty;
    }

    public static VerySimpleTestClassV3 createTestObjectWithNullProperty() {
        return (VerySimpleTestClassV3) createVerySimpleTestClass(new VerySimpleTestClassV3());
    }

    public static SimpleTestClass createTestObjectWithChild() {
        SimpleTestClass classWithChild = (SimpleTestClass) createVerySimpleTestClass(new SimpleTestClass());
        classWithChild.setVerySimple(createVerySimpleTestClass(new VerySimpleTestClass()));
        return classWithChild;
    }

    private static VerySimpleTestClass createVerySimpleTestClass(VerySimpleTestClass testClass) {
        testClass.setStringProp("stringProp");
        testClass.setBooleanProp(true);
        testClass.setIntProp(2);
        return testClass;
    }

}
