package com.rc.cloud.app.operate.infrastructure.repository.persistence;

import cn.hutool.core.collection.CollectionUtil;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSku;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuAttribute;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuImage;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuRepository;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.convert.ProductSkuAttributeConvert;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.convert.ProductSkuConvert;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.convert.ProductSkuImageConvert;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.mapper.ProductSkuAttributeMapper;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.mapper.ProductSkuImageMapper;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.mapper.ProductSkuMapper;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductSkuAttributePO;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductSkuImagePO;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductSkuPO;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    /**
     * 批量更新规格图片
     * 怎么样的图片算相同呢？
     * 至少需要skuid相同
     * skuid相同的图片有多张，这个又是值类型
     * 自身id是没有用的，我们需要skuid相同，图片地址相同，排序相同这样才算同一张图片(这里不同于商品图片，商品图片定义为实体，有实际id)
     * @param productSkuId
     * @param productSkuImageList
     * @return
     */
    private void updateProductSkuImageList(ProductSkuId productSkuId, List<ProductSkuImage> productSkuImageList) {

        List<ProductSkuImage> oriDicts = getProductSkuImageByProductSkuId(productSkuId);

        List<ProductSkuImage> addList = CollectionUtil.subtractToList(productSkuImageList, oriDicts);
        List<ProductSkuImage> removeList = CollectionUtil.subtractToList(oriDicts, productSkuImageList);
        //这里不存在交集，只有需要删除的与需要增加的
//        List<ProductSkuImage> intersectionList =
//                CollectionUtil.intersection(oriDicts,productSkuImageList).stream().collect(Collectors.toList());

        if(addList!=null && addList.size()>0){
            batchInsertProductSkuImage(addList);
        }
        if(removeList!=null && removeList.size()>0){
            for (ProductSkuImage productSkuImage : removeList) {
                removeProductSkuImageByProductSkuIdAndUrlAndSort(productSkuId
                        ,productSkuImage.getUrl().getValue()
                        ,productSkuImage.getSort().getValue());
            }
        }
    }

    /**
     * 移除单张图片，一定需要指定skuid和url和sort
     * @param productSkuId
     * @param url
     * @param sort
     * @return
     */
    private int removeProductSkuImageByProductSkuIdAndUrlAndSort(ProductSkuId productSkuId, String url, int sort) {
        LambdaQueryWrapperX<ProductSkuImagePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductSkuImagePO::getProductSkuId, productSkuId.id());
        wrapper.eq(ProductSkuImagePO::getUrl, url);
        wrapper.eq(ProductSkuImagePO::getSort, sort);
        return productSkuImageMapper.delete(wrapper);
    }

    private void batchInsertProductSkuImage(List<ProductSkuImage> productSkuImageList) {

        if(productSkuImageList!=null && productSkuImageList.size()>0){
            for (ProductSkuImage productSkuImage : productSkuImageList) {
                ProductSkuImagePO po = ProductSkuImageConvert.convertProductSkuImagePO(
                        productSkuImage);
                this.productSkuImageMapper.insert(po);
            }
        }
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


    private ProductSkuAttributePO findProductSkuAttributeByProductSkuId(ProductSkuId productSkuId) {
        LambdaQueryWrapperX<ProductSkuAttributePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductSkuAttributePO::getProductSkuId, productSkuId.getId());
        return this.productSkuAttributeMapper.selectOne(wrapper);
    }

    /**
     * 修改ProductSkuAttribute的content
     * @param productSku
     * @return
     */
    private int updateProductSkuAttribute(ProductSku productSku) {
        //新的attribute
        ProductSkuAttributePO newAttribute = ProductSkuAttributeConvert.convertPO(productSku.getProductSkuAttribute());
        ProductSkuAttributePO oriAttribute = findProductSkuAttributeByProductSkuId(productSku.getId());
        //设置修改内容
        oriAttribute.setContent(newAttribute.getContent());
        LambdaQueryWrapperX<ProductSkuAttributePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductSkuAttributePO::getId, oriAttribute.getId());
        return this.productSkuAttributeMapper.update(oriAttribute, wrapper);
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
        ProductSkuPO po = this.productSkuMapper.selectOne(wrapper);
        ProductSku productSku = ProductSkuConvert.convertDomain(po);
        if(productSku!=null){
            productSku.setSkuImageList(getProductSkuImageByProductSkuId(productSkuId));
            productSku.setProductSkuAttribute(getProductSkuAttributeByProductSkuId(productSkuId));
        }
        return productSku;
    }


    /**
     * 批量保持SKULIST
     * 包含新增和修改
     * @param productSkuList
     * @return
     */
    @Override
    public void batchSaveProductSku(ProductId productId,List<ProductSku> productSkuList) {
        //保存ProductSku
        //这里的规格有可能是原有的基础上新增
        //比如：1 2 3 新增 4 5 6 结果是 1 2 3 4 5 6
        //也有可能是减少 1 2 3 4 减少 3 4 结果是 1 2
        //也可能是重新洗牌 1 2 3 4 结果是 5 6 7 8
        List<ProductSku> oriList = getProductSkuListByProductId(productId);
        List<ProductSku> addList = CollectionUtil.subtractToList(productSkuList, oriList);
        List<ProductSku> removeList = CollectionUtil.subtractToList(oriList, productSkuList);
        List<ProductSku> intersectionList =
                CollectionUtil.intersection(oriList,productSkuList).stream().collect(Collectors.toList());

        for (ProductSku add : addList) {
           insertProductSku(add);
        }
        for (ProductSku remove : removeList) {
            removeProductSku(remove);
        }
        //更新
        for (ProductSku productSku : intersectionList) {
            updateProductSku(productSku);
        }
    }

    /**
     * 修改单个SKU
     * 同时会更新SKU图片以及SKU属性
     * @param productSku
     * @return
     */
    @Override
    public void updateProductSku(ProductSku productSku) {
        LambdaQueryWrapperX<ProductSkuPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductSkuPO::getId, productSku.getId().id());
        ProductSkuPO po = ProductSkuConvert.convertProductSkuPO(productSku);
        this.productSkuMapper.update(po,wrapper);
        updateProductSkuImageList(productSku.getId(),productSku.getSkuImageList());
        updateProductSkuAttribute(productSku);

    }

    /**
     * 新增SKU
     * 同时插入图片
     * 同时插入SKU属性
     * @param productSku
     * @return
     */
    @Override
    public void insertProductSku(ProductSku productSku) {
        ProductSkuPO po = ProductSkuConvert.convertProductSkuPO(productSku);
        this.productSkuMapper.insert(po);
        batchInsertProductSkuImage(productSku.getSkuImageList());
        insertProductSkuAttribute(productSku);
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

    private void removeProductSku(ProductSku productSku) {
        removeProductSku(productSku.getId());
    }


}
