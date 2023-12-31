package com.rc.cloud.app.system.service.dict;

import com.rc.cloud.app.system.model.dict.SysDictDataPO;
import com.rc.cloud.app.system.model.dict.SysDictTypePO;
import com.rc.cloud.app.system.mapper.dict.DictDataMapper;
import com.rc.cloud.app.system.vo.dict.data.DictDataCreateReqVO;
import com.rc.cloud.app.system.vo.dict.data.DictDataExportReqVO;
import com.rc.cloud.app.system.vo.dict.data.DictDataPageReqVO;
import com.rc.cloud.app.system.vo.dict.data.DictDataUpdateReqVO;
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

import static com.rc.cloud.app.system.enums.ErrorCodeConstants.*;
import static com.rc.cloud.common.core.util.object.ObjectUtils.cloneIgnoreId;
import static com.rc.cloud.common.test.core.util.AssertUtils.assertPojoEquals;
import static com.rc.cloud.common.test.core.util.AssertUtils.assertServiceException;
import static com.rc.cloud.common.test.core.util.RandomUtils.*;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@Import(DictDataServiceImpl.class)
public class DictDataServiceImplTest extends BaseDbUnitTest {

    @Resource
    private DictDataServiceImpl dictDataService;

    @Resource
    private DictDataMapper dictDataMapper;
    @MockBean
    private DictTypeService dictTypeService;

    @Test
    public void testGetDictDataList() {
        // mock 数据
        SysDictDataPO dictDataDO1 = randomDictDataDO();
        dictDataDO1.setDictType("yunai");
        dictDataDO1.setSort(2);
        dictDataMapper.insert(dictDataDO1);
        SysDictDataPO dictDataDO2 = randomDictDataDO();
        dictDataDO2.setDictType("yunai");
        dictDataDO2.setSort(1);
        dictDataMapper.insert(dictDataDO2);
        // 准备参数

        // 调用
        List<SysDictDataPO> dictDataDOList = dictDataService.getDictDataList();
        // 断言
        assertEquals(2, dictDataDOList.size());
        assertPojoEquals(dictDataDO2, dictDataDOList.get(0));
        assertPojoEquals(dictDataDO1, dictDataDOList.get(1));
    }

    @Test
    public void testGetDictDataPage() {
        // mock 数据
        SysDictDataPO dbDictData = randomPojo(SysDictDataPO.class, o -> { // 等会查询到
            o.setLabel("芋艿");
            o.setDictType("yunai");
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
        });
        dictDataMapper.insert(dbDictData);
        // 测试 label 不匹配
        dictDataMapper.insert(cloneIgnoreId(dbDictData, o -> o.setLabel("艿")));
        // 测试 dictType 不匹配
        dictDataMapper.insert(cloneIgnoreId(dbDictData, o -> o.setDictType("nai")));
        // 测试 status 不匹配
        dictDataMapper.insert(cloneIgnoreId(dbDictData, o -> o.setStatus(CommonStatusEnum.DISABLE.getStatus())));
        // 准备参数
        DictDataPageReqVO reqVO = new DictDataPageReqVO();
        reqVO.setLabel("芋");
        reqVO.setDictType("yunai");
        reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());

