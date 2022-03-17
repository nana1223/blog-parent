package zn.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zn.blog.dao.dos.Archives;
import zn.blog.dao.mapper.ArticleBodyMapper;
import zn.blog.dao.mapper.ArticleMapper;
import zn.blog.dao.pojo.Article;
import zn.blog.dao.pojo.ArticleBody;
import zn.blog.service.*;
import zn.blog.vo.ArticleBodyVo;
import zn.blog.vo.ArticleVo;
import zn.blog.vo.Result;
import zn.blog.vo.params.PageParams;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TagService tagService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ThreadService threadService;

    @Override
    public List<ArticleVo> listArticle(PageParams pageParams) {
        //分页查询数据库表article
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //按置顶和创建时间排序
        queryWrapper.orderByDesc(Article::getWeight, Article::getCreateDate);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<Article> records = articlePage.getRecords();
        //把数据库对应的实体数据转换成页面展示的数据vo对象
        List<ArticleVo> articleVoList = copyList(records, true, true);
        return articleVoList;
    }


    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit);
        List<Article> articleList = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articleList, false, false));
    }

    @Override
    public Result newArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit);
        List<Article> articleList = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articleList, false, false));
    }

    @Override
    public Result listArchives() {
        List<Archives> archivesList = articleMapper.listArchives();
        return Result.success(archivesList);
    }

    @Override
    public Result findArticleById(Long articleId) {
        //1.根据id查询文章信息
        Article article = articleMapper.selectById(articleId);
        //2.根据bodyId和categoryId，做关联查询，查文章的详情内容和分类
        ArticleVo articleVo = copy(article, true, true, true, true);

        //查看完文章，要在这儿新增阅读次数，注意点：
        //1.查看完文章后，本应该直接返回数据，这时候做了一个更新操作，更新时加写锁，阻塞其他的读操作，性能就会比较低
        //2.而且更新 增加了这个查看文章详情业务接口的耗时。如果一旦更新出问题，不能影响查看文章的操作
        //线程池：可以把更新阅读次数的操作 扔到线程池中去执行，就和主线程不相干了
        threadService.updateArticleViewCount(articleMapper, article);
        return Result.success(articleVo);
    }

    /**
     * 一个重载的小思路！！！，方便复用
     *
     * @param records
     * @param isTag
     * @param isAuthor
     * @return
     */
    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record, isTag, isAuthor, false, false));
        }
        return articleVoList;
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor, boolean isBody, boolean idCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record, isTag, isAuthor, isBody, idCategory));
        }
        return articleVoList;
    }

    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor, boolean isBody, boolean idCategory) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        //Article的createDate是Long，ArticleVo的是String，要转一下
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        //首页展示文章的标签、作者信息
        if (isTag) {
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if (isAuthor) {
            Long authorId = article.getAuthorId(); //用postman测都能拿到，但是用前端测就会NullPointerException
            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
        }
        if (isBody) {
            Long bodyId = article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyId));
        }
        if (idCategory) {
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }
        return articleVo;
    }

    private ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }

}
