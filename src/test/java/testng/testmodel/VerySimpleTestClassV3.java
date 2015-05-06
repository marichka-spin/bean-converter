package testng.testmodel;

import conversion.annotation.ConvertTarget;
import conversion.annotation.TargetProperty;
import testng.testmodel.targets.VerySimpleTestClassBean;

@ConvertTarget(VerySimpleTestClassBean.class)
public class VerySimpleTestClassV3 extends VerySimpleTestClass {

    @TargetProperty(exclude = true)
    private String excludeProp;
    @TargetProperty(exclude = true)
    private String skipProp;
    private String nullProp;

    public String getExcludeProp() {
        return excludeProp;
    }

    public void setExcludeProp(String excludeProp) {
        this.excludeProp = excludeProp;
    }

    public String getSkipProp() {
        return skipProp;
    }

    public void setSkipProp(String skipProp) {
        this.skipProp = skipProp;
    }

    public String getNullProp() {
        return nullProp;
    }

    public void setNullProp(String nullProp) {
        this.nullProp = nullProp;
    }
}
