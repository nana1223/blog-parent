package zn.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zn.blog.service.SysUserService;
import zn.blog.vo.Result;

@RestController
@RequestMapping("users")
public class UsersController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 根据前端提供的token 获取用户信息
     * Authorization 存在http头部中，所以用 @RequestHeader获取
     *
     * @param token
     * @return
     */
    @GetMapping("currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token) {
        return sysUserService.findUserByToken(token);
    }

}
