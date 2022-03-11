package zn.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zn.blog.dao.mapper.ArticleMapper;
import zn.blog.dao.pojo.Article;
import zn.blog.service.ArticleService;
import zn.blog.service.SysUserService;
import zn.blog.service.TagService;
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

    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record, isTag, isAuthor));
        }
        return articleVoList;
    }

    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        //Article的createDate是Long，ArticleVo的是String，要转一下
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        //首页展示文章的标签、作者信息
        if (isTag) {
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
//        if (isAuthor) {
//            Long authorId = article.getAuthorId();
//            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
//        }
        return articleVo;
    }

}
