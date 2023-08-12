#  主要模块

```
rc-framework：技术框架 只包含技术相关的公共基础模块
rc-support：支撑组件，支撑rc-application、rc-server功能的公共支撑模块
rc-server：基础服务，为rc-application提供基础服务和运维服务
rc-application：应用服务，包含各个业务模块
rc-api：服务api，由基础服务、应用服务暴露出来的接口，贯穿rc-common、rc-server、和rc-application模块
```

# 项目结构

```
rc-could
├─doc                           文档
│    ├─db                        sql
│    ├─deploy                    部署脚本
│    └─yaml                      配置文件
│ 
├─rc-api                         服务api
│    ├─rc-app-distributor-api    经销商api
│    ├─rc-app-product-dubbo-api  产品api
│    ├─rc-app-system-api         系统api
│    ├─rc-uid-generator-api      uid生成api
│   
├─rc-application                 应用服务
│    ├─rc-app-system-biz         系统模块
│    ├─rc-app-operate-biz        运营模块 
│    ├─rc-app-marketing-biz      营销模块
│    └─rc-app-distributor-biz    经销商模块 
│
├─rc-support                     支撑组件
│    ├─rc-common-dict            核心公共模块
│    ├─rc-common-security        安全模块  
│    └─rc-common-tenant          多租户公共模块  
│
├─rc-framework                   技术框架
│    ├─rc-common-core            核心公共模块
│    ├─rc-common-log             日志公共模块
│    ├─rc-common-mybatis         mybatis公共模块
│    ├─rc-common-redis           redis公共模块
│    ├─rc-common-swagger         swagger公共模块
│    ├─rc-common-tenant          多租户公共模块 
│    └─rc-common-web             web公共模块 
│   
├─rc-server                      基础服务
│    ├─rc-auth-server            认证服务
│    ├─rc-gateway-server         网关  
│    ├─rc-monitor-server         健康监控服务 
│    ├─rc-register-server        注册配置中心
│    └─rc-sentinel-server        流量控制
```



# 

#### 介绍
rc-cloud

#### 软件架构
领域模型编写
    1.应用服务层不能调用仓储层接口
    2.应用服务需要编写接口
    3.领域服务不能用调用其他聚合根下的仓储层接口，比如ProductDomainService只能调用ProductRepository
    4.领域服务不需要编写接口
    5.事件在领域服务层出发
    6.资源库返回的领域对象要完整
	7.不涉及领域层的转换考虑用MapStruct

#### 安装教程

1.  xxxx
2.  xxxx
3.  xxxx

#### 使用说明

1.  xxxx
2.  xxxx
3.  xxxx

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request

