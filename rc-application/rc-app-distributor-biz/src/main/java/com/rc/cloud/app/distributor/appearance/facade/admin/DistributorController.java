package com.rc.cloud.app.distributor.appearance.facade.admin;

import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorContactUpdatePasswordReqVO;
import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorCreateReqVO;
import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorPageReqVO;
import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorUpdateReqVO;
import com.rc.cloud.app.distributor.appearance.facade.admin.resp.DistributorDetailRespVO;
import com.rc.cloud.app.distributor.appearance.facade.admin.resp.DistributorRespVO;
import com.rc.cloud.app.distributor.appearance.vo.DistributorContactBaseVO;
import com.rc.cloud.app.distributor.appearance.facade.admin.convert.DistributorContactConvert;
import com.rc.cloud.app.distributor.appearance.facade.admin.convert.DistributorConvert;
import com.rc.cloud.app.distributor.appearance.facade.admin.convert.DistributorDetailConvert;
import com.rc.cloud.app.distributor.application.service.DistributorContactService;
import com.rc.cloud.app.distributor.application.service.DistributorService;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorDetailPO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorPO;
import com.rc.cloud.app.system.api.user.feign.RemoteUserService;
import com.rc.cloud.app.system.api.user.vo.SysUserInfoVO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.security.service.RcUser;
import com.rc.cloud.common.security.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.rc.cloud.common.core.web.CodeResult.SUCCESS;

@Tag(name = "用户 APP - 经销商")
@RestController
@RequestMapping("/distributor/")
@Validated
public class DistributorController {

    @Resource
    private DistributorService service;

    @Resource
    private RemoteUserService userService;

    @Resource
    private DistributorContactService contactService;

    /**
     * 创建经销商
     *
     * @param createReqVO
     * @return 创建的经销商id
     */
    @PostMapping("/create")
    @Operation(summary = "创建经销商")
    public CodeResult<String> create(@Valid @RequestBody DistributorCreateReqVO createReqVO) {
        RcUser user = SecurityUtils.getUser();
        createReqVO.setAdminId(user.getId());
        return CodeResult.ok(service.create(createReqVO));
    }

    /**
     * 更新经销商
     *
     * @param updateReqVO
     * @return 更新结果
     */
    @PutMapping("/update")
    @Operation(summary = "更新经销商")
    public CodeResult<Boolean> update(@Valid @RequestBody DistributorUpdateReqVO updateReqVO) {
        service.update(updateReqVO);
        return CodeResult.ok(true);
    }

    /**
     * 根据id删除经销商
     *
     * @param id 经销商id
     * @return 删除结果
     */
    @DeleteMapping("/delete")
    @Operation(summary = "删除经销商")
    @Parameter(name = "id", description = "编号", required = true)
    public CodeResult<Boolean> delete(@RequestParam("id") String id) {
        service.delete(id);
        return CodeResult.ok(true);
    }

    /**
     * 根据id获取经销商
     *
     * @param id 经销商id
     * @return DistributorRespVO
     */
    @GetMapping("/get")
    @Operation(summary = "获得经销商")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CodeResult<DistributorRespVO> get(@RequestParam("id") String id) {
        DistributorPO distributorPO = service.get(id);
        DistributorRespVO respvo = DistributorConvert.INSTANCE.convert(distributorPO);
        CodeResult<List<SysUserInfoVO>> sysUserVOCodeResult = userService.infoByIds(Arrays.asList(distributorPO.getAdminId()));
        //显示管理员名称
        if (SUCCESS.getCode().equals(sysUserVOCodeResult.getCode())) {
            Map<String, SysUserInfoVO> sysUserVOMap = sysUserVOCodeResult.getData()
                    .stream().collect(Collectors.toMap(SysUserInfoVO::getId, Function.identity()));
            if (sysUserVOMap.containsKey(respvo.getAdminId())) {
                respvo.setAdminName(sysUserVOMap.get(respvo.getAdminId()).getUsername());
            }
        }
        return CodeResult.ok(respvo);
    }

    /**
     * 根据id获取经销商详细信息
     *
     * @param id 经销商id
     * @return DistributorDetailRespVO
     */
    @GetMapping("/getDetail")
    @Operation(summary = "获得经销商详细信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CodeResult<DistributorDetailRespVO> getDetail(@RequestParam("id") String id) {
        DistributorDetailPO distributorDetailPO = service.getDetail(id);
        return CodeResult.ok(DistributorDetailConvert.INSTANCE.convert(distributorDetailPO));
    }

    /**
     * 根据id集合获得经销商列表
     *
     * @param ids 经销商id集合
     * @return DistributorRespVO列表
     */
    @GetMapping("/list")
    @Operation(summary = "获得经销商列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    public CodeResult<List<DistributorRespVO>> getList(@RequestParam("ids") Collection<String> ids) {
        List<DistributorPO> list = service.getList(ids);
        List<DistributorRespVO> distributorRespVOS = DistributorConvert.INSTANCE.convertList(list);
        List<String> userIds = distributorRespVOS.stream().map(x -> x.getAdminId()).distinct().collect(Collectors.toList());
        CodeResult<List<SysUserInfoVO>> sysUserVOCodeResult = userService.infoByIds(userIds);
        //显示管理员名称
        if (SUCCESS.getCode().equals(sysUserVOCodeResult.getCode())) {
            Map<String, SysUserInfoVO> sysUserVOMap = sysUserVOCodeResult.getData()
                    .stream().collect(Collectors.toMap(SysUserInfoVO::getId, Function.identity()));
            distributorRespVOS.forEach(x -> {
                if (sysUserVOMap.containsKey(x.getAdminId())) {
                    x.setAdminName(sysUserVOMap.get(x.getAdminId()).getUsername());
                }
            });
        }
        //按创建时间倒序排
        distributorRespVOS.sort(Comparator.comparing(DistributorRespVO::getCreateTime).reversed());
        return CodeResult.ok(distributorRespVOS);
    }

