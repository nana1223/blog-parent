package zn.blog.service;

import zn.blog.vo.Result;

public interface CommentService {
    /**
     * 根据文章id 查所有评论列表
     * @param articleId
     * @return
     */
    Result commentsByArticleId(Long articleId);
}
