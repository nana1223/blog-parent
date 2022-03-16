package zn.blog.service;

import zn.blog.vo.ArticleVo;
import zn.blog.vo.Result;
import zn.blog.vo.params.PageParams;

import java.util.List;

public interface ArticleService {

    /**
     * 分页查询 文章列表
     *
     * @param pageParams
     * @return
     */
    List<ArticleVo> listArticle(PageParams pageParams);

    /**
     * 最热文章
     *
     * @param limit
     * @return
     */
    Result hotArticle(int limit);

    /**
     * 最新文章
     *
     * @param limit
     * @return
     */
    Result newArticles(int limit);

    /**
     * 文章归档
     *
     * @return
     */
    Result listArchives();

    /**
     * 查看文章详情
     *
     * @param articleId
     * @return
     */
    Result findArticleById(Long articleId);
}
