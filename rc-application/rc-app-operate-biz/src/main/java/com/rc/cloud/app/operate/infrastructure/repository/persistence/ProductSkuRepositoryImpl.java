package com.rc.cloud.app.operate.infrastructure.repository.persistence;

import cn.hutool.core.collection.CollectionUtil;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductAttribute;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSku;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuAttribute;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuImage;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuRepository;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuId;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.convert.ProductAttributeConvert;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.convert.ProductSkuAttributeConvert;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.convert.ProductSkuConvert;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.convert.ProductSkuImageConvert;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.mapper.ProductSkuAttributeMapper;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.mapper.ProductSkuImageMapper;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.mapper.ProductSkuMapper;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.*;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ProductSkuRepositoryImpl implements ProductSkuRepository{

    @Autowired
    ProductSkuMapper productSkuMapper;

    @Autowired
    ProductSkuImageMapper productSkuImageMapper;

    @Autowired
    ProductSkuAttributeMapper productSkuAttributeMapper;


    private int removeProductSkuImageByProductSkuId(ProductSkuId productSkuId) {
        LambdaQueryWrapperX<ProductSkuImagePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductSkuImagePO::getProductSkuId, productSkuId.id());
        return this.productSkuImageMapper.delete(wrapper);
    }


    private int updateProductSkuImageByProductSku(ProductSku productSku) {
        List<ProductSkuImage> newList = new ArrayList<>();
        if(productSku.getSkuImageList()!=null){
            newList.addAll(productSku.getSkuImageList());
        }

        List<ProductSkuImage> oriList = getProductSkuImageByProductSkuId(productSku.getId());
        List<ProductSkuImage> addList = CollectionUtil.subtractToList(newList, oriList);
        List<ProductSkuImage> removeList = CollectionUtil.subtractToList(oriList, newList);
        removeList.forEach(x ->
                removeProductSkuImageByUrlAndSort(x.getUrl().getValue(), x.getSort().getValue())
        );
        batchInsertProductSkuImage(addList, productSku.getId());
        return 1;
    }


    private int removeProductSkuImageByUrlAndSort(String url, int sort) {
        LambdaQueryWrapperX<ProductSkuImagePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductSkuImagePO::getUrl, url);
        wrapper.eq(ProductSkuImagePO::getSort, sort);
        return productSkuImageMapper.delete(wrapper);
    }



    private int batchInsertProductSkuImage(List<ProductSkuImage> productSkuImageList, ProductSkuId productSkuId) {

        if(productSkuImageList!=null && productSkuImageList.size()>0){
            for (ProductSkuImage productSkuImage : productSkuImageList) {
                ProductSkuImagePO po = ProductSkuImageConvert.convertProductSkuImagePO(
                        productSkuImage);
                this.productSkuImageMapper.insert(po);
            }
        }
        return 1;
    }

    private int removeProductSkuAttributeByProductSkuId(ProductSkuId productSkuId) {
        LambdaQueryWrapperX<ProductSkuAttributePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductSkuAttributePO::getProductSkuId, productSkuId.id());
        return this.productSkuAttributeMapper.delete(wrapper);
    }


    private int insertProductSkuAttribute(ProductSku productSku) {
        ProductSkuAttributePO productSkuAttributePO = ProductSkuAttributeConvert.convertPO(productSku.getProductSkuAttribute());
        return this.productSkuAttributeMapper.insert(productSkuAttributePO);
    }


    private int updateProductSkuAttribute(ProductSku productSku) {
        ProductSkuAttribute productSkuAttribute = productSku.getProductSkuAttribute();
        ProductSkuAttributePO po = ProductSkuAttributeConvert.convertPO(productSkuAttribute);
        po.setProductSkuId(productSku.getId().id());
        LambdaQueryWrapperX<ProductSkuAttributePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductSkuAttributePO::getProductSkuId, productSku.getId().id());
        return this.productSkuAttributeMapper.update(po, wrapper);
    }


    @Override
    public boolean exist(ProductSkuId productSkuId){
        LambdaQueryWrapperX<ProductSkuPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductSkuPO::getId, productSkuId.id());
        return this.productSkuMapper.exists(wrapper);
    }

    @Override
    public ProductSku findById(ProductSkuId productSkuId) {
        LambdaQueryWrapperX<ProductSkuPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductSkuPO::getId, productSkuId.id());
        ProductSkuPO ProductSkuPO = this.productSkuMapper.selectOne(wrapper);
        ProductSku productSku = ProductSkuConvert.convertDomain(ProductSkuPO);
        productSku.setSkuImageList(getProductSkuImageByProductSkuId(productSkuId));
        productSku.setProductSkuAttribute(getProductSkuAttributeByProductSkuId(productSkuId));
        return productSku;
    }


    /**
     * 批量保持SKULIST
     * 包含新增和修改
     * @param productSkuList
     * @return
     */
    @Override
    public int batchSaveProductSku(List<ProductSku> productSkuList) {
        //保存ProductSku
        //这里的规格有可能是原有的基础上新增
        //比如：1 2 3 新增 4 5 6 结果是 1 2 3 4 5 6
        //也有可能是减少 1 2 3 4 减少 3 4 结果是 1 2
        //也可能是重新洗牌 1 2 3 4 结果是 5 6 7 8
        ProductId productId=null;
        Set<String> oldList=new HashSet<>();
        Set<String> newList =new HashSet<>();
        if(productSkuList!=null && productSkuList.size()>0){
            productId=productSkuList.get(0).getProductId();
        }
        List<ProductSku> oriList = getProductSkuListByProductId(productId);
        if(oriList!=null && oriList.size()>0){
            oldList =oriList.stream().map(x->x.getId().id()).distinct().collect(Collectors.toSet());
        }
        newList = productSkuList.stream().map(x->x.getId().id()).distinct().collect(Collectors.toSet());
        //添加
        List<String> addList = CollectionUtil.subtractToList(newList, oldList);
        for (String x : addList) {
            insertProductSku(productSkuList.stream().filter(u->
                    u.getId().id().equals(x)
            ).findFirst().get());

        }
        //移除
        List<String> reduceList = CollectionUtil.subtractToList(oldList, newList);
        for (String x : reduceList) {
            removeProductSku(new ProductSkuId(x));
        }
        //更新
        Collection<String> intersection = CollectionUtil.intersection(newList, oldList);
        for (String x : intersection) {
            updateProductSku(productSkuList.stream().filter(u->
                    u.getId().id().equals(x)
            ).findFirst().get());
        }
        return 1;
    }

    /**
     * 修改单个SKU
     * 同时会更新SKU图片以及SKU属性
     * @param productSku
     * @return
     */
    @Override
    public int updateProductSku(ProductSku productSku) {
        LambdaQueryWrapperX<ProductSkuPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductSkuPO::getId, productSku.getId().id());
        ProductSkuPO po = ProductSkuConvert.convertProductSkuPO(productSku);
        this.productSkuMapper.update(po,wrapper);
        updateProductSkuImageByProductSku(productSku);
        updateProductSkuAttribute(productSku);
        return 1;
    }

    /**
     * 新增SKU
     * 同时插入图片
     * 同时插入SKU属性
     * @param productSku
     * @return
     */
    @Override
    public int insertProductSku(ProductSku productSku) {
        ProductSkuPO po = ProductSkuConvert.convertProductSkuPO(productSku);
        this.productSkuMapper.insert(po);
        batchInsertProductSkuImage(productSku.getSkuImageList(),productSku.getId());
        insertProductSkuAttribute(productSku);
        return 1;
    }




    @Override
    public List<ProductSku> getProductSkuListByProductId(ProductId productId) {
        LambdaQueryWrapperX<ProductSkuPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductSkuPO::getProductId, productId.id());
        List<ProductSkuPO> productSkuPOList = this.productSkuMapper.selectList(wrapper);
        List<ProductSku> resList = ProductSkuConvert.convertDomainList(productSkuPOList);
        for (ProductSku productSku : resList) {
            productSku.setSkuImageList(getProductSkuImageByProductSkuId(productSku.getId()));
            productSku.setProductSkuAttribute(getProductSkuAttributeByProductSkuId(productSku.getId()));
        }
        return resList;
    }


    private List<ProductSkuImage> getProductSkuImageByProductSkuId(ProductSkuId productSkuId) {
        LambdaQueryWrapperX wrapperX=new LambdaQueryWrapperX<ProductSkuImagePO>();
        LambdaQueryWrapperX<ProductSkuImagePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductSkuImagePO::getProductSkuId, productSkuId.id());
        List<ProductSkuImagePO> productSkuImagePOS = this.productSkuImageMapper.selectList(wrapper);
        return ProductSkuImageConvert.convertDomainList(productSkuImagePOS);
    }

    private ProductSkuAttribute getProductSkuAttributeByProductSkuId(ProductSkuId productSkuId) {

        LambdaQueryWrapperX wrapperX=new LambdaQueryWrapperX<ProductSkuAttributePO>();
        LambdaQueryWrapperX<ProductSkuAttributePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductSkuAttributePO::getProductSkuId, productSkuId.id());

        return ProductSkuAttributeConvert.convertDomain(this.productSkuAttributeMapper.selectOne(wrapper));

    }

    @Override
    public void removeProductSku(ProductSkuId productSkuId) {
        LambdaQueryWrapperX<ProductSkuPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductSkuPO::getId, productSkuId.id());
        this.productSkuMapper.delete(wrapper);
        removeProductSkuImageByProductSkuId(productSkuId);
        removeProductSkuAttributeByProductSkuId(productSkuId);
    }


}
