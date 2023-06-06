package com.rc.cloud.common.log;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URL;

/**
 * @author WJF
 * @create 2022-08-06 14:27
 * @description TODO
 */
//@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("log")
public class LogController {
    @Value("${spring.application.name}")
    private String appName;
    @Value("${spring.profiles.active:dev}")
    private String active;
    @Value("${spring.profiles.include:dev}")
    private String include;
    @Value("${server.port}")
    private String port;
    @Value("${spring.cloud.client.ip-address:i}")
    private String ip;
    @Value("${sup.gateway-route-config.uri:}")
    private String uri;
    private final FileHandler fileHandler;

    @GetMapping
    public String push(Model model,
                       @RequestParam(value = "service", required = false) String service
        , @RequestParam(value = "port", required = false) String port) {
        // 优先通过网关配置处理
        if (StrUtil.isNotBlank(uri)) {
            String normalizeUrl = URLUtil.normalize(uri);
            try {
                URL url = URLUtil.url(normalizeUrl);
                if (StrUtil.isBlank(service)) {
                    service = url.getHost();
                }
                if (StrUtil.isBlank(port)) {
                    port = String.valueOf(url.getPort());
                }
            } catch (Exception e) {
                log.error("uri解析失败：uri = {}", normalizeUrl);
            }
        }
        // 根据环境判断当前访问为ip还是域名
        if (StrUtil.isBlank(service)) {
            service = active.contains("prod") || include.contains("prod") ? appName : "";
        }
        // 使用应用端口为外部访问端口
        if (StrUtil.isBlank(port)) {
            port = this.port;
        }
        model.addAttribute("wsHost", "ws://" + service + ":" + port);
        model.addAttribute("service", "/" + service);
        return "log";
    }

    @GetMapping("download")
    public void download(@RequestParam(required = false) String level, HttpServletResponse response) throws Exception {
        File fileZip = fileHandler.getFileZip(level);
        response.setContentType("application/x-zip-compressed");
        response.setCharacterEncoding("utf-8");
        String filename = URLUtil.encode(fileZip.getName());
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);
        ServletOutputStream outputStream = response.getOutputStream();
        FileUtil.writeToStream(fileZip, outputStream);
        FileUtil.del(fileZip);
        outputStream.close();
    }

    @GetMapping("/testlog1")
    public String testLog(){
        log.info("c成功");
        return "chegn";
    }
}
