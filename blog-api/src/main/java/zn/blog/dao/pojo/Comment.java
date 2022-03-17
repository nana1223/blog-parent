package zn.blog.dao.pojo;

import lombok.Data;

@Data
public class Comment {

    private Long id;

    private String content;

    private Long createDate;

    private Long articleId;

    private Long authorId;

    private Long parentId;

    /**
     * 在第二层的评论，就会记录toUid（第一层的人），但是第一层的评论是给文章评论的，toUid==null
     */
    private Long toUid;

    private Integer level;
}