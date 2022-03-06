package zn.blog.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zn.blog.dao.mapper.TagMapper;
import zn.blog.dao.pojo.Tag;
import zn.blog.service.TagService;
import zn.blog.vo.TagVo;

import java.util.ArrayList;
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
}
