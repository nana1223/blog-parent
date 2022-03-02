package zn.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zn.blog.service.ArticleService;
import zn.blog.vo.Result;
import zn.blog.vo.params.PageParams;

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
     * @return
     */
    @PostMapping()
    public Result listArticle(@RequestBody PageParams pageParams) {
        return articleService.listArticle(pageParams);
    }

}
