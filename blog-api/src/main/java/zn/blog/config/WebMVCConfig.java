package zn.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //跨域配置，允许前端访问后端，配置前端的域名（域名=ip+端口）
        registry.addMapping("/**").allowedOrigins("http://localhost:8080");
    }
}
