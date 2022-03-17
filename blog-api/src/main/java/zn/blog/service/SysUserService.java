package zn.blog.service;

import zn.blog.dao.pojo.SysUser;
import zn.blog.vo.Result;
import zn.blog.vo.UserVo;

public interface SysUserService {

    /**
     * 根据作者id 查作者的详细信息
     *
     * @param id
     * @return
     */
    SysUser findUserById(Long id);

    /**
     * 根据账户密码查用户
     *
     * @param account
     * @param password
     * @return
     */
    SysUser findUser(String account, String password);

    /**
     * 根据token查询用户信息
     *
     * @param token
     * @return
     */
    Result findUserByToken(String token);

    /**
     * 根据账户查找用户
     *
     * @param account
     * @return
     */
    SysUser findUserByAccount(String account);

    /**
     * 保存新用户
     *
     * @param sysUser
     */
    void save(SysUser sysUser);

    /**
     * 根据作者id 查作者的信息
     *
     * @param authorId
     * @return
     */
    UserVo findUserVoById(Long authorId);
}
