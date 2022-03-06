package zn.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zn.blog.service.ArticleService;
import zn.blog.vo.ArticleVo;
import zn.blog.vo.Result;
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

}
