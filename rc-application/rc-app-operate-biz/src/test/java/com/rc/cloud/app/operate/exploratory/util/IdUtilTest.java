package com.rc.cloud.app.operate.exploratory.util;

import com.rc.cloud.app.operate.domain.model.brand.identifier.BrandId;
import com.rc.cloud.common.core.domain.IdUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName IdUtilTest
 * @Author liandy
 * @Date 2023/7/25 17:08
 * @Description TODO
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
public class IdUtilTest {
    @Test
    public void test(){
        List<String> idList=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            idList.add(UUID.randomUUID().toString().substring(0,31));
        }
        List<BrandId> branId= IdUtil.toList(idList, BrandId.class);
        List<String> list=IdUtil.toList(branId);
        System.out.println("");
    }


}
