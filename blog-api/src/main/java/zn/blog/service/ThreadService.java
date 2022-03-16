package zn.blog.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import zn.blog.dao.mapper.ArticleMapper;
import zn.blog.dao.pojo.Article;

@Component
public class ThreadService {

    /**
     * 异步操作：更新阅读次数
     * @Async 定义此方法为一个异步任务,使用自定义的线程池，即由@EnableAsync所标注的类下定义的线程池
     */
    @Async("taskExecutor")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {

        int viewCount = article.getViewCounts();
        //进行articleMapper.update()时，有的值都要设置，所以为了最小限度的更改，就new一个新的Article只设置viewCount
        Article updateArticle = new Article();
        updateArticle.setViewCounts(viewCount+1);
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId,article.getId());
        //设置一个 为了在多线程的环境下 线程安全
        updateWrapper.eq(Article::getViewCounts,viewCount);
        //update article set view_count=100 where view_count=99 and id=11;
        articleMapper.update(updateArticle,updateWrapper);

    }
}
