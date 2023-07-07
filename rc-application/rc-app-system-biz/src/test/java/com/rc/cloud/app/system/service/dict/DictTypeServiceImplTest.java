package com.rc.cloud.app.system.service.dict;

import com.rc.cloud.app.system.model.dict.SysDictTypeDO;
import com.rc.cloud.app.system.mapper.dict.DictTypeMapper;
import com.rc.cloud.app.system.vo.dict.type.DictTypeCreateReqVO;
import com.rc.cloud.app.system.vo.dict.type.DictTypeExportReqVO;
import com.rc.cloud.app.system.vo.dict.type.DictTypePageReqVO;
import com.rc.cloud.app.system.vo.dict.type.DictTypeUpdateReqVO;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.util.collection.ArrayUtils;
import com.rc.cloud.common.test.core.ut.BaseDbUnitTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Consumer;

import static cn.hutool.core.util.RandomUtil.randomEle;
import static com.rc.cloud.app.system.enums.ErrorCodeConstants.*;
import static com.rc.cloud.common.core.util.date.LocalDateTimeUtils.buildBetweenTime;
import static com.rc.cloud.common.core.util.date.LocalDateTimeUtils.buildTime;
import static com.rc.cloud.common.core.util.object.ObjectUtils.cloneIgnoreId;
import static com.rc.cloud.common.test.core.util.AssertUtils.assertPojoEquals;
import static com.rc.cloud.common.test.core.util.AssertUtils.assertServiceException;
import static com.rc.cloud.common.test.core.util.RandomUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@Import(DictTypeServiceImpl.class)
public class DictTypeServiceImplTest extends BaseDbUnitTest {

    @Resource
    private DictTypeServiceImpl dictTypeService;

    @Resource
    private DictTypeMapper dictTypeMapper;
    @MockBean
    private DictDataService dictDataService;

