package testng.testmodel;


import conversion.annotation.ConvertTarget;
import conversion.annotation.TargetProperty;
import conversion.impl.SimpleTypeConverter;
import testng.testmodel.targets.SimpleTestClassBean;

@ConvertTarget(SimpleTestClassBean.class)
public class SimpleTestClass extends VerySimpleTestClass {

    @TargetProperty(complex = true)
    private VerySimpleTestClass verySimple;

    public VerySimpleTestClass getVerySimple() {
        return verySimple;
    }

    public void setVerySimple(VerySimpleTestClass verySimple) {
        this.verySimple = verySimple;
    }

    @Override
    public SimpleTestClassBean convert() {
        SimpleTypeConverter<VerySimpleTestClass, SimpleTestClassBean> converter = new SimpleTypeConverter<>();
        return converter.convert(this);
    }
}
