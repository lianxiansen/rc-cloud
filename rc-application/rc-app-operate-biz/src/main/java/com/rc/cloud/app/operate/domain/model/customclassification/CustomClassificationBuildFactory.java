package com.rc.cloud.app.operate.domain.model.customclassification;

import com.rc.cloud.app.operate.domain.common.valobj.CreateTime;
import com.rc.cloud.app.operate.domain.common.valobj.Enabled;
import com.rc.cloud.app.operate.domain.common.valobj.Sort;
import com.rc.cloud.app.operate.domain.model.customclassification.identifier.CustomClassificationId;
import com.rc.cloud.app.operate.domain.model.customclassification.valobj.Layer;
import com.rc.cloud.app.operate.domain.model.customclassification.valobj.Url;
import com.rc.cloud.common.core.annotation.Factory;

import java.time.LocalDateTime;

/**
 * @ClassName: CustomClassificationFactory
 * @Author: liandy
 * @Date: 2023/7/12 17:06
 * @Description: 产品分类建造工厂
 */
@Factory
public class CustomClassificationBuildFactory {
    public CustomClassificationBuilder create(CustomClassificationId id, String name) {
        return new CustomClassificationBuilder(id, name);
    }

    public class CustomClassificationBuilder {
        private CustomClassification current;

        public CustomClassificationBuilder(CustomClassificationId id, String name) {
            current = new CustomClassification(id, name);
        }

        public CustomClassificationBuilder name(String name) {
            current.setName(name);
            return this;
        }


        public CustomClassificationBuilder sort(Sort sort) {
            current.setSort(sort);
            return this;
        }

        public CustomClassificationBuilder layer(Layer layer) {
            current.setLayer(layer);
            return this;
        }

        public CustomClassificationBuilder parentId(CustomClassificationId parentId) {
            current.setParentId(parentId);
            return this;
        }

        public CustomClassificationBuilder customClassificationImage(Url customClassificationImage) {
            current.setCustomClassificationImage(customClassificationImage);
            return this;
        }

        public CustomClassificationBuilder customClassificationPoster(Url customClassificationPoster) {
            current.setCustomClassificationPoster(customClassificationPoster);
            return this;
        }
        public CustomClassificationBuilder productPoster(Url productPoster) {
            current.setProductPoster(productPoster);
            return this;
        }

        public CustomClassificationBuilder setEnabled(Enabled enabled) {
            if (enabled.isTrue()) {
                current.enable();
            } else {
                current.disable();
            }
            return this;
        }

        public CustomClassification build() {
            current.setCreateTime(new CreateTime(LocalDateTime.now()));
            return current;
        }
    }


}
