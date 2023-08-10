package com.rc.cloud.app.operate.domain.model.customclassification;

import com.rc.cloud.app.operate.domain.common.valobj.CreateTime;
import com.rc.cloud.app.operate.domain.common.valobj.Enabled;
import com.rc.cloud.app.operate.domain.common.valobj.Sort;
import com.rc.cloud.app.operate.domain.model.customclassification.identifier.CustomClassificationId;
import com.rc.cloud.app.operate.domain.model.customclassification.valobj.Layer;
import com.rc.cloud.app.operate.domain.model.customclassification.valobj.Url;
import com.rc.cloud.common.core.annotation.Factory;


@Factory
public class CustomClassificationRebuildFactory {
    public CustomClassificationRebuilder create(CustomClassificationId id, String name, CreateTime createTime) {
        return new CustomClassificationRebuilder(id, name, createTime);
    }

    public CustomClassificationRebuilder create(CustomClassification CustomClassification) {
        return new CustomClassificationRebuilder(CustomClassification);
    }

    public class CustomClassificationRebuilder {
        private CustomClassification current;

        public CustomClassificationRebuilder(CustomClassification customClassification) {
            this.current = customClassification;
        }

        public CustomClassificationRebuilder(CustomClassificationId id, String name, CreateTime createTime) {
            current = new CustomClassification(id, name);
            current.setCreateTime(createTime);
        }

        public CustomClassificationRebuilder name(String name) {
            current.setName(name);
            return this;
        }


        public CustomClassificationRebuilder sort(Sort sort) {
            current.setSort(sort);
            return this;
        }

        public CustomClassificationRebuilder layer(Layer layer) {
            current.setLayer(layer);
            return this;
        }

        public CustomClassificationRebuilder parentId(CustomClassificationId parentId) {
            current.setParentId(parentId);
            return this;
        }

        public CustomClassificationRebuilder customClassificationImage(Url customClassificationImage) {
            current.setCustomClassificationImage(customClassificationImage);
            return this;
        }

        public CustomClassificationRebuilder customClassificationPoster(Url customClassificationPoster) {
            current.setCustomClassificationPoster(customClassificationPoster);
            return this;
        }
        public CustomClassificationRebuilder productPoster(Url productPoster) {
            current.setProductPoster(productPoster);
            return this;
        }



        public CustomClassificationRebuilder setEnabled(Enabled enabled) {
            if (enabled.isTrue()) {
                current.enable();
            } else {
                current.disable();
            }
            return this;
        }


        public CustomClassification rebuild() {
            return current;
        }


    }


}
