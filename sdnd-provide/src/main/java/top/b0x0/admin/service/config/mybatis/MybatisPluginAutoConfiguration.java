package top.b0x0.admin.service.config.mybatis;

import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author musui
 * @since 2021-04-02
 */
@Configuration
@ConditionalOnBean({SqlSessionFactory.class})
@AutoConfigureAfter({PageHelperAutoConfiguration.class})
public class MybatisPluginAutoConfiguration {
//public class MybatisInterceptorAutoConfiguration implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private List<SqlSessionFactory> sqlSessionFactoryList;

    @PostConstruct
    public void addMyInterceptor() {
        MybatisQueryPlugin interceptor = new MybatisQueryPlugin();

        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            sqlSessionFactory.getConfiguration().addInterceptor(interceptor);
        }

    }

//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        MybatisQueryInterceptor interceptor = new MybatisQueryInterceptor();
//
//        for (SqlSessionFactory factory : sqlSessionFactoryList) {
//            factory.getConfiguration().addInterceptor(interceptor);
//        }
//    }
}