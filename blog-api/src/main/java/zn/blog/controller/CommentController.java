package zn.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zn.blog.service.CommentService;
import zn.blog.vo.Result;
import zn.blog.vo.params.CommentParam;

/**
 * @author zhangna
 */
@RestController
@RequestMapping("comments")
public class CommentController {

    @Autowired
    private CommentService commentsService;

    /**
     * 访问文章详情页时 获取该文章对应的评论
     *
     * @param articleId
     * @return
     */
    @GetMapping("article/{id}")
    public Result comments(@PathVariable("id") Long articleId) {
        return commentsService.commentsByArticleId(articleId);
    }

    /**
     * 发表评论
     */
    @PostMapping("creat/change")
    public Result comment(@RequestBody CommentParam commentParam) {
        return commentsService.comment(commentParam);
    }
}
