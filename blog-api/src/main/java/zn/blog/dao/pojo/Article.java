package zn.blog.dao.pojo;

import lombok.Data;

@Data
public class Article {

    public static final int Article_TOP = 1;

    public static final int Article_Common = 0;

    private Long id;

    private String title;

    private String summary;

    /**
     * 理解：int型的数据不能写int，需要写成Integer，因为int会给默认值0，
     * 然后在更新阅读次数那个操作那儿articleMapper.update()，传入整个pojo，mybatisplus就会默认给所有有值的属性都更新，就会改变int的字段值，所以要写成封装类
     */
    private Integer commentCounts;

    private Integer viewCounts;

    /**
     * 作者id
     */
    private Long authorId;
    /**
     * 内容id
     */
    private Long bodyId;
    /**
     *类别id
     */
    private Long categoryId;

    /**
     * 置顶
     */
    private Integer weight;


    /**
     * 创建时间
     */
    private Long createDate;
}

