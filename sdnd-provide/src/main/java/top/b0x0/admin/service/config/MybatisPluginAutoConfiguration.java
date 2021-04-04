package top.b0x0.admin.service.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author musui
 * @since 2021-04-02
 */
//@Component
@Configuration
@AutoConfigureAfter(PaginationInterceptor.class)
//@AutoConfigureAfter({PaginationInterceptor.class, PageBoundSqlInterceptors.class})
public class MybatisPluginAutoConfiguration {
//public class MybatisInterceptorAutoConfiguration implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private List<SqlSessionFactory> sqlSessionFactoryList;

    @PostConstruct
    public void addMyInterceptor() {
        MybatisParamQueryPlugin interceptor = new MybatisParamQueryPlugin();

        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            sqlSessionFactory.getConfiguration().addInterceptor(interceptor);
            System.out.println("sqlSessionFactory = " + sqlSessionFactory);
            System.out.println("Interceptors() = " + sqlSessionFactory.getConfiguration().getInterceptors());
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