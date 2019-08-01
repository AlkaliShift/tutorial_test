package cn.shenghui.tutorial.service;

import cn.shenghui.tutorial.dao.mapper.FilterMapper;
import cn.shenghui.tutorial.dao.model.Good;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author shenghui
 * @version 1.0
 * @since 2019/7/31 13:43
 */
@Service
public class FilterService {
    private FilterMapper filterMapper;

    @Autowired
    public void setFilterMapper(FilterMapper filterMapper) {
        this.filterMapper = filterMapper;
    }

    /**
     * filter
     *
     * @param temp
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public JSONObject filter(String temp) {
        JSONObject response = new JSONObject();
        try {
            Good good = parse(temp);
            filterMapper.filter(good);
        } catch (Exception e) {
            response.put("content", temp);
            response.put("msg", e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            return response;
        }
    }

    private Good parse(String temp) throws Exception {
        Good good = new Good();
        String[] src = temp.trim().split(",");
        int length = src.length;
        good.setId(Integer.parseInt(src[0]));
        good.setName(length > 1 ? src[1] : null);
        good.setPrice(length > 2 ? new BigDecimal(src[2]) : null);
        good.setCurrency(length > 3 ? src[3] : null);
        good.setDescription(length > 4 ? src[4] : null);
        return good;
    }
}