    /**
     * 获得经销商分页
     *
     * @param pageVO 分页及条件参数
     * @return DistributorRespVO分页
     */
    @GetMapping("/page")
    @Operation(summary = "获得经销商分页")
    public CodeResult<PageResult<DistributorRespVO>> getPage(@Valid DistributorPageReqVO pageVO) {
        PageResult<DistributorPO> pageResult = service.getPage(pageVO);
        List<String> userIds = pageResult.getList().stream().map(x -> x.getAdminId()).distinct().collect(Collectors.toList());
        PageResult<DistributorRespVO> distributorRespVOPageResult = DistributorConvert.INSTANCE.convertPage(pageResult);
        CodeResult<List<SysUserInfoVO>> sysUserVOCodeResult = userService.infoByIds(userIds);
        //显示管理员名称
        if (SUCCESS.getCode().equals(sysUserVOCodeResult.getCode())) {
            Map<String, SysUserInfoVO> sysUserVOMap = sysUserVOCodeResult.getData()
                    .stream().collect(Collectors.toMap(SysUserInfoVO::getId, Function.identity()));
            distributorRespVOPageResult.getList().forEach(x -> {
                if (sysUserVOMap.containsKey(x.getAdminId())) {
                    x.setAdminName(sysUserVOMap.get(x.getAdminId()).getUsername());
                }
            });
        }
        return CodeResult.ok(distributorRespVOPageResult);
    }

//    @GetMapping("/export-excel")
//    @Operation(summary = "导出经销商 Excel")
//    public void exportExcel(@Valid DistributorExportReqVO exportReqVO,
//                            HttpServletResponse response) throws IOException {
//        List<DistributorPO> list = service.getList(exportReqVO);
//        // 导出 Excel
//        List<DistributorExcelVO> datas = DistributorConvert.INSTANCE.convertList02(list);
//        ExcelUtils.write(response, "经销商.xls", "数据", DistributorExcelVO.class, datas);
//    }

    /**
     * 更新联系人密码
     *
     * @param updatePasswordReqVO
     * @return 更新结果
     */
    @PutMapping("/updateContactPassword")
    @Operation(summary = "更新联系人密码")
    public CodeResult<Boolean> updateContactPassword(@Valid @RequestBody DistributorContactUpdatePasswordReqVO updatePasswordReqVO) {
        contactService.updatePassword(updatePasswordReqVO);
        return CodeResult.ok(true);
    }

    /**
     * 重置联系人密码
     *
     * @param id 联系人id
     * @return 重置结果
     */
    @PutMapping("/resetContactPassword")
    @Operation(summary = "重置联系人密码")
    public CodeResult<Boolean> resetContactPassword(@RequestParam("id") String id) {
        contactService.resetPassword(id);
        return CodeResult.ok(true);
    }

    /**
     * 获取经销商联系人
     *
     * @param id 经销商id
     * @return DistributorContactBaseVO集合
     */
    @GetMapping("/getContacts")
    @Operation(summary = "获取经销商联系人")
    public CodeResult<List<DistributorContactBaseVO>> getContacts(@RequestParam("id") String id) {
        return CodeResult.ok(DistributorContactConvert.INSTANCE.convertList3(contactService.getByDistributorId(id)));
    }

    /**
     * 删除经销商至回收站
     *
     * @param id 经销商id
     * @return 删除结果
     */
    @DeleteMapping("/deleteToRecycle")
    @Operation(summary = "删除经销商至回收站")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CodeResult<Boolean> deleteToRecycle(@RequestParam("id") String id) {
        service.deleteToRecycle(id);
        return CodeResult.ok(true);
    }

    /**
     * 从回收站恢复经销商
     *
     * @param id 经销商id
     * @return 操作结果
     */
    @PutMapping("/recycle")
    @Operation(summary = "从回收站恢复经销商")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CodeResult<Boolean> recycle(@RequestParam("id") String id) {
        service.recycle(id);
        return CodeResult.ok(true);
    }

    /**
     * 锁定经销商
     *
     * @param id 经销商id
     * @return 操作结果
     */
    @PutMapping("/lock")
    @Operation(summary = "锁定经销商")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CodeResult<Boolean> lock(@RequestParam("id") String id) {
        service.lock(id);
        return CodeResult.ok(true);
    }

    /**
     * 解除锁定经销商
     *
     * @param id 经销商id
     * @return 操作结果
     */
    @PutMapping("/unlock")
    @Operation(summary = "解除锁定")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CodeResult<Boolean> unlock(@RequestParam("id") String id) {
        service.unlock(id);
        return CodeResult.ok(true);
    }
}
