package com.rc.cloud.app.mall.infrastructure.persistence.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.sql.Timestamp;

/**
 * @Author:chenjianxiang
 * @Date 2021/2/8
 * @Description:
 */
public class BaseEntity<T extends Model<T>> extends Model<T> {

    public BaseEntity(){
        this.createTime=new Timestamp(System.currentTimeMillis());
    }
    /**
     * 主键ID
     */
    @TableId(value = "ID")
    @JSONField(ordinal = 1, name = "ID")
    private Integer id;

    @TableField(value = "CreateTime")
    @JSONField(ordinal = 1 ,name = "CreateTime")
    private Timestamp createTime;

    public Integer getId() {
        if(id==null){
            return 0;
        }
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
