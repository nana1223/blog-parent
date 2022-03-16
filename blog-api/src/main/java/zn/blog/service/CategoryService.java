package zn.blog.service;

import zn.blog.vo.CategoryVo;

public interface CategoryService {
    /**
     * 根据类别id查询类别信息
     * @param categoryId
     * @return
     */
    CategoryVo findCategoryById(Long categoryId);
}
