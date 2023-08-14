package com.rc.cloud.uid.provide.service;

import com.rc.cloud.uid.generator.UidGenerator;
import com.rc.cloud.uid.provide.util.RedisUtil;
import com.rc.cloud.uid.provide.util.Util;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service
public class UidGenService {

    //本地生成时缓存key   linked基于链表不需要设置初始化容量
    public LinkedList<Long> localIds = new LinkedList<>();

    @Resource
    private RedisUtil redisUtil;

    @Resource(name = "cachedUidGenerator")
    private UidGenerator uidGenerator;

    public long getUid() {
        return uidGenerator.getUID();
    }

    public Long getUidByLocal() {
        if (localIds.size() > 1) {
            synchronized (this) {
                createUidToLocal();
                return localIds.removeFirst();
            }
        }
        return Long.getLong(Util.getLocalRandom(null));
    }


    public String getUidByRedis() {
        Object o = redisUtil.lPop(Util.uidListKey);
        if (o != null) {
            createUidToRedis();
            return o.toString();
        }
        String localRandom = Util.getLocalRandom(null);
        createUidToRedis();
        return localRandom;
    }


    @Async
    @PostConstruct
    public void createUidToLocal() {
        long create_num = Util.uidListMaxSize - localIds.size();
        if (create_num > Util.uidLackSize) {
            long state_num = 1;
            List<Long> data = new LinkedList<>();
            while (state_num <= create_num) {
                long uid = uidGenerator.getUID();
                data.add(uid);
                state_num++;
            }
            localIds.addAll(data);
        }
    }


    /**
     * 本地初始化id到redis
     * - 异步调用 or 项目启动加载
     *
     */
    @Async
    @PostConstruct
    public void createUidToRedis() {
        long create_num = Util.uidListMaxSize - redisUtil.lGetListSize(Util.uidListKey);
        if (create_num > Util.uidLackSize) {
            long state_num = 1;
            List<Long> data = new LinkedList<>();
            while (state_num < create_num) {
                long uid = uidGenerator.getUID();
                data.add(uid);
                state_num++;
                redisUtil.lSet(Util.uidListKey, uid);
            }
        }
    }


    /**
     * 基于任务调度初始化id
     */
    @XxlJob("createUidToRedisByJob")
    public ReturnT<String> createUidToRedisByJob(String param) {
        long create_num = Util.uidListMaxSize - redisUtil.lGetListSize(Util.uidListKey);
        if (create_num > Util.uidLackSize) {
            long state_num = 1;
            List<Long> data = new LinkedList<>();
            while (state_num < create_num) {
                long uid = uidGenerator.getUID();
                data.add(uid);
                state_num++;
                redisUtil.lSet(Util.uidListKey, uid);
            }
        }
        return ReturnT.SUCCESS;
    }



}
