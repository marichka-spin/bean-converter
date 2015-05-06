package testng.testmodel;

import conversion.Convertable;
import conversion.impl.SimpleTypeConverter;
import testng.testmodel.targets.VerySimpleTestClassBean;

public class VerySimpleTestClassV2 implements Convertable<VerySimpleTestClassBean> {

    private String stringProp;
    private int intProp;
    private boolean booleanProp;

    public String getStringProp() {
        return stringProp;
    }

    public void setStringProp(String stringProp) {
        this.stringProp = stringProp;
    }

    public int getIntProp() {
        return intProp;
    }

    public void setIntProp(int intProp) {
        this.intProp = intProp;
    }

    public boolean isBooleanProp() {
        return booleanProp;
    }

    public void setBooleanProp(boolean booleanProp) {
        this.booleanProp = booleanProp;
    }

    @Override
    public VerySimpleTestClassBean convert() {
        SimpleTypeConverter<VerySimpleTestClassV2, VerySimpleTestClassBean> converter = new SimpleTypeConverter<>();
        return converter.convert(this);
    }
}
