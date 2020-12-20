package cn.tlh.admin.consumer.controller.system;

import cn.tlh.admin.common.util.RsaProperties;
import cn.tlh.admin.common.base.dto.RoleSmallDto;
import cn.tlh.admin.common.base.dto.UserDto;
import cn.tlh.admin.common.base.dto.UserReq;
import cn.tlh.admin.common.exception.customexception.BusinessErrorException;
import cn.tlh.admin.common.base.mapstruct.UserMapper;
import cn.tlh.admin.common.pojo.system.SysUser;
import cn.tlh.admin.common.util.RsaUtils;
import cn.tlh.admin.common.util.enums.CodeEnum;
import cn.tlh.admin.common.base.vo.req.UserPassVo;
import cn.tlh.admin.common.base.vo.resp.Response;
import cn.tlh.admin.service.aop.annotaion.Log;
import cn.tlh.admin.service.system.RoleService;
import cn.tlh.admin.service.system.UserService;
import cn.tlh.admin.service.system.VerifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author TANG
 * @date 2020-11-23
 */
@Api(tags = "系统：用户管理")
@RestController
@RequestMapping("/api/users")
public class UserController {

//    PasswordEncoder passwordEncoder;

    @Reference(version = "${service.version}", check = false)
    UserService userService;
    @Reference(version = "${service.version}", check = false)
    RoleService roleService;
    @Reference(version = "${service.version}", check = false)
    VerifyService verificationCodeService;

    UserMapper userMapper;

    @ApiOperation("查询用户")
    @GetMapping
    // // @PreAuthorize("@el.check('SysUser:list')")
    public Response query(UserReq userReq) {
        return Response.ok(userService.selectList(userReq));
    }

    @ApiOperation("导出用户数据")
    @GetMapping(value = "/download")
    // // @PreAuthorize("@el.check('SysUser:list')")
    public void download(HttpServletResponse response, UserReq userReq) throws IOException {
        List<SysUser> records = userService.selectList(userReq).getRecords();
        List<UserDto> userDtoList = userMapper.toDto(records);
        userService.download(userDtoList, response);
    }

    @Log(description = "新增用户")
    @ApiOperation("新增用户")
    @PostMapping
    // // @PreAuthorize("@el.check('SysUser:add')")
    public Response create(@Validated @RequestBody SysUser resources) {
        checkLevel(resources);
        // 默认密码 123456
//        resources.setPassword(passwordEncoder.encode("123456"));
        userService.create(resources);
        return Response.ok();
    }

    @Log(description = "修改用户")
    @ApiOperation("修改用户")
    @PutMapping
    // // @PreAuthorize("@el.check('SysUser:edit')")
    public Response update(@Validated @RequestBody SysUser resources) {
        checkLevel(resources);
        userService.update(resources);
        return Response.ok();
    }

    @Log(description = "修改用户：个人中心")
    @ApiOperation("修改用户：个人中心")
    @PutMapping(value = "center")
    public Response center(@Validated @RequestBody SysUser resources) {
        // SecurityUtils.getCurrentUserId()
        if (!resources.getUserId().equals("currentUserId")) {
            throw new BusinessErrorException("不能修改他人资料");
        }
        userService.updateCenter(resources);
        return Response.ok();
    }

    @Log(description = "删除用户")
    @ApiOperation("删除用户")
    @DeleteMapping
    // // @PreAuthorize("@el.check('SysUser:del')")
    public Response delete(@RequestBody Set<Long> ids) {
        Integer currentUserId = 1;
        List<Integer> integerList = new ArrayList<>();
        integerList.add(currentUserId);
        for (Long id : ids) {
            // roleService.findByUsersId(SecurityUtils.getCurrentUserId()).stream().map(RoleSmallDto::getLevel).collect(Collectors.toList())
            Integer currentLevel = Collections.min(integerList);
            Integer optLevel = Collections.min(roleService.findByUsersId(id).stream().map(RoleSmallDto::getLevel).collect(Collectors.toList()));
            if (currentLevel > optLevel) {
                throw new BusinessErrorException("角色权限不足，不能删除：" + userService.findById(id).getUsername());
            }
        }
        userService.delete(ids);
        return Response.ok();
    }

    @ApiOperation("修改密码")
    @PostMapping(value = "/updatePass")
    public Response updatePass(@RequestBody UserPassVo passVo) throws Exception {
        String oldPass = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, passVo.getOldPass());
        String newPass = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, passVo.getNewPass());
        // SecurityUtils.getCurrentUsername()
/*        SysUser SysUser = userService.findByName("currentUsername");
        if (!passwordEncoder.matches(oldPass, SysUser.getPassword())) {
            throw new BusinessErrorException("修改失败，旧密码错误");
        }
        if (passwordEncoder.matches(newPass, SysUser.getPassword())) {
            throw new BusinessErrorException("新密码不能与旧密码相同");
        }
        userService.updatePass(SysUser.getUsername(), passwordEncoder.encode(newPass));*/
        return Response.ok();
    }

    @ApiOperation("修改头像")
    @PostMapping(value = "/updateAvatar")
    public Response updateAvatar(@RequestParam MultipartFile avatar) {
        return Response.ok(userService.updateAvatar(avatar));
    }

    @Log(description = "修改邮箱")
    @ApiOperation("修改邮箱")
    @PostMapping(value = "/updateEmail/{code}")
    public Response updateEmail(@PathVariable String code, @RequestBody SysUser SysUser) throws Exception {
        String password = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, SysUser.getPassword());
        // String currentUsername = SecurityUtils.getCurrentUsername()
        SysUser userDto = userService.findByName("currentUsername");
/*        if (!passwordEncoder.matches(password, userDto.getPassword())) {
            throw new BusinessErrorException("密码错误");
        }*/
        verificationCodeService.validated(CodeEnum.EMAIL_RESET_EMAIL_CODE.getKey() + SysUser.getEmail(), code);
        userService.updateEmail(userDto.getUsername(), SysUser.getEmail());
        return Response.ok();
    }

    /**
     * 如果当前用户的角色级别低于创建用户的角色级别，则抛出权限不足的错误
     *
     * @param resources /
     */
    private void checkLevel(SysUser resources) {
//        Integer currentLevel = Collections.min(roleService.findByUsersId(SecurityUtils.getCurrentUserId()).stream().map(RoleSmallDto::getLevel).collect(Collectors.toList()));
        Integer currentLevel = 2;
//        Integer optLevel = roleService.findByRoles(resources.getRoles());
        Integer optLevel = 1;
        if (currentLevel > optLevel) {
            throw new BusinessErrorException("角色权限不足");
        }
    }
}
