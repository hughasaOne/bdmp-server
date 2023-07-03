package com.rhy.bdmp.open.modules.user.controller;

import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bdmp.open.modules.user.domain.bo.GetUserByOrgBo;
import com.rhy.bdmp.open.modules.user.domain.vo.UserLoginVo;
import com.rhy.bdmp.open.modules.user.domain.vo.UserOrgRelationVo;
import com.rhy.bdmp.open.modules.user.domain.vo.UserVo;
import com.rhy.bdmp.open.modules.user.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Api(tags = "用户服务")
@RestController
@RequestMapping("/bdmp/public/user")
public class UserController {

    @Resource
    private IUserService userService;

    @ApiOperation("获取用户信息")
    @GetMapping("/info/v1")
    public RespResult<UserVo> getUserInfo(@RequestParam("userId") String userId) {
        return RespResult.ok(userService.getUserInfo(userId));
    }

    @ApiOperation("用户组织关系")
    @PostMapping("/org/relation/v1")
    public RespResult<List<UserOrgRelationVo>> getUserOrgRelation(@RequestBody Set<String> userIds) {
        return RespResult.ok(userService.getUserOrgRelation(userIds));
    }

    @ApiOperation("用户登录(供第三方集成使用)")
    @GetMapping("/login/v1")
    public RespResult<UserLoginVo> login(@RequestParam("account") String account) {
        return RespResult.ok(userService.login(account));
    }

    @ApiOperation("根据组织机构获取用户")
    @PostMapping("/byOrg/v1")
    public RespResult<List<UserVo>> getUserByOrg(@Validated @RequestBody GetUserByOrgBo getUserByOrgBo) {
        return RespResult.ok(userService.getUserByOrg(getUserByOrgBo));
    }
}
