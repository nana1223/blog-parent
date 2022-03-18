package zn.blog.service;

import zn.blog.vo.Result;
import zn.blog.vo.params.CommentParam;

public interface CommentService {
    /**
     * 根据文章id 查所有评论列表
     * @param articleId
     * @return
     */
    Result commentsByArticleId(Long articleId);

    /**
     * 创建新评论
     * @param commentParam
     * @return
     */
    Result comment(CommentParam commentParam);
}
