package zn.blog.vo;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

@Data
public class CommentVo  {

    /**
     * id是分布式id，过长，导致前端解析出错，把id转成String，再传到前端，防止前端精度损失
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private UserVo author;

    private String content;

    private List<CommentVo> children;

    private String createDate;

    private Integer level;

    private UserVo toUser;
}
