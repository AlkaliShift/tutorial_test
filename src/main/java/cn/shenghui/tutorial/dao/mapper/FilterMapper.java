package cn.shenghui.tutorial.dao.mapper;

import cn.shenghui.tutorial.dao.model.Good;
import org.mapstruct.Mapper;

/**
 * @author shenghui
 * @version 1.0
 * @since 2019/7/31 13:46
 */
@Mapper
public interface FilterMapper {
    /**
     * goods
     * @param good
     */
    void filter(Good good);
}
