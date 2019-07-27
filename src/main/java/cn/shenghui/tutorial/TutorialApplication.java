package cn.shenghui.tutorial;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @version 1.0
 * @author shenghui
 * @since 2019/7/25 22:23
 */
@SpringBootApplication
@MapperScan(basePackages = "cn.shenghui.tutorial.dao.mapper")
@EnableTransactionManagement
public class TutorialApplication {
    public static void main(String[] args){
        SpringApplication.run(TutorialApplication.class, args);
    }
}
