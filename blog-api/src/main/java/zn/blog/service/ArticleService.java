package zn.blog.service;

import zn.blog.vo.ArticleVo;
import zn.blog.vo.params.PageParams;

import java.util.List;

public interface ArticleService {

    /**
     * 分页查询 文章列表
     * @param pageParams
     * @return
     */
    List<ArticleVo> listArticle(PageParams pageParams);
}
