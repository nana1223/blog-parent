package zn.blog.service;

import zn.blog.dao.pojo.SysUser;

public interface SysUserService {

    /**
     * 根据作者id 查作者的详细信息
     *
     * @param id
     * @return
     */
    SysUser findUserById(Long id);
}
