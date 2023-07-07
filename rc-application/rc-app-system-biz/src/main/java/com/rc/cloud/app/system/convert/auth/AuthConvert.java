package com.rc.cloud.app.system.convert.auth;

import com.rc.cloud.app.system.model.permission.SysMenuDO;
import com.rc.cloud.app.system.model.permission.SysRoleDO;
import com.rc.cloud.app.system.model.user.SysUserDO;
import com.rc.cloud.app.system.vo.auth.AuthMenuRespVO;
import com.rc.cloud.app.system.vo.auth.AuthPermissionInfoRespVO;
import com.rc.cloud.common.core.util.collection.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.rc.cloud.app.system.model.permission.SysMenuDO.ID_ROOT;
import static com.rc.cloud.common.core.util.collection.CollectionUtils.filterList;


@Mapper
public interface AuthConvert {

    AuthConvert INSTANCE = Mappers.getMapper(AuthConvert.class);

//    AuthLoginRespVO convert(OAuth2AccessTokenDO bean);

    default AuthPermissionInfoRespVO convert(SysUserDO user, List<SysRoleDO> roleList, List<SysMenuDO> menuList) {
        return AuthPermissionInfoRespVO.builder()
            .user(AuthPermissionInfoRespVO.UserVO.builder().id(user.getId()).nickname(user.getNickname()).avatar(user.getAvatar()).build())
            .roles(CollectionUtils.convertSet(roleList, SysRoleDO::getCode))
            .permissions(CollectionUtils.convertSet(menuList, SysMenuDO::getPermission))
            .build();
    }

    AuthMenuRespVO convertTreeNode(SysMenuDO menu);

    /**
     * 将菜单列表，构建成菜单树
     *
     * @param menuList 菜单列表
     * @return 菜单树
     */
    default List<AuthMenuRespVO> buildMenuTree(List<SysMenuDO> menuList) {
        // 排序，保证菜单的有序性
        menuList.sort(Comparator.comparing(SysMenuDO::getSort));
        // 构建菜单树
        // 使用 LinkedHashMap 的原因，是为了排序 。实际也可以用 Stream API ，就是太丑了。
        Map<String, AuthMenuRespVO> treeNodeMap = new LinkedHashMap<>();
        menuList.forEach(menu -> treeNodeMap.put(menu.getId(), AuthConvert.INSTANCE.convertTreeNode(menu)));
        // 处理父子关系
        treeNodeMap.values().stream().filter(node -> !node.getParentId().equals(ID_ROOT)).forEach(childNode -> {
            // 获得父节点
            AuthMenuRespVO parentNode = treeNodeMap.get(childNode.getParentId());
            if (parentNode == null) {
                LoggerFactory.getLogger(getClass()).error("[buildRouterTree][resource({}) 找不到父资源({})]",
                    childNode.getId(), childNode.getParentId());
                return;
            }
            // 将自己添加到父节点中
            if (parentNode.getChildren() == null) {
                parentNode.setChildren(new ArrayList<>());
            }
            parentNode.getChildren().add(childNode);
        });
        // 获得到所有的根节点
        return filterList(treeNodeMap.values(), node -> ID_ROOT.equals(node.getParentId()));
    }

//    SocialUserBindReqDTO convert(String userId, Integer userType, AuthSocialLoginReqVO reqVO);

//    SmsCodeSendReqDTO convert(AuthSmsSendReqVO reqVO);
//
//    SmsCodeUseReqDTO convert(AuthSmsLoginReqVO reqVO, Integer scene, String usedIp);

}
