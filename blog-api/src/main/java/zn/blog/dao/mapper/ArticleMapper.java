package zn.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import zn.blog.dao.dos.Archives;
import zn.blog.dao.pojo.Article;

import java.util.List;

@Repository
public interface ArticleMapper extends BaseMapper<Article> {


    List<Archives> listArchives();
}
