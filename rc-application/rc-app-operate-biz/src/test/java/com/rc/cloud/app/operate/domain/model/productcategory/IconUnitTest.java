package com.rc.cloud.app.operate.domain.model.productcategory;

import com.rc.cloud.app.operate.domain.model.productcategory.valobj.Icon;
import com.rc.cloud.common.core.util.AssertUtils;
import com.rc.cloud.common.core.util.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @ClassName: IconTest
 * @Author: liandy
 * @Date: 2023/6/30 09:08
 * @Description: TODO
 */
public class IconUnitTest extends MockitoExtension {
    String invalidUrl;
    String validUrl;
    String emptyUrl;
    @Before
    public void setUp() {
        invalidUrl="invalidUrl";
        validUrl="http://www.576zx.com/storage/uploads/20220707/2b454a34bb5934ea82e5602ef14006e8.jpg";
        emptyUrl="";
    }

    @Test(expected=IllegalArgumentException.class)
    public void invalidUrl(){
        new Icon("invalidUrl");
    }
    @Test
    public void validUrl(){
        Icon icon = new Icon(validUrl);
        AssertUtils.equals(icon.getPictureUrl(),validUrl,"icon无效");
    }
    @Test
    public void emptyUrl(){
        Icon icon = new Icon(emptyUrl);
        Assert.assertTrue(StringUtils.isEmpty(icon.getPictureUrl()));
    }
}
