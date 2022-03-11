package zn.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import zn.blog.dao.pojo.Tag;

import java.util.List;

@Repository
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 根据文章id查询该文章的标签列表
     * @param articleId
     * @return
     */
    List<Tag> findTagsByArticleId(Long articleId);

    /**
     * 查询最热标签（标签下文章数最多的） 前limit条
     * @param limit
     * @return
     */
    List<Long> findHotsTagIds(int limit);

    /**
     * 根据tagId查Tag对象
     * @param tagIdList
     * @return
     */
    List<Tag> findTagsByTagIds(List<Long> tagIdList);
}
