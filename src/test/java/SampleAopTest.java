import sample_aop.MathCalculator;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sample_aop.LoggerAOPConfig;

/**
 * ProjectName: spring_AOP
 * Package: PACKAGE_NAME
 * Description:
 *
 * @author a0909
 * @version v1.0
 * @create 2023/4/1 - 下午 09:07
 * @since JDK 1.8
 */
public class SampleAopTest {

    @Test
    public void test01(){
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(LoggerAOPConfig.class);
        MathCalculator bean = context.getBean(MathCalculator.class);
        int result = bean.div(10, 5);
        System.out.println(result);
    }

}
