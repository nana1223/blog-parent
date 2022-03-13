package zn.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zn.blog.service.LoginService;
import zn.blog.vo.Result;
import zn.blog.vo.params.LoginParam;

@RestController
@RequestMapping("login")
public class LoginController {

    //不用 SysUserService 服务要分模块，SysUserService主要就对sysUser表操作
    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result login(@RequestBody LoginParam loginParam) {

        return loginService.login(loginParam);

    }
}
