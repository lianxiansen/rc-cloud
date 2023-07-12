
package com.rc.cloud.app.operate.domain.model.productgroup.valobj;

import com.rc.cloud.common.core.domain.ValueObject;
import com.rc.cloud.common.core.util.AssertUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @ClassName: Sort
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class CreateTime implements ValueObject<CreateTime>,Comparable<CreateTime> {
    private LocalDateTime time;
    public CreateTime(LocalDateTime time){
        setTime(time);
    }
    private void setTime(LocalDateTime time){
        AssertUtils.assertArgumentNotNull(time, "time must not be null");
        this.time=time;
    }

    public String format(){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.format(fmt);
    }

    public LocalDateTime getTime(){
        return this.time;
    }



    @Override
    public int compareTo(CreateTime o) {
        return getTime().compareTo(o.getTime());
    }

    @Override
    public boolean sameValueAs(CreateTime other) {
        return false;
    }
}
