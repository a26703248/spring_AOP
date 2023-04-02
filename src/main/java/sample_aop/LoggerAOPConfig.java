package sample_aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * ProjectName: spring_AOP
 * Package: aop_demo1
 * Description:
 *
 * @author a0909
 * @version v1.0
 * @create 2023/4/1 - 下午 08:54
 * @since JDK 1.8
 * @EnableAspectJAutoProxy 原理
 * 1. 透過 @EnableAspectJAutoProxy 啟動 Aspects 自動代理
 * 2. 透過 @Import(AspectJAutoProxyRegistrar.class) 引入自動代理註冊器
 * 3. 透過 AspectJAutoProxyRegistrar 註冊器註冊自訂 Bean
 * 4. 註冊 InternalAutoProxyCreator=AnnotationAwareAspectJAutoProxyCreator.class
 * 5. 通過 AnnotationAwareAspectJAutoProxyCreator 將所有 Aspects 註冊製容器中
 */
@Configuration
@EnableAspectJAutoProxy // 啟動 Aspects 自動代理
public class LoggerAOPConfig {

    @Bean
    public MathCalculator mathCalculator(){
        return new MathCalculator();
    }

    @Bean
    public LoggerAspects loggerAspects(){
        return new LoggerAspects();
    }


}
