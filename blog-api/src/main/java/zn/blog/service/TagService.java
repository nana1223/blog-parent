package zn.blog.service;

import zn.blog.vo.Result;
import zn.blog.vo.TagVo;

import java.util.List;

public interface TagService {

    /**
     * 根据文章id查标签tag
     * @param articleId
     * @return
     */
    List<TagVo> findTagsByArticleId(Long articleId);

    /**
     * 首页获取最热标签：即该标签下文章数量是最多的
     * @param limit
     * @return
     */
    Result hots(int limit);
}
