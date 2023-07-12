package com.rc.cloud.common.core.domain;

/**
 * @ClassName: AggregateRoot
 * @Author: liandy
 * @Date: 2023/7/12 12:23
 */
public abstract class AggregateRoot extends Entity {

    private int concurrencyVersion;
    /**
     * Aggregate compare by identity, not by attributes.
     */
    public AggregateRoot() {
        super();
    }

}
