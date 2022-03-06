package zn.blog.vo;


import lombok.Data;

import java.util.List;

@Data
public class ArticleVo {

    private Long id;

    private String title;

    private String summary;

    private int commentCounts;

    private int viewCounts;

    private int weight;
    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 作者名称 即SysUser-nickname
     */
    private String author;

//    private ArticleBodyVo body;
    /**
     * 文章所属的标签们
     */
    private List<TagVo> tags;
//
//    private List<CategoryVo> categorys;

}
