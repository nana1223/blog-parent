package zn.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zn.blog.dao.mapper.TagMapper;
import zn.blog.dao.pojo.Tag;
import zn.blog.service.TagService;
import zn.blog.vo.Result;
import zn.blog.vo.TagVo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        //mybatisplus没有封装多表查询，得用mybatis手写
        List<Tag> tagList = tagMapper.findTagsByArticleId(articleId);
        return copyList(tagList);
    }

    public List<TagVo> copyList(List<Tag> tagList) {
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    public TagVo copy(Tag tag) {
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag, tagVo);
        return tagVo;
    }

    @Override
    public Result hots(int limit) {
        //查询 根据tag_id 分组 计数，从大到小排列 取前limit个
        List<Long> tagIdList = tagMapper.findHotsTagIds(limit);
        //select * from tag where id in (1,2,3)    sql语句的in子句内不能为空
        if (CollectionUtils.isEmpty(tagIdList)) {
            return Result.success(Collections.emptyList());
        }
        List<Tag> tagList = tagMapper.findTagsByTagIds(tagIdList);
        return Result.success(tagList);
    }

    @Override
    public Result findAll() {

        List<Tag> tags = this.tagMapper.selectList(new LambdaQueryWrapper<>());
        return Result.success(copyList(tags));
    }
}
