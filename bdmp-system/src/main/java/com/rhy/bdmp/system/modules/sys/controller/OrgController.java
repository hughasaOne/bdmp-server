package com.rhy.bdmp.system.modules.sys.controller;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.domain.vo.NodeVo;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.PageUtils;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bcp.logging.annotation.Log;
import com.rhy.bdmp.base.modules.sys.domain.po.Org;
import com.rhy.bdmp.base.modules.sys.domain.po.WayMapping;
import com.rhy.bdmp.base.modules.sys.service.IBaseOrgService;
import com.rhy.bdmp.system.modules.logging.enums.LogTypeEnum;
import com.rhy.bdmp.system.modules.logging.util.LogUtil;
import com.rhy.bdmp.system.modules.sys.domain.vo.CCPWayVo;
import com.rhy.bdmp.system.modules.sys.domain.vo.OrgVo;
import com.rhy.bdmp.system.modules.sys.service.OrgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Api(tags = {"组织机构管理"})
@Slf4j
@RestController
@RequestMapping("/bdmp/system/sys/org")
public class OrgController {

    @Resource
    private OrgService orgService;

    @Resource
    private IBaseOrgService baseOrgService;

    @ApiOperation(value = "查询组织机构(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<Org>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<Org> pageUtil =  baseOrgService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查询组织机构", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult list(@RequestBody(required = false) QueryVO queryVO) {
        Object result = orgService.list(queryVO);
        if (result instanceof Page) {
            return RespResult.ok(new PageUtils((Page<Org>) result));
        } else {
            return RespResult.ok(result);
        }
    }

    @ApiOperation(value = "查看组织机构(根据ID)")
    @GetMapping(value = "/{orgId}")
    public RespResult detail(@PathVariable("orgId") String orgId) {
        OrgVo org = orgService.detail(orgId);
        return RespResult.ok(org);
    }

    /**
     * @Description: 删除组织
     * @Author: dongyu
     * @Date: 2021/4/14
     */
    @ApiOperation("删除组织机构")
    @Log("组织：删除")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> orgIds) {
        orgService.delete(orgIds);
        log.info(LogUtil.buildUpParams("删除组织机构", LogTypeEnum.OPERATE.getCode(),orgIds));
        return RespResult.ok();
    }

    /**
     * @Description: 新增组织
     * @Author: dongyu
     * @Date: 2021/4/20
     */
    @ApiOperation("新增组织机构")
    @Log("组织：新增")
    @PostMapping
    public RespResult create(@Validated @RequestBody Org org) {
        orgService.create(org);
        log.info(LogUtil.buildUpParams("新增组织机构", LogTypeEnum.OPERATE.getCode(),org.getOrgId()));
        return RespResult.ok();
    }

    /**
     * @Description: 修改应组织
     * @Author: dongyu
     * @Date: 2021/4/20
     */
    @ApiOperation("修改组织机构")
    @Log("组织：修改")
    @PutMapping
    public RespResult update(@Validated @RequestBody Org org) {
        orgService.update(org);
        log.info(LogUtil.buildUpParams("修改组织机构", LogTypeEnum.OPERATE.getCode(),org.getOrgId()));
        return RespResult.ok();
    }

    @ApiOperation(value = "查询组织机构树节点")
    @GetMapping(value = "/findOrgTree")
    public RespResult findOrgTree() {
        List<NodeVo> result = orgService.findOrgTree();
        return RespResult.ok(result);
    }

    @ApiOperation("根据父节点获取机构列表")
    @PostMapping(value = "/listByParentId")
    public RespResult<List<OrgVo>> listByParentId(@RequestBody(required = false) QueryVO queryVO){
        List<OrgVo> orgVoLIst =  orgService.listByParentId(queryVO);
        return RespResult.ok(orgVoLIst);
    }


    @ApiOperation("查询组织:根据ID获取同级与上级数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "组织ID字符串,多个英文逗号分隔", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "hasSelf", value = "是否包含参数ID节点", dataType = "boolean", paramType = "query", required = false),
    })
    @GetMapping("/superior")
    public RespResult<List<Tree<String>> > getSuperior(@RequestParam(required = true) String ids, @RequestParam(required = false) Boolean hasSelf) {
        hasSelf = (null == hasSelf) ? false : hasSelf;
        return RespResult.ok(orgService.getSuperior(ids, hasSelf));
    }

    @ApiOperation("查找集控点下的路段")
    @GetMapping("/ccp/way")
    public RespResult<List<CCPWayVo>> getCCPWay(@RequestParam String orgId) {
        return RespResult.ok(orgService.getCCPWay(orgId));
    }

    @ApiOperation("查询路段集控点的映射关系")
    @GetMapping("/ccp/way/mapping")
    public RespResult<List<CCPWayVo>> getCCPWayMapping(@RequestParam String orgId) {
        return RespResult.ok(orgService.getCCPWayMapping(orgId));
    }

    @ApiOperation("保存集控点路段映射关系")
    @PostMapping("/ccp/way/save")
    public RespResult ccpWayMappingSave(@RequestBody List<WayMapping> wayMappingList,@RequestParam("orgId") String orgId) {
        orgService.ccpWayMappingSave(wayMappingList,orgId);
        return RespResult.ok();
    }
}
