package zn.blog.service;

import zn.blog.vo.CategoryVo;
import zn.blog.vo.Result;

public interface CategoryService {
    /**
     * 根据类别id查询类别信息
     * @param categoryId
     * @return
     */
    CategoryVo findCategoryById(Long categoryId);

    /**
     * 获取所有类别
     * @return
     */
    Result findAll();

    Result findAllDetail();

    Result categoriesDetailById(Long categoryId);
}
