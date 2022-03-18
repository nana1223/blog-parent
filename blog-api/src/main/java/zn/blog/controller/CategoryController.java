package zn.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
     * 获取文章所有的分类
     *
     * @return
     */
    @GetMapping
    public Result categories() {
        return categoryService.findAll();
    }
}