    @Test
    public void testGetDictTypePage() {
       // mock 数据
       SysDictTypeDO dbDictType = randomPojo(SysDictTypeDO.class, o -> { // 等会查询到
           o.setName("yunai");
           o.setType("芋艿");
           o.setStatus(CommonStatusEnum.ENABLE.getStatus());
           o.setCreateTime(buildTime(2021, 1, 15));
       });
       dictTypeMapper.insert(dbDictType);
       // 测试 name 不匹配
       dictTypeMapper.insert(cloneIgnoreId(dbDictType, o -> o.setName("tudou")));
       // 测试 type 不匹配
       dictTypeMapper.insert(cloneIgnoreId(dbDictType, o -> o.setType("土豆")));
       // 测试 status 不匹配
       dictTypeMapper.insert(cloneIgnoreId(dbDictType, o -> o.setStatus(CommonStatusEnum.DISABLE.getStatus())));
       // 测试 createTime 不匹配
       dictTypeMapper.insert(cloneIgnoreId(dbDictType, o -> o.setCreateTime(buildTime(2021, 1, 1))));
       // 准备参数
       DictTypePageReqVO reqVO = new DictTypePageReqVO();
       reqVO.setName("nai");
       reqVO.setType("艿");
       reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
       reqVO.setCreateTime(buildBetweenTime(2021, 1, 10, 2021, 1, 20));

       // 调用
       PageResult<SysDictTypeDO> pageResult = dictTypeService.getDictTypePage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbDictType, pageResult.getList().get(0));
    }

    @Test
    public void testGetDictTypeList_export() {
        // mock 数据
        SysDictTypeDO dbDictType = randomPojo(SysDictTypeDO.class, o -> { // 等会查询到
            o.setName("yunai");
            o.setType("芋艿");
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
            o.setCreateTime(buildTime(2021, 1, 15));
        });
        dictTypeMapper.insert(dbDictType);
        // 测试 name 不匹配
        dictTypeMapper.insert(cloneIgnoreId(dbDictType, o -> o.setName("tudou")));
        // 测试 type 不匹配
        dictTypeMapper.insert(cloneIgnoreId(dbDictType, o -> o.setType("土豆")));
        // 测试 status 不匹配
        dictTypeMapper.insert(cloneIgnoreId(dbDictType, o -> o.setStatus(CommonStatusEnum.DISABLE.getStatus())));
        // 测试 createTime 不匹配
        dictTypeMapper.insert(cloneIgnoreId(dbDictType, o -> o.setCreateTime(buildTime(2021, 1, 1))));
        // 准备参数
        DictTypeExportReqVO reqVO = new DictTypeExportReqVO();
        reqVO.setName("nai");
        reqVO.setType("艿");
        reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        reqVO.setCreateTime(buildBetweenTime(2021, 1, 10, 2021, 1, 20));

        // 调用
        List<SysDictTypeDO> list = dictTypeService.getDictTypeList(reqVO);
        // 断言
        assertEquals(1, list.size());
        assertPojoEquals(dbDictType, list.get(0));
    }

    @Test
    public void testGetDictType_id() {
        // mock 数据
        SysDictTypeDO dbDictType = randomDictTypeDO();
        dictTypeMapper.insert(dbDictType);
        // 准备参数
        String id = dbDictType.getId();

        // 调用
        SysDictTypeDO dictType = dictTypeService.getDictTypeById(id);
        // 断言
        assertNotNull(dictType);
        assertPojoEquals(dbDictType, dictType);
    }

    @Test
    public void testGetDictType_type() {
        // mock 数据
        SysDictTypeDO dbDictType = randomDictTypeDO();
        dictTypeMapper.insert(dbDictType);
        // 准备参数
        String type = dbDictType.getType();

        // 调用
        SysDictTypeDO dictType = dictTypeService.getDictTypeByType(type);
        // 断言
        assertNotNull(dictType);
        assertPojoEquals(dbDictType, dictType);
    }

    @Test
    public void testCreateDictType_success() {
        // 准备参数
        DictTypeCreateReqVO reqVO = randomPojo(DictTypeCreateReqVO.class,
                o -> o.setStatus(randomEle(CommonStatusEnum.values()).getStatus()));

        // 调用
        String dictTypeId = dictTypeService.createDictType(reqVO);
        // 断言
        assertNotNull(dictTypeId);
        // 校验记录的属性是否正确
        SysDictTypeDO dictType = dictTypeMapper.selectById(dictTypeId);
        assertPojoEquals(reqVO, dictType);
    }

    @Test
    public void testUpdateDictType_success() {
        // mock 数据
        SysDictTypeDO dbDictType = randomDictTypeDO();
        dictTypeMapper.insert(dbDictType);// @Sql: 先插入出一条存在的数据
        // 准备参数
        DictTypeUpdateReqVO reqVO = randomPojo(DictTypeUpdateReqVO.class, o -> {
            o.setId(dbDictType.getId()); // 设置更新的 ID
            o.setStatus(randomEle(CommonStatusEnum.values()).getStatus());
        });

        // 调用
        dictTypeService.updateDictType(reqVO);
        // 校验是否更新正确
        SysDictTypeDO dictType = dictTypeMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, dictType);
    }

    @Test
    public void testDeleteDictType_success() {
        // mock 数据
        SysDictTypeDO dbDictType = randomDictTypeDO();
        dictTypeMapper.insert(dbDictType);// @Sql: 先插入出一条存在的数据
        // 准备参数
        String id = dbDictType.getId();

        // 调用
        dictTypeService.deleteDictType(id);
        // 校验数据不存在了
        assertNull(dictTypeMapper.selectById(id));
    }

    @Test
    public void testDeleteDictType_hasChildren() {
        // mock 数据
        SysDictTypeDO dbDictType = randomDictTypeDO();
        dictTypeMapper.insert(dbDictType);// @Sql: 先插入出一条存在的数据
        // 准备参数
        String id = dbDictType.getId();
        // mock 方法
        when(dictDataService.countByDictType(eq(dbDictType.getType()))).thenReturn(1L);

        // 调用, 并断言异常
        assertServiceException(() -> dictTypeService.deleteDictType(id), DICT_TYPE_HAS_CHILDREN);
    }

    @Test
    public void testGetDictTypeList() {
        // 准备参数
        SysDictTypeDO dictTypeDO01 = randomDictTypeDO();
        dictTypeMapper.insert(dictTypeDO01);
        SysDictTypeDO dictTypeDO02 = randomDictTypeDO();
        dictTypeMapper.insert(dictTypeDO02);
        // mock 方法

        // 调用
        List<SysDictTypeDO> dictTypeDOList = dictTypeService.getDictTypeList();
        // 断言
        assertEquals(2, dictTypeDOList.size());
        assertPojoEquals(dictTypeDO01, dictTypeDOList.get(0));
        assertPojoEquals(dictTypeDO02, dictTypeDOList.get(1));
    }

    @Test
    public void testValidateDictDataExists_success() {
        // mock 数据
        SysDictTypeDO dbDictType = randomDictTypeDO();
        dictTypeMapper.insert(dbDictType);// @Sql: 先插入出一条存在的数据

        // 调用成功
        dictTypeService.validateDictTypeExists(dbDictType.getId());
    }

    @Test
    public void testValidateDictDataExists_notExists() {
        assertServiceException(() -> dictTypeService.validateDictTypeExists(randomLongId().toString()), DICT_TYPE_NOT_EXISTS);
    }

    @Test
    public void testValidateDictTypeUnique_success() {
        // 调用，成功
        dictTypeService.validateDictTypeUnique(randomLongId().toString(), randomString());
    }

    @Test
    public void testValidateDictTypeUnique_valueDuplicateForCreate() {
        // 准备参数
        String type = randomString();
        // mock 数据
        dictTypeMapper.insert(randomDictTypeDO(o -> o.setType(type)));

        // 调用，校验异常
        assertServiceException(() -> dictTypeService.validateDictTypeUnique(null, type),
                DICT_TYPE_TYPE_DUPLICATE);
    }

    @Test
    public void testValidateDictTypeUnique_valueDuplicateForUpdate() {
        // 准备参数
        String id = randomLongId().toString();
        String type = randomString();
        // mock 数据
        dictTypeMapper.insert(randomDictTypeDO(o -> o.setType(type)));

        // 调用，校验异常
        assertServiceException(() -> dictTypeService.validateDictTypeUnique(id, type),
                DICT_TYPE_TYPE_DUPLICATE);
    }

    @Test
    public void testValidateDictTypNameUnique_success() {
        // 调用，成功
        dictTypeService.validateDictTypeNameUnique(randomLongId().toString(), randomString());
    }

    @Test
    public void testValidateDictTypeNameUnique_nameDuplicateForCreate() {
        // 准备参数
        String name = randomString();
        // mock 数据
        dictTypeMapper.insert(randomDictTypeDO(o -> o.setName(name)));

        // 调用，校验异常
        assertServiceException(() -> dictTypeService.validateDictTypeNameUnique(null, name),
                DICT_TYPE_NAME_DUPLICATE);
    }

    @Test
    public void testValidateDictTypeNameUnique_nameDuplicateForUpdate() {
        // 准备参数
        String id = randomLongId().toString();
        String name = randomString();
        // mock 数据
        dictTypeMapper.insert(randomDictTypeDO(o -> o.setName(name)));

        // 调用，校验异常
        assertServiceException(() -> dictTypeService.validateDictTypeNameUnique(id, name),
                DICT_TYPE_NAME_DUPLICATE);
    }

    // ========== 随机对象 ==========

    @SafeVarargs
    private static SysDictTypeDO randomDictTypeDO(Consumer<SysDictTypeDO>... consumers) {
        Consumer<SysDictTypeDO> consumer = (o) -> {
            o.setStatus(randomEle(CommonStatusEnum.values()).getStatus()); // 保证 status 的范围
        };
        return randomPojo(SysDictTypeDO.class, ArrayUtils.append(consumer, consumers));
    }

}
