package com.rc.cloud.app.operate.infrastructure.repository.persistence.convert;

import com.rc.cloud.app.operate.domain.common.valobj.CreateTime;
import com.rc.cloud.app.operate.domain.common.valobj.Enabled;
import com.rc.cloud.app.operate.domain.common.valobj.Sort;
import com.rc.cloud.app.operate.domain.model.customclassification.CustomClassification;
import com.rc.cloud.app.operate.domain.model.customclassification.CustomClassificationRebuildFactory;
import com.rc.cloud.app.operate.domain.model.customclassification.identifier.CustomClassificationId;
import com.rc.cloud.app.operate.domain.model.customclassification.valobj.Layer;
import com.rc.cloud.app.operate.domain.model.customclassification.valobj.Url;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.CustomClassificationPO;
import com.rc.cloud.common.core.annotation.Convert;
import com.rc.cloud.common.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * @ClassName: CustomClassificationConvert
 * @Author: liandy
 * @Date: 2023/6/23 14:16
 * @Description: TODO
 */
@Convert
public class CustomClassificationConvert {
    @Autowired
    private CustomClassificationRebuildFactory customClassificationRebuildFactory;

    public CustomClassificationPO convertPO(CustomClassification source) {
        if (Objects.isNull(source)) {
            return null;
        }
        CustomClassificationPO target = new CustomClassificationPO();
        target.setId(source.getId().id());
        target.setCustomClassificationImage(source.getCustomClassificationImage().getValue());
        target.setCustomClassificationPoster(source.getCustomClassificationPoster().getValue());
        target.setProductPoster(source.getProductPoster().getValue());
        target.setLayer(source.getLayer().getValue());
        target.setEnabledFlag(source.getEnabledFlag());
        target.setName(source.getName());
        target.setParentId(source.getParentId() == null ? "" : source.getParentId().id());
        target.setSort(source.getSort().getValue());
        return target;
    }

    public CustomClassification convertDomain(CustomClassificationPO po) {
        if (Objects.isNull(po)) {
            return null;
        }
        CustomClassificationId id = new CustomClassificationId(po.getId());
        CreateTime createTime = new CreateTime(po.getCreateTime());
        CustomClassificationRebuildFactory.CustomClassificationRebuilder rebuilder
                = customClassificationRebuildFactory.create(id, po.getName(), createTime);

        rebuilder.productPoster(new Url(po.getProductPoster()));
        rebuilder.customClassificationImage(new Url(po.getCustomClassificationImage()));
        rebuilder.customClassificationPoster(new Url(po.getCustomClassificationPoster()));

        if (Objects.nonNull(po.getEnabledFlag())) {
            rebuilder.setEnabled(new Enabled(po.getEnabledFlag()));
        }
        rebuilder.sort(new Sort(po.getSort()));
        rebuilder.layer(new Layer(po.getLayer()));
        if (StringUtils.isNotEmpty(po.getParentId())) {
            rebuilder.parentId(new CustomClassificationId(po.getParentId()));
        }
        return rebuilder.rebuild();
    }

}
