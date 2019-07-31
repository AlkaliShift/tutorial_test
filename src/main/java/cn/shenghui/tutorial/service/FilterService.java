package cn.shenghui.tutorial.service;

import cn.shenghui.tutorial.dao.mapper.FilterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public JSONObject filter(String[] temp) throws JSONException {
        JSONObject response = new JSONObject();
        try {
            filterMapper.filter(temp);
        } catch (RuntimeException e) {
            response.put("id", temp[0]);
            response.put("msg", e.getMessage());
            return response;
        }
        return null;
    }
}
