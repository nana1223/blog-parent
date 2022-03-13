package zn.blog.service;

import org.springframework.transaction.annotation.Transactional;
import zn.blog.dao.pojo.SysUser;
import zn.blog.vo.Result;
import zn.blog.vo.params.LoginParam;

public interface LoginService {

    /**
     * 登录功能
     *
     * @param loginParam
     * @return
     */
    Result login(LoginParam loginParam);

    /**
     * 检查token合法性：1）是否为空，2）解析是否成功，3）redis中是否存在
     *
     * @param token
     * @return
     */
    SysUser checkToken(String token);

    /**
     * 退出登录
     *
     * @param token
     * @return
     */
    Result logout(String token);

    /**
     * 注册功能
     *
     * @param loginParam
     * @return
     */
    Result register(LoginParam loginParam);
}
