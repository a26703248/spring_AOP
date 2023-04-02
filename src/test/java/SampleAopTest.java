import sample_aop.MathCalculator;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sample_aop.LoggerAOPConfig;

/**
 * ProjectName: spring_AOP
 * Package: PACKAGE_NAME
 * Description:
 *      Spring 代理
 *      1. JDK Dynamic Proxy (若有實作界面則會使用)
 *      2. cglib (default)
 *
 *      @EnableAspectJAutoProxy 註冊生成器
 *      1. 透過 @EnableAspectJAutoProxy 內的 @Import(AspectJAutoProxyRegistrar.class)
 *         將 AspectJAutoProxyRegistrar.class 物件註冊進 IoC 容器中
 *      2. AspectJAutoProxyRegistrar 物件中包含呼叫 AopConfigUtils 中的
 *         registerAspectJAnnotationAutoProxyCreatorIfNecessary 方法
 *         並由 registerOrEscalateApcAsRequired 將 internalAutoProxyCreator
 *         物件註冊進入 IoC 容器
 *      補充:
 *          自動代理生成器可分為
 *          1. BeanNameAutoProxyCreator: 基於Bean 名稱的自動代理生成器
 *          2. DefaultAdvisorAutoProxyCreator: 基於 Advisor 的自動代理生成器，會針對容器中 Advisor
 *             進行掃描
 *          3. AspectJ: 基於 AspectJ 註解的自動代理生成器
 *
 *      AnnotationAwareAspectJAutoProxyCreator 生成器執行時機
 *      1. AnnotationAwareAspectJAutoProxyCreator 也屬於 BeanPostProcessor 及 Aware 一種
 *         所以可以透過容器中攔截所有 Bean 的創建過程狀態
 *      2. AnnotationAwareAspectJAutoProxyCreator 並透過 InstantiationAwareBeanPostProcessor
 *         攔截 Bean 建立過程，與 BeanPostProcessor 的差別在於 BeanPostProcessor 是 Bean以建立後再
 *         調用後置處理器，而 InstantiationAwareBeanPostProcessor 是在建立 Bean 前嘗試使用後置處理器
 *
 *      AnnotationAwareAspectJAutoProxyCreator 建立切面 Bean 流程
 *      postProcessorBeforeInstantiation()
 *      1. 建立 Bean 過程中會先判斷是否為 AdvisedBeans(保存了所有需要增強(切面) Bean) 中
 *      2. 判斷為是切面 Bean 則需實作或繼承，Advice、Pointcut、Advisor、AopInfrastructureBean 介面
 *         ，或是 @Aspect 註解物件，若都不是則跳過
 *      3. 若是為切面 Bean 則會進入判斷是否為 AspectJPointcutAdvisor 等需類型的切面
 *      postProcessorBeforeInstantiation()
 *      1. 透過 warpIfNecessary() 方法判斷是否須為切面目標
 *          a. 若為切面目標則會尋找對應的切面物件(通知方法物件)並依照順序排序(Order介面)
 *          b. 若接收到切面物件(通知方法物件)不為空則對當前 Bean 建立代理物件
 *      2. 最終將 cglib 增強 Bean 建立至 IoC 容器中，當使用目標方法則會呼叫切面方法
 *
 *      切面目標 Bean 方法執行流程
 *      1. 當目標方法執行時，將會被 CglibAopProxy 中 interceptor() 方法攔截
 *      2. interceptor() 會先取得攔截器鏈(@Before相關方法)，若無則直接呼叫方法
 *      3. 若有攔截器鏈則會透過建立 CglibMethodInvocation 並傳入目標物件及攔截
 *         器鏈
 *      4. CglibMethodInvocation 建立時返回 ReflectiveMethodInvocation 物件並呼叫
 *         process() 方法
 *      5. process 方法中會計算 interceptors 陣列長度，並鏈式呼叫方式依序執行切面方法及
 *         目標方法
 *
 *      攔截器鏈 (MethodInterceptor)
 *      1. 透過 ProxyFactory 獲得目標方法攔截器鏈，並加入 interceptorList 中
 *         ，攔截器集合 interceptorList 長度為 5(1個預設 ExposeInvocationInterceptor
 *         ，4個自訂)
 *      2. 判斷是哪種類型 Advisor 轉型為 Interceptor 類型， 通過 Interceptor 適配器的方
 *         式轉換成對應的 MethodInterceptor 物件並存至 interceptors 陣列中
 *
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
        System.out.println(bean.getClass());
        int result = bean.div(10, 5);
        System.out.println(result);
    }

}
