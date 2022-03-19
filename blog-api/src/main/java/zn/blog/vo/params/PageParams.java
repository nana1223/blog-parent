package zn.blog.vo.params;

import lombok.Data;

/**
 * 展示文章列表 传过来的参数
 */
@Data
public class PageParams {

    private int page = 1;

    private int pageSize = 10;

    private Long categoryId;

    private Long tagId;
}
