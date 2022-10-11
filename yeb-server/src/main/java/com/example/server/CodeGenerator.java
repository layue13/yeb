package com.example.server;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Scanner;

/**
 * @author ppliang
 */

public class CodeGenerator {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入" + tip + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");
        AbstractTemplateEngine templateEngine = new FreemarkerTemplateEngine();
        templateEngine.templateFilePath("/templates/mapper.xml.ftl");

        FastAutoGenerator.create("jdbc:mysql://localhost:3306/yeb_2011?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia" +
                        "/Shanghai", "root", "123456")
                .globalConfig(builder -> {
                    builder.outputDir(projectPath + "/yeb-server/src/main/java")
                            .author("小红")
                            .disableOpenDir()
                            .enableSwagger()
                            .build();
                })
                .packageConfig(builder -> {
                    builder.parent("com.example.server")
                            .entity("pojo")
                            .mapper("mapper")
                            .service("service")
                            .serviceImpl("service.impl")
                            .controller("controller")
                            .build();
                })
                .injectionConfig(
                        builder -> {
                        }
                )
                .templateConfig(builder -> {
                    builder.xml(null);
                })
                .templateEngine(templateEngine)
                .strategyConfig(builder -> {
                    builder.addInclude(scanner("表名，多个英文逗号分割").split(","))
                            .addTablePrefix("t_")
                            .entityBuilder()
                            .naming(NamingStrategy.underline_to_camel)
                            .columnNaming(NamingStrategy.no_change)
                            .enableLombok()

                            .controllerBuilder()
                            .enableRestStyle()
                            .enableHyphenStyle()
                            .build();
                })
                .execute();
    }

}