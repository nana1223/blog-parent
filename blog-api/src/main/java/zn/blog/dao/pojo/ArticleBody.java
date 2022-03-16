package zn.blog.dao.pojo;

import lombok.Data;

/**
 * 文章内容
 */
@Data
public class ArticleBody {

    private Long id;
    private String content;
    private String contentHtml;
    private Long articleId;
}
