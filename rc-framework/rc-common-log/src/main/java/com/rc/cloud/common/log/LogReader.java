package com.rc.cloud.common.log;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

/**
 * @author WJF
 * @create 2022-08-06 14:27
 * @description TODO
 */
@Slf4j
public class LogReader {
    private static final Integer MAX_LINES = 200;

    public String read(String filePath, SessionData sessionData) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            Object[] lines = br.lines().toArray();
            Integer pageSize = lines.length/MAX_LINES;
            //只取从上次之后产生的日志
            Object[] copyOfRange;
            if (sessionData.isNew() && lines.length > MAX_LINES) {

                sessionData.setStartLine(lines.length - MAX_LINES);
                sessionData.setNew(false);
            }
            copyOfRange = Arrays.copyOfRange(lines, sessionData.getStartLine(), lines.length);

            //对日志进行着色，更加美观  PS：注意，这里要根据日志生成规则来操作
            for (int i = 0; i < copyOfRange.length; i++) {
                String line = (String) copyOfRange[i];
                //先转义
                line = line.replaceAll("&", "&amp;")
                        .replaceAll("<", "&lt;")
                        .replaceAll(">", "&gt;")
                        .replaceAll("\"", "&quot;");

                //处理等级
                line = line.replace("DEBUG", "<span style='color: blue;font-weight:bold;'>DEBUG</span>");
                line = line.replace("INFO", "<span style='color: green;font-weight:bold;'>INFO</span>");
                line = line.replace("WARN", "<span style='color: orange;font-weight:bold;'>WARN</span>");
                line = line.replace("ERROR", "<span style='color: red;font-weight:bold;'>ERROR</span>");
                line = line.replace("requestId", "<span style='color: orange;'>requestId</span>");
                copyOfRange[i] = line;
            }

            //存储最新一行开始
            sessionData.setStartLine(sessionData.getStartLine() + copyOfRange.length);
            return StringUtils.join(copyOfRange, "<br/>");
        } catch (Exception e) {
            log.error("日志文件读取发生错误", e);
        }
        return "";
    }
}