        // 调用
        PageResult<SysDictDataPO> pageResult = dictDataService.getDictDataPage(reqVO);
        // 断言
        assertEquals(1, pageResult.getTotal());
        assertEquals(1, pageResult.getList().size());
        assertPojoEquals(dbDictData, pageResult.getList().get(0));
    }

    @Test
    public void testGetDictDataList_export() {
        // mock 数据
        SysDictDataPO dbDictData = randomPojo(SysDictDataPO.class, o -> { // 等会查询到
            o.setLabel("芋艿");
            o.setDictType("yunai");
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
        });
        dictDataMapper.insert(dbDictData);
        // 测试 label 不匹配
        dictDataMapper.insert(cloneIgnoreId(dbDictData, o -> o.setLabel("艿")));
        // 测试 dictType 不匹配
        dictDataMapper.insert(cloneIgnoreId(dbDictData, o -> o.setDictType("nai")));
        // 测试 status 不匹配
        dictDataMapper.insert(cloneIgnoreId(dbDictData, o -> o.setStatus(CommonStatusEnum.DISABLE.getStatus())));
        // 准备参数
        DictDataExportReqVO reqVO = new DictDataExportReqVO();
        reqVO.setLabel("芋");
        reqVO.setDictType("yunai");
        reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());

        // 调用
        List<SysDictDataPO> list = dictDataService.getDictDataList(reqVO);
        // 断言
        assertEquals(1, list.size());
        assertPojoEquals(dbDictData, list.get(0));
    }

    @Test
    public void testGetDictData() {
        // mock 数据
        SysDictDataPO dbDictData = randomDictDataDO();
        dictDataMapper.insert(dbDictData);
        // 准备参数
        String id = dbDictData.getId();

        // 调用
        SysDictDataPO dictData = dictDataService.getDictData(id);
        // 断言
        assertPojoEquals(dbDictData, dictData);
    }

    @Test
    public void testCreateDictData_success() {
        // 准备参数
        DictDataCreateReqVO reqVO = randomPojo(DictDataCreateReqVO.class,
                o -> o.setStatus(randomCommonStatus()));
        // mock 方法
        when(dictTypeService.getDictTypeByType(eq(reqVO.getDictType()))).thenReturn(randomDictTypeDO(reqVO.getDictType()));

        // 调用
        String dictDataId = dictDataService.createDictData(reqVO);
        // 断言
        assertNotNull(dictDataId);
        // 校验记录的属性是否正确
        SysDictDataPO dictData = dictDataMapper.selectById(dictDataId);
        assertPojoEquals(reqVO, dictData);
    }

    @Test
    public void testUpdateDictData_success() {
        // mock 数据
        SysDictDataPO dbDictData = randomDictDataDO();
        dictDataMapper.insert(dbDictData);// @Sql: 先插入出一条存在的数据
        // 准备参数
        DictDataUpdateReqVO reqVO = randomPojo(DictDataUpdateReqVO.class, o -> {
            o.setId(dbDictData.getId()); // 设置更新的 ID
            o.setStatus(randomCommonStatus());
        });
        // mock 方法，字典类型
        when(dictTypeService.getDictTypeByType(eq(reqVO.getDictType()))).thenReturn(randomDictTypeDO(reqVO.getDictType()));

        // 调用
        dictDataService.updateDictData(reqVO);
        // 校验是否更新正确
        SysDictDataPO dictData = dictDataMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, dictData);
    }

    @Test
    public void testDeleteDictData_success() {
        // mock 数据
        SysDictDataPO dbDictData = randomDictDataDO();
        dictDataMapper.insert(dbDictData);// @Sql: 先插入出一条存在的数据
        // 准备参数
        String id = dbDictData.getId();

        // 调用
        dictDataService.deleteDictData(id);
        // 校验数据不存在了
        assertNull(dictDataMapper.selectById(id));
    }

    @Test
    public void testValidateDictDataExists_success() {
        // mock 数据
        SysDictDataPO dbDictData = randomDictDataDO();
        dictDataMapper.insert(dbDictData);// @Sql: 先插入出一条存在的数据

        // 调用成功
        dictDataService.validateDictDataExists(dbDictData.getId());
    }

    @Test
    public void testValidateDictDataExists_notExists() {
        assertServiceException(() -> dictDataService.validateDictDataExists(randomLongId().toString()), DICT_DATA_NOT_EXISTS);
    }

    @Test
    public void testValidateDictTypeExists_success() {
        // mock 方法，数据类型被禁用
        String type = randomString();
        when(dictTypeService.getDictTypeByType(eq(type))).thenReturn(randomDictTypeDO(type));

        // 调用, 成功
        dictDataService.validateDictTypeExists(type);
    }

    @Test
    public void testValidateDictTypeExists_notExists() {
        assertServiceException(() -> dictDataService.validateDictTypeExists(randomString()), DICT_TYPE_NOT_EXISTS);
    }

    @Test
    public void testValidateDictTypeExists_notEnable() {
        // mock 方法，数据类型被禁用
        String dictType = randomString();
        when(dictTypeService.getDictTypeByType(eq(dictType))).thenReturn(
                randomPojo(SysDictTypePO.class, o -> o.setStatus(CommonStatusEnum.DISABLE.getStatus())));

        // 调用, 并断言异常
        assertServiceException(() -> dictDataService.validateDictTypeExists(dictType), DICT_TYPE_NOT_ENABLE);
    }

    @Test
    public void testValidateDictDataValueUnique_success() {
        // 调用，成功
        dictDataService.validateDictDataValueUnique(randomLongId().toString(), randomString(), randomString());
    }

    @Test
    public void testValidateDictDataValueUnique_valueDuplicateForCreate() {
        // 准备参数
        String dictType = randomString();
        String value = randomString();
        // mock 数据
        dictDataMapper.insert(randomDictDataDO(o -> {
            o.setDictType(dictType);
            o.setValue(value);
        }));

        // 调用，校验异常
        assertServiceException(() -> dictDataService.validateDictDataValueUnique(null, dictType, value),
                DICT_DATA_VALUE_DUPLICATE);
    }

    @Test
    public void testValidateDictDataValueUnique_valueDuplicateForUpdate() {
        // 准备参数
        String id = randomLongId().toString();
        String dictType = randomString();
        String value = randomString();
        // mock 数据
        dictDataMapper.insert(randomDictDataDO(o -> {
            o.setDictType(dictType);
            o.setValue(value);
        }));

        // 调用，校验异常
        assertServiceException(() -> dictDataService.validateDictDataValueUnique(id, dictType, value),
                DICT_DATA_VALUE_DUPLICATE);
    }

    @Test
    public void testCountByDictType() {
        // mock 数据
        dictDataMapper.insert(randomDictDataDO(o -> o.setDictType("yunai")));
        dictDataMapper.insert(randomDictDataDO(o -> o.setDictType("tudou")));
        dictDataMapper.insert(randomDictDataDO(o -> o.setDictType("yunai")));
        // 准备参数
        String dictType = "yunai";

        // 调用
        long count = dictDataService.countByDictType(dictType);
        // 校验
        assertEquals(2L, count);
    }

    @Test
    public void testValidateDictDataList_success() {
        // mock 数据
        SysDictDataPO dictDataDO = randomDictDataDO();
        dictDataDO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        dictDataMapper.insert(dictDataDO);
        // 准备参数
        String dictType = dictDataDO.getDictType();
        List<String> values = singletonList(dictDataDO.getValue());

        // 调用，无需断言
        dictDataService.validateDictDataList(dictType, values);
    }

    @Test
    public void testValidateDictDataList_notFound() {
        // 准备参数
        String dictType = randomString();
        List<String> values = singletonList(randomString());

        // 调用, 并断言异常
        assertServiceException(() -> dictDataService.validateDictDataList(dictType, values), DICT_DATA_NOT_EXISTS);
    }

    @Test
    public void testValidateDictDataList_notEnable() {
        // mock 数据
        SysDictDataPO dictDataDO = randomDictDataDO();
        dictDataDO.setStatus(CommonStatusEnum.DISABLE.getStatus());
        dictDataMapper.insert(dictDataDO);
        // 准备参数
        String dictType = dictDataDO.getDictType();
        List<String> values = singletonList(dictDataDO.getValue());

        // 调用, 并断言异常
        assertServiceException(() -> dictDataService.validateDictDataList(dictType, values),
                DICT_DATA_NOT_ENABLE, dictDataDO.getLabel());
    }

    @Test
    public void testGetDictData_dictType() {
        // mock 数据
        SysDictDataPO dictDataDO1 = randomDictDataDO();
        dictDataDO1.setDictType("yunai");
        dictDataDO1.setValue("1");
        dictDataMapper.insert(dictDataDO1);
        SysDictDataPO dictDataDO2 = randomDictDataDO();
        dictDataDO2.setDictType("yunai");
        dictDataDO2.setValue("2");
        dictDataMapper.insert(dictDataDO2);
        // 准备参数
        String dictType = "yunai";
        String value = "1";

        // 调用
        SysDictDataPO dbDictData = dictDataService.getDictData(dictType, value);
        // 断言
        assertEquals(dictDataDO1, dbDictData);
    }

    @Test
    public void testParseDictData() {
        // mock 数据
        SysDictDataPO dictDataDO1 = randomDictDataDO();
        dictDataDO1.setDictType("yunai");
        dictDataDO1.setLabel("1");
        dictDataMapper.insert(dictDataDO1);
        SysDictDataPO dictDataDO2 = randomDictDataDO();
        dictDataDO2.setDictType("yunai");
        dictDataDO2.setLabel("2");
        dictDataMapper.insert(dictDataDO2);
        // 准备参数
        String dictType = "yunai";
        String label = "1";

        // 调用
        SysDictDataPO dbDictData = dictDataService.parseDictData(dictType, label);
        // 断言
        assertEquals(dictDataDO1, dbDictData);
    }

    // ========== 随机对象 ==========

    @SafeVarargs
    private static SysDictDataPO randomDictDataDO(Consumer<SysDictDataPO>... consumers) {
        Consumer<SysDictDataPO> consumer = (o) -> {
            o.setStatus(randomCommonStatus()); // 保证 status 的范围
        };
        return randomPojo(SysDictDataPO.class, ArrayUtils.append(consumer, consumers));
    }

    /**
     * 生成一个有效的字典类型
     *
     * @param type 字典类型
     * @return DictTypeDO 对象
     */
    private static SysDictTypePO randomDictTypeDO(String type) {
        return randomPojo(SysDictTypePO.class, o -> {
            o.setType(type);
            o.setStatus(CommonStatusEnum.ENABLE.getStatus()); // 保证 status 是开启
        });
    }

}
