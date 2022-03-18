package zn.blog.vo.params;


import lombok.Data;
import zn.blog.vo.CategoryVo;
import zn.blog.vo.TagVo;

import java.util.List;

/**
 * 发布文章时 前端传进来的表单对应的参数
 *
 * @author zhangna
 */
@Data
public class ArticleParam {

    private Long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

    private String title;


}

