package cn.shenghui.tutorial.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author shenghui
 * @version 1.0
 * @since 2019/7/31 13:46
 */
@Mapper
public interface FilterMapper {
    /**
     * filter mapper
     * @param temp
     */

    void filter(@Param("temp")String[] temp);
}
