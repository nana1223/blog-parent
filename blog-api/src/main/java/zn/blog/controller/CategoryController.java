package zn.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zn.blog.service.CategoryService;
import zn.blog.vo.Result;

@RestController
@RequestMapping("categorys")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取文章所有的分类（就发表文章时候 要勾选文章分类）
     *
     * @return
     */
    @GetMapping
    public Result categories() {
        return categoryService.findAll();
    }

    /**
     * 导航 文章分类
     *
     * @return
     */
    @GetMapping("detail")
    public Result categoriesDetail() {
        return categoryService.findAllDetail();
    }

    /**
     * 根据分类 获取该类下的所有文章列表
     * 这个接口获取的是那个页面的 头部对应的分类信息
     */
    @GetMapping("detail/{id}")
    public Result categoriesDetailById(@PathVariable("id") Long categoryId) {
        return categoryService.categoriesDetailById(categoryId);
    }

}
