package com.blysin.leshop.common.mybatis.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 实现代码自动生成
 *
 * @author Blysin
 * @since 2017年6月26日14:23:43
 */
public class CodeGenerator {
    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(System.getProperty("user.dir" ) + "\\src\\main\\java" );//生成的代码放入项目路径中
        gc.setFileOverride(true);//覆盖文件
        gc.setActiveRecord(false);
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(false);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        gc.setAuthor("代码生成器" );
        gc.setOpen(false);//生成后打开文件夹

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sDao" );
        gc.setXmlName("%sDao" );
        gc.setServiceName("%sService" );
        gc.setServiceImplName("%sServiceImpl" );
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);

        //jdbc配置文件
//        Properties jdbc = new Properties();
//        try (InputStream in = ClassLoader.getSystemResourceAsStream("jdbc.properties" )) {
//            jdbc.load(in);
//
//            dsc.setDriverName(jdbc.getProperty("jdbc.driverClassName" ));
//            dsc.setUsername(jdbc.getProperty("jdbc.username" ));
//            dsc.setPassword(jdbc.getProperty("jdbc.password" ));
//            dsc.setUrl(jdbc.getProperty("jdbc.url" ));
//
//        } catch (IOException e) {
//            System.out.println("jdbc配置文件读取错误" );
//        }

        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        dsc.setUrl("jdbc:mysql://localhost:3306/le38?useUnicode=true&characterEncoding=utf-8&useSSL=true");

        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略

        String tables = JOptionPane.showInputDialog("请输入表名称，多张表以分号‘；’分隔" );
        String[] tableAttr = tables.trim().split(";" );
        JOptionPane.showMessageDialog(null, "共" + tableAttr.length + "张表" , "将生成的表" , JOptionPane.INFORMATION_MESSAGE);

        strategy.setInclude(tableAttr); // 需要生成的表
        // strategy.setExclude(new String[]{"test"}); // 排除生成的表
        // 自定义实体父类
        // strategy.setSuperEntityClass("com.baomidou.demo.TestEntity");
        // 自定义 dao 父类
        strategy.setSuperMapperClass("com.blysin.leshop.common.mybatis.MyBaseMapper" );
        // 自定义 service 父类
        strategy.setSuperServiceClass("com.blysin.leshop.common.mybatis.BaseService" );
        // 自定义 service 实现类父类
        strategy.setSuperServiceImplClass("com.blysin.leshop.common.mybatis.BaseServiceImpl" );
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.blysin.leshop.common.mybatis" );
        pc.setModuleName("generator" );
        pc.setEntity("domain" );
        pc.setMapper("dao" );
        mpg.setPackageInfo(pc);

        TemplateConfig tc = new TemplateConfig();
        tc.setEntity("/templates/domain.java.vm" );//自定义javaBean模板
        tc.setMapper("/templates/dao.java.vm");
        mpg.setTemplate(tc);

        // 执行生成
        mpg.execute();
        JOptionPane.showMessageDialog(null, "生成成功" );
    }
}
