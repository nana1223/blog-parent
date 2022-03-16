package zn.blog.dao.pojo;

import lombok.Data;

/**
 * 文章所属类别
 */
@Data
public class Category {

    private Long id;

    private String avatar;

    private String categoryName;

    private String description;
}
