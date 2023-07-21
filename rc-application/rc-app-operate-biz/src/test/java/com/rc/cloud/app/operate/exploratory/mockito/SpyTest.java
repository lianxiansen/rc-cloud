package com.rc.cloud.app.operate.exploratory.mockito;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.doNothing;

/**
 * @ClassName: SpyTest
 * @Author: liandy
 * @Date: 2023/6/28 10:51
 * @Description:
 * MOCK:
 * 对该对象所有非私有方法的调用都没有调用真实方法
 * 对该对象私有方法的调用无法进行模拟，会调用真实方法
 *
 * SPY:
 * 对该对象所有方法的调用都直接调用真实方法
 */
@RunWith(SpringRunner.class)
public class SpyTest {
    @Spy
    private TestSubject testSubject;

    @Mock
    private TestSubject mock;


    @Test
    public void testSpy(){
        doNothing().when(testSubject).methodA();
        testSubject.methodB();
    }
    @Test
    public void testMock(){
        doNothing().when(mock).methodA();
        mock.methodB();
    }
}
@Component
class TestSubject{
    public void methodA(){
        throw new RuntimeException();
    }

    public void methodB(){
        System.out.println("methodB begin");
        methodA();
        methodC();
        System.out.println("methodB end");
    }

    public void methodC(){
        System.out.println("methodC");
    }
}
