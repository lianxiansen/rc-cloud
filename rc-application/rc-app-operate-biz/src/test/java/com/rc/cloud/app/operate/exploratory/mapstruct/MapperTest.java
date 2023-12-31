package com.rc.cloud.app.operate.exploratory.mapstruct;

import com.rc.cloud.app.operate.exploratory.mapstruct.data.Source;
import com.rc.cloud.app.operate.exploratory.mapstruct.data.Target;
import com.rc.cloud.app.operate.exploratory.mapstruct.mapper.DeepMapper;
import com.rc.cloud.app.operate.exploratory.mapstruct.mapper.SimpleMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.SecureRandom;

/**
 * @ClassName: MapStructTest
 * @Author: liandy
 * @Date: 2023/6/28 10:51
 * @Description: TODO
 */
@RunWith(SpringRunner.class)
public class MapperTest {
    private Source source;
    @Before
    public void doBefore() {
        int randomInt = new SecureRandom().nextInt();
        source=new Source();
        source.setId(randomInt);
        source.setName("myName");
        source.setTagSource("myTag");
    }
    @Test
    public void simpleMapper(){
        Target target= SimpleMapper.INSTANCE.convert(source);
        Assert.assertEquals("属性转换错误",source.getId(),target.getId());
    }
    @Test
    public void deepMapper(){
        Target target= DeepMapper.INSTANCE.convert(source);
        System.out.println(source.getName());
        Assert.assertEquals("属性转换错误",source.getName(),target.getName().getValue());
    }



}
