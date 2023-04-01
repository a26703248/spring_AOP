package sample_aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * ProjectName: spring_AOP
 * Package: aop_demo1
 * Description:
 *
 * @author a0909
 * @version v1.0
 * @create 2023/4/1 - 下午 08:52
 * @since JDK 1.8
 */
@Aspect // 此物件為切面類別
public class LoggerAspects {

    @Pointcut("execution(public int sample_aop.MathCalculator.*(..))")
    public void pointcutOne(){};

    @Before("pointcutOne()")
    public void logStart(JoinPoint joinPoint){
        System.out.println("除法 start, ");
        System.out.println("method: " + joinPoint.getSignature().getName());
        System.out.println("args: " + joinPoint.getArgs());
        System.out.println("modifiers: " + joinPoint.getSignature().getModifiers());
    }

    @After("pointcutOne()")
    public void logEnd(JoinPoint joinPoint){
        System.out.println("除法 end, ");
        System.out.println("method: " + joinPoint.getSignature().getName());
    }

    // JoinPoint 參數必須再傳入參數個第一個位置
    @AfterReturning(value = "pointcutOne()", returning = "result")
    public void logReturn(JoinPoint joinPoint, Object result){
        System.out.println("除法 Return, ");
        System.out.println("result" + result);
    }

    @AfterThrowing(value = "pointcutOne()", throwing = "exception")
    public void logThrows(Exception exception){
        System.out.println("除法 Throws, ");
        exception.printStackTrace();
    }

}
