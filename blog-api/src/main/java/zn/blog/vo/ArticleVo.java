package zn.blog.vo;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

@Data
public class ArticleVo {

    private Long id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    private Integer weight;
    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 作者名称 即SysUser-nickname
     */
    private String author;

    //文章内容
    private ArticleBodyVo body;
    /**
     * 文章所属的标签们
     */
    private List<TagVo> tags;

    //文章所属类别
    private CategoryVo category;


}
