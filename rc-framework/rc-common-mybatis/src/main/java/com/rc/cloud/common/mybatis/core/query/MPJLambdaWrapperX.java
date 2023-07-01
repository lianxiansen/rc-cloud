package com.rc.cloud.common.mybatis.core.query;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.rc.cloud.common.core.util.collection.ArrayUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * @author WJF
 * @create 2023-07-01 11:22
 * @description
 * 1. 增加联表查询的功能，增加 xxxIfPresent 方法，用于判断值不存在的时候，不要拼接到条件中。
 */

public class MPJLambdaWrapperX<T> extends MPJLambdaWrapper<T> {

    public <R> MPJLambdaWrapperX<T> likeIfPresent(SFunction<R, ?> column, String val) {
        if (StringUtils.hasText(val)) {
            return (MPJLambdaWrapperX<T>) super.like(column, val);
        }
        return this;
    }

    public <R> MPJLambdaWrapperX<T> inIfPresent(SFunction<R, ?> column, Collection<?> values) {
        if (!CollectionUtils.isEmpty(values)) {
            return (MPJLambdaWrapperX<T>) super.in(column, values);
        }
        return this;
    }

    public <R> MPJLambdaWrapperX<T> inIfPresent(SFunction<R, ?> column, Object... values) {
        if (!ArrayUtil.isEmpty(values)) {
            return (MPJLambdaWrapperX<T>) super.in(column, values);
        }
        return this;
    }

    public <R> MPJLambdaWrapperX<T> eqIfPresent(SFunction<R, ?> column, Object val) {
        if (ObjectUtil.isNotEmpty(val)) {
            return (MPJLambdaWrapperX<T>) super.eq(column, val);
        }
        return this;
    }

    public <R> MPJLambdaWrapperX<T> neIfPresent(SFunction<R, ?> column, Object val) {
        if (ObjectUtil.isNotEmpty(val)) {
            return (MPJLambdaWrapperX<T>) super.ne(column, val);
        }
        return this;
    }

    public <R> MPJLambdaWrapperX<T> gtIfPresent(SFunction<R, ?> column, Object val) {
        if (val != null) {
            return (MPJLambdaWrapperX<T>) super.gt(column, val);
        }
        return this;
    }

    public <R> MPJLambdaWrapperX<T> geIfPresent(SFunction<R, ?> column, Object val) {
        if (val != null) {
            return (MPJLambdaWrapperX<T>) super.ge(column, val);
        }
        return this;
    }

    public <R> MPJLambdaWrapperX<T> ltIfPresent(SFunction<R, ?> column, Object val) {
        if (val != null) {
            return (MPJLambdaWrapperX<T>) super.lt(column, val);
        }
        return this;
    }

    public <R> MPJLambdaWrapperX<T> leIfPresent(SFunction<R, ?> column, Object val) {
        if (val != null) {
            return (MPJLambdaWrapperX<T>) super.le(column, val);
        }
        return this;
    }

    public <R> MPJLambdaWrapperX<T> betweenIfPresent(SFunction<R, ?> column, Object val1, Object val2) {
        if (val1 != null && val2 != null) {
            return (MPJLambdaWrapperX<T>) super.between(column, val1, val2);
        }
        if (val1 != null) {
            return (MPJLambdaWrapperX<T>) ge(column, val1);
        }
        if (val2 != null) {
            return (MPJLambdaWrapperX<T>) le(column, val2);
        }
        return this;
    }

    public <R> MPJLambdaWrapperX<T> betweenIfPresent(SFunction<R, ?> column, Object[] values) {
        Object val1 = ArrayUtils.get(values, 0);
        Object val2 = ArrayUtils.get(values, 1);
        return betweenIfPresent(column, val1, val2);
    }

    // ========== 重写父类方法，方便链式调用 ==========

    @Override
    public <R> MPJLambdaWrapperX<T> eq(boolean condition, SFunction<R, ?> column, Object val) {
        super.eq(condition, column, val);
        return this;
    }

    @Override
    public <R> MPJLambdaWrapperX<T> eq(SFunction<R, ?> column, Object val) {
        super.eq(column, val);
        return this;
    }

    @Override
    public <R> MPJLambdaWrapperX<T> orderByDesc(SFunction<R, ?> column) {
        super.orderByDesc(true, column);
        return this;
    }

    @Override
    public MPJLambdaWrapperX<T> last(String lastSql) {
        super.last(lastSql);
        return this;
    }

    @Override
    public <X> MPJLambdaWrapperX<T> in(boolean condition, SFunction<X, ?> column, Collection<?> coll){
        super.in(condition, column, coll);
        return this;
    }

}
