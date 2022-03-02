package zn.blog.service;

import zn.blog.vo.Result;
import zn.blog.vo.params.PageParams;

public interface ArticleService {

    /**
     * 分页查询 文章列表
     * @param pageParams
     * @return
     */
    Result listArticle(PageParams pageParams);
}
