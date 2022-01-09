package org.sadness.transaction.infrastructure.helper;

import cn.hutool.db.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p></p>
 *
 * @author xlp
 * @version 1.0.0
 * @since 2021/12/19 14:32
 */
public class PageHelper {

    public static <T> Page<T> createQueryPage(cn.hutool.db.Page page) {
        return new Page<>(page.getPageNumber(), page.getPageSize());
    }

    public static <T> PageResult<T> createPageResult(Page<T> page) {
        PageResult<T> result = new PageResult<>();
        result.setPage((int) page.getCurrent());
        result.setPageSize((int) page.getSize());
        result.setTotal((int) page.getTotal());
        result.addAll(page.getRecords());
        long totalPage = page.getTotal() / page.getSize();
        if (page.getTotal() % page.getSize() != 0)
            totalPage++;
        result.setTotalPage((int) totalPage);
        return result;
    }
}
