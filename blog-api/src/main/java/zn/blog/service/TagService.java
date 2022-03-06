package zn.blog.service;

import zn.blog.vo.TagVo;

import java.util.List;

public interface TagService {

    /**
     * 根据文章id查标签tag
     * @param articleId
     * @return
     */
    List<TagVo> findTagsByArticleId(Long articleId);
}
