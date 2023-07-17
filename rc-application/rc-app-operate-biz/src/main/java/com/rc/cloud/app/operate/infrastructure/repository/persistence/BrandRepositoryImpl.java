package com.rc.cloud.app.operate.infrastructure.repository.persistence;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.rc.cloud.app.operate.domain.model.brand.Brand;
import com.rc.cloud.app.operate.domain.model.brand.BrandRepository;
import com.rc.cloud.app.operate.domain.model.brand.identifier.BrandId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.convert.BrandConvert;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.mapper.BrandMapper;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.BrandPO;
import com.rc.cloud.common.core.pojo.PageParam;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Objects;


@Service
public class BrandRepositoryImpl implements BrandRepository {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public boolean save(Brand brand) {
        BrandPO brandPO = BrandConvert.convert2BrandPO(brand);
        String idVal = brand.getId().id();
        return !StringUtils.checkValNull(idVal) && !Objects.isNull(brandMapper.selectById((Serializable) idVal)) ? brandMapper.updateById(brandPO) > 0 : brandMapper.insert(brandPO) > 0;
    }

    @Override
    public Brand findById(BrandId brandId) {
        BrandPO brandPO=brandMapper.selectById((Serializable)brandId.id());
        if(Objects.isNull(brandPO)){
            return null;
        }
        return BrandConvert.convert2Brand(brandPO);
    }


    @Override
    public boolean removeById(BrandId brandId) {
        return brandMapper.deleteById((Serializable)brandId.id()) > 0;
    }

    @Override
    public PageResult<Brand> selectPageResult(Integer pageNo, Integer pageSize, String name) {
        BrandPageParam param = new BrandPageParam();
        param.setPageNo(pageNo);
        param.setPageSize(pageSize);
        param.setName(name);
        PageResult<BrandPO> brandDOPageResult=brandMapper.selectPage(param, new LambdaQueryWrapperX<BrandPO>()
                .likeIfPresent(BrandPO::getName, param.getName())
                .orderByDesc(BrandPO::getId));
        return BrandConvert.convert2BrandPageResult(brandDOPageResult);
    }



}
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
class BrandPageParam extends PageParam {
    private String name;
}


