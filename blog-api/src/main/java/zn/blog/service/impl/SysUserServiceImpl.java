package zn.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zn.blog.dao.mapper.SysUserMapper;
import zn.blog.dao.pojo.SysUser;
import zn.blog.service.SysUserService;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser == null) {
            //?????????????????????????????????????????????空指针报错Cannot invoke "zn.blog.dao.pojo.SysUser.setNickname(String)" because "sysUser" is null
            sysUser.setNickname("匿名");
        }
        return sysUser;
    }
}
