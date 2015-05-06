package testng.converters;

import conversion.exception.ConversionImpossibleException;
import conversion.impl.SimpleTypeConverter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import testng.testmodel.SimpleTestClass;
import testng.testmodel.VerySimpleTestClass;
import testng.testmodel.VerySimpleTestClassV2;
import testng.testmodel.VerySimpleTestClassV3;
import testng.testmodel.targets.SimpleTestClassBean;
import testng.testmodel.targets.VerySimpleTestClassBean;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static testng.testmodel.ObjectFactory.*;

public class SimpleTypeConverterTest {

    private static final String EXPECTED_STRING_PROPERTY_VALUE = "stringProp";
    private static final int EXPECTED_INT_PROPERTY_VALUE = 2;
    private static final boolean EXPECTED_BOOLEAN_PROPERTY_VALUE = true;
    private static final String CONVERT_TARGET_MISSING_ERROR_MESSAGE = "ConvertTarget missing. Target class can not be instantiated.";

    @Rule
    public ExpectedException rule = ExpectedException.none();

    @Test
    public void testConvertClassWithoutChildren() {
        VerySimpleTestClass classWithoutChildren = createTestObjectWithoutChildren();
        VerySimpleTestClassBean bean = classWithoutChildren.convert();

        assertThat(bean, notNullValue());
        assertThat(bean.getStringProp(), is(EXPECTED_STRING_PROPERTY_VALUE));
        assertThat(bean.getIntProp(), is(EXPECTED_INT_PROPERTY_VALUE));
        assertThat(bean.isBooleanProp(), is(EXPECTED_BOOLEAN_PROPERTY_VALUE));
    }

    @Test
    public void testConvertClassWithoutConvertTargetAnnotation() {
        VerySimpleTestClassV2 classWithoutChildren = createTestObjectWithoutConvertTargetAnnotation();

        rule.expect(ConversionImpossibleException.class);
        rule.expectMessage(CONVERT_TARGET_MISSING_ERROR_MESSAGE);
        classWithoutChildren.convert();
    }

    @Test
    public void testExcludePropertyFromConversion() {
        VerySimpleTestClassV3 objectWithExcludeProperty = createTestObjectWithExcludeProperty();

        VerySimpleTestClassBean bean = objectWithExcludeProperty.convert();

        assertThat(bean, notNullValue());
        assertThat(bean.getStringProp(), is(EXPECTED_STRING_PROPERTY_VALUE));
        assertThat(bean.getIntProp(), is(EXPECTED_INT_PROPERTY_VALUE));
        assertThat(bean.isBooleanProp(), is(EXPECTED_BOOLEAN_PROPERTY_VALUE));
        assertThat(bean.getExcludeProp(), nullValue());
    }

    @Test
    public void testSkipPropertyFromConversion() {
        VerySimpleTestClassV3 objectWithExcludeProperty = createTestObjectWithSkipProperty();

        VerySimpleTestClassBean bean = objectWithExcludeProperty.convert();

        assertThat(bean, notNullValue());
        assertThat(bean.getStringProp(), is(EXPECTED_STRING_PROPERTY_VALUE));
        assertThat(bean.getIntProp(), is(EXPECTED_INT_PROPERTY_VALUE));
        assertThat(bean.isBooleanProp(), is(EXPECTED_BOOLEAN_PROPERTY_VALUE));
        assertThat(bean.getSkipProp(), nullValue());
    }

    @Test
    public void testSkipNullPropertyFromConversion() {
        VerySimpleTestClassV3 objectWithExcludeProperty = createTestObjectWithNullProperty();

        VerySimpleTestClassBean bean = objectWithExcludeProperty.convert();

        assertThat(bean, notNullValue());
        assertThat(bean.getStringProp(), is(EXPECTED_STRING_PROPERTY_VALUE));
        assertThat(bean.getIntProp(), is(EXPECTED_INT_PROPERTY_VALUE));
        assertThat(bean.isBooleanProp(), is(EXPECTED_BOOLEAN_PROPERTY_VALUE));
        assertThat(bean.getNullProp(), nullValue());
    }

    @Test
    public void testConvertClassWithChild() {
        SimpleTestClass objectWithChild = createTestObjectWithChild();

        SimpleTestClassBean bean = objectWithChild.convert();

        assertThat(bean, notNullValue());
        assertThat(bean.getStringProp(), is(EXPECTED_STRING_PROPERTY_VALUE));
        assertThat(bean.getIntProp(), is(EXPECTED_INT_PROPERTY_VALUE));
        assertThat(bean.isBooleanProp(), is(EXPECTED_BOOLEAN_PROPERTY_VALUE));
        assertThat(bean.getVerySimple(), notNullValue());
        assertThat(bean.getVerySimple().getStringProp(), is(EXPECTED_STRING_PROPERTY_VALUE));
        assertThat(bean.getVerySimple().getIntProp(), is(EXPECTED_INT_PROPERTY_VALUE));
        assertThat(bean.getVerySimple().isBooleanProp(), is(EXPECTED_BOOLEAN_PROPERTY_VALUE));
    }

}
