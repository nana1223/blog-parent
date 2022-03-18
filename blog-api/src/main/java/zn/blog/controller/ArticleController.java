package zn.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zn.blog.service.ArticleService;

import zn.blog.vo.ArticleVo;
import zn.blog.vo.Result;
import zn.blog.vo.params.ArticleParam;
import zn.blog.vo.params.PageParams;

import java.util.List;

//json数据进行交互
@RestController
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 首页  文章列表
     *
     * @param pageParams
     * @return Result是统一返回结果
     */
    @PostMapping()
    public Result listArticle(@RequestBody PageParams pageParams) {
        List<ArticleVo> articleVoList = articleService.listArticle(pageParams);
        return Result.success(articleVoList);
    }

    /**
     * 首页 最热文章
     *
     * @return
     */
    @PostMapping("hot")
    public Result hotArticle() {
        int limit = 5;
        return articleService.hotArticle(limit);
    }

    /**
     * 首页 最新文章
     *
     * @return
     */
    @PostMapping("new")
    public Result newArticles() {
        int limit = 5;
        return articleService.newArticles(limit);
    }

    /**
     * 首页 文章归档
     *
     * @return
     */
    @PostMapping("listArchives")
    public Result listArchives() {
        return articleService.listArchives();
    }

    /**
     * 文章详情
     *
     * @param articleId
     * @return
     */
    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id") Long articleId) {
        return articleService.findArticleById(articleId);
    }

    /**
     * 发布文章
     *
     * @param articleParam
     * @return 返回文章id （发布完文章要跳到文章详情页）
     */
    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam) {
        return articleService.publish(articleParam);
    }
}
