
package com.rc.cloud.app.operate.domain.common.valobj;

import com.rc.cloud.common.core.domain.ValueObject;
import com.rc.cloud.common.core.util.AssertUtils;

import java.time.LocalDateTime;

/**
 * @ClassName: CreateTime
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class CreateTime extends ValueObject implements Comparable<CreateTime> {
    private LocalDateTime time;
    public CreateTime(LocalDateTime time){
        setTime(time);
    }
    private void setTime(LocalDateTime time){
        AssertUtils.assertArgumentNotNull(time, "time must not be null");
        this.time=time;
    }


    public LocalDateTime getTime(){
        return this.time;
    }


    @Override
    public int compareTo(CreateTime o) {
        return getTime().compareTo(o.getTime());
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && this.getClass() == other.getClass()) {
            CreateTime typedObject = (CreateTime) other;
            this.time.equals(typedObject.time);
        }
        return false;
    }
}
