package com.fantasy;

//import com.baomidou.mybatisplus.generator.FastAutoGenerator;
//import com.baomidou.mybatisplus.generator.config.OutputFile;
//import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

@SpringBootTest
class FantasyBlogApplicationTests {

    @Test
    void contextLoads() {
    }

    /*
    @Test
    void createProject() {
        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/nblog",
                        "root", "1234")
                .globalConfig(builder -> {
                    builder.author("Fantasy0521") // 设置作者
                            //swagger 用于生成html格式的api文档
//                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D:\\代码生成器\\Fantasy-Blog"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com") // 设置父包名
                            .moduleName("fantasy") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D:\\代码生成器\\Fantasy-Blog")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
//                    builder.addInclude("t_simple") // 设置需要生成的表名
//                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                    //全部的表都生成
                    builder.likeTable(new LikeTable("%"));
                })
                //设置生成代码使用额 模板技术
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }*/


}
