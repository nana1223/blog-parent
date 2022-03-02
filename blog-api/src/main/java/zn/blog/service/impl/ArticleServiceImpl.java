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
import zn.blog.vo.ArticleVo;
import zn.blog.vo.Result;
import zn.blog.vo.params.PageParams;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Result listArticle(PageParams pageParams) {
        //分页查询数据库表article
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //按置顶和创建时间排序
        queryWrapper.orderByDesc(Article::getWeight, Article::getCreateDate);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<Article> records = articlePage.getRecords();
        //把数据库对应的实体数据转换成页面展示的数据vo对象
        List<ArticleVo> articleVoList = copyList(records);
        return Result.success(articleVoList);
    }

    private List<ArticleVo> copyList(List<Article> records) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record));
        }
        return articleVoList;
    }

    private ArticleVo copy(Article article) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        //Article的createDate是Long，ArticleVo的是String，要转一下
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        return articleVo;
    }
}
