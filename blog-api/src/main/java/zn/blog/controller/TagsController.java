package zn.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zn.blog.service.TagService;
import zn.blog.vo.Result;

@RestController
@RequestMapping("tags")
public class TagsController {

    @Autowired
    private TagService tagService;

    /**
     * 首页 最热标签展示 tags/hot
     *
     * @return
     */
    @GetMapping("hot")
    public Result hot() {
        int limit = 6;
        return tagService.hots(limit);
    }

    /**
     * 获取所有的文章标签（在发布文章时 获取的）
     *
     * @return
     */
    @GetMapping
    public Result findAll() {
        return tagService.findAll();
    }

    /**
     * 导航栏 获取所有文章标签
     *
     * @return
     */
    @GetMapping("detail")
    public Result findAllDetail() {
        return tagService.findAllDetail();
    }

    /**
     * 导航栏的标签 点进去的那个页面 上方标签细节的展示
     * @param id
     * @return
     */
    @GetMapping("detail/{id}")
    public Result findDetailById(@PathVariable("id") Long id) {
        return tagService.findDetailById(id);
    }
}
