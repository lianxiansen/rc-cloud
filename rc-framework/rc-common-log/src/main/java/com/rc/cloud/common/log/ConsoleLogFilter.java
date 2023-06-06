package com.rc.cloud.common.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.filter.AbstractMatcherFilter;
import ch.qos.logback.core.spi.FilterReply;
import cn.hutool.extra.spring.SpringUtil;
import org.springframework.context.ApplicationContext;

/**
 * @author WJF
 * @create 2023-02-20 15:53
 * @description TODO
 */

public class ConsoleLogFilter extends AbstractMatcherFilter<ILoggingEvent> {
    Level level;

    @Override
    public FilterReply decide(ILoggingEvent iLoggingEvent) {
        if (iLoggingEvent instanceof LoggingEvent) {
            LoggingEvent loggingEvent = (LoggingEvent) iLoggingEvent;
            ApplicationContext applicationContext = SpringUtil.getApplicationContext();
            if (applicationContext != null) {
//                ResourceMessageUtil resourceMessageUtil = SpringUtil.getBean("resourceMessageUtil");
//                String msg = resourceMessageUtil.getLocal(loggingEvent.getFormattedMessage(), null, loggingEvent.getFormattedMessage());
//                ReflectUtil.setFieldValue(loggingEvent, "message", msg);
//                ReflectUtil.setFieldValue(loggingEvent, "formattedMessage", msg);
            }
        }
        return isDiscardable(iLoggingEvent) ? onMatch : onMismatch;
    }

    protected boolean isDiscardable(ILoggingEvent event) {
        Level curLevel = event.getLevel();
        return curLevel.toInt() >= level.toInt();
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void start() {
        if (this.level != null) {
            super.start();
        }
    }
}
