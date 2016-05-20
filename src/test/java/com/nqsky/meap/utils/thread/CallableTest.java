package com.nqsky.meap.utils.thread;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/***
Callable 和 Runnable 的使用方法大同小异， 区别在于：
1.Callable 使用 call（） 方法， Runnable 使用 run() 方法
2.call() 可以返回值， 而 run()方法不能返回。
3.call() 可以抛出受检查的异常，比如ClassNotFoundException， 而run()不能抛出受检查的异常。
注意ExecutorService 在Callable中使用的是submit()， 在Runnable中使用的是 execute() 
 * @author lrm
 *
 */
public class CallableTest {

	/**
	 * @param args
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		ExecutorService exec = Executors.newCachedThreadPool();  
        ArrayList<Future<String>> results = new ArrayList<Future<String>>();    //Future 相当于是用来存放Executor执行的结果的一种容器  
        for (int i = 0; i < 10; i++) {  
            results.add(exec.submit(new TaskWithResult(i)));  
        }  
        for (Future<String> fs : results) {  
            if (fs.isDone()) {  
                System.out.println(fs.get());  
            } else {  
                System.out.println("Future result is not yet complete");  
            }  
        }  
        exec.shutdown();
	}

}

class TaskWithResult implements Callable<String> {  
    private int id;  
  
    public TaskWithResult(int id) {  
        this.id = id;  
    }  
  
    public String call() throws Exception {  
        return "result of TaskWithResult " + id;  
    }  
}  
