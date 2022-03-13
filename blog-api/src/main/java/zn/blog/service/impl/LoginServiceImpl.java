package zn.blog.service.impl;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import zn.blog.dao.pojo.SysUser;
import zn.blog.service.LoginService;
import zn.blog.service.SysUserService;
import zn.blog.utils.JWTUtils;
import zn.blog.vo.ErrorCode;
import zn.blog.vo.Result;
import zn.blog.vo.params.LoginParam;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    //加密盐（加盐加密是一种对系统登录口令的加密方式，它实现的方式是将每一个口令同一个叫做”盐“（salt）的n位随机数相关联。）
    private static final String slat = "mszlu!@#";

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Result login(LoginParam loginParam) {
        /**
         * 1.检查参数是否合法
         * 2.根据用户名和密码去sysUser表中查询，是否存在
         * 3.如果不存在，登陆失败；如果存在，使用jwt生成token返回给前端
         * 4.token放入redis当中，redis token：user信息，设置过期时间
         * （登录认证的时候，先认证token字符串是否合法，去redis认证是否存在）
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        //数据库中存的密码是加密后的密码，处理password
        password = DigestUtils.md5Hex(password + slat);
        SysUser sysUser = sysUserService.findUser(account, password);
        if (sysUser == null) {
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        //用户存在，使用jwt生成token, 把token返回给前端
        String token = JWTUtils.createToken(sysUser.getId());
        //把token放在redis里,过期时间1天
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        return Result.success(token);
    }

    @Override
    public SysUser checkToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap == null) {
            return null;
        }
        String userJson = (String) redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)) {
            return null;
        }
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_" + token);
        return Result.success(null);
    }

    @Override
    public Result register(LoginParam loginParam) {
        /**
         * 1.判断传入参数是否合法
         * 2.判断账户是否存在。若存在则返回账户已注册，若不存在则注册用户成功
         * 3.生成token存入redis并返回token
         * 4.注意，加上事务，一旦中间的任何过程出现问题，注册的用户需要回滚
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        String nickname = loginParam.getNickname();
        //1.判断传入参数是否合法
        if (StringUtils.isBlank(account)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(nickname)
        ) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        //2.判断账户是否存在。若存在则返回账户已注册，若不存在则注册用户成功
        SysUser sysUser = sysUserService.findUserByAccount(account);
        if (sysUser != null) {
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(), ErrorCode.ACCOUNT_EXIST.getMsg());
        }
        sysUser = new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password + slat));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/logo.b3a48c0.png");
        sysUser.setAdmin(1); //1 为true
        sysUser.setDeleted(0); // 0 为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        sysUserService.save(sysUser);
        //3.生成token存入redis并返回token
        String token = JWTUtils.createToken(sysUser.getId());
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        return Result.success(token);
    }
}
