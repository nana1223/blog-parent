package zn.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zn.blog.service.CommentService;
import zn.blog.vo.Result;

/**
 * @author zhangna
 */
@RestController
@RequestMapping("comments")
public class CommentController {

    @Autowired
    private CommentService commentsService;

    @GetMapping("article/{id}")
    public Result comments(@PathVariable("id") Long articleId) {
        return commentsService.commentsByArticleId(articleId);
    }
}
