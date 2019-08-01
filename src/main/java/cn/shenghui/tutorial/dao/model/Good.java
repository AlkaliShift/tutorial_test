package cn.shenghui.tutorial.dao.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author shenghui
 * @version 1.0
 * @since 2019/8/1 09:18
 */
@Data
public class Good {
    /**
     * goods' id
     */
    int id;

    /**
     * goods' name
     */
    String name;

    /**
     * goods' price
     */
    BigDecimal price;

    /**
     * goods' currency
     */
    String currency;

    /**
     * goods' description
     */
    String description;
}
