package ml.ulinom.dorm.config;

import ml.ulinom.dorm.utils.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
/**
 * @ClassName InterceptorConfig
 * @Description TODO
 * @Author ZGD
 * @Date 2018/11/2913:33
 * @Version 1.0
 **/
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/","/pages/index.html");
            super.addInterceptors(registry);
        }
    }
