package com.j2se.Thread;
/**
 *http://blog.qianxuefeng.com/category/javabasic
 */
public class Test001_LockTest {
	 /** 每个一秒打印一个递增的数字,打印4次 **/
    private static void print() {
        for (int i = 0; i < 4; i++){
            System.out.println(Thread.currentThread().getName() + "：" + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /** 静态方法锁1 **/
    private static synchronized void staticMethodLock1(){
        print();
    }

    /** 静态方法锁2 **/
    private static synchronized void staticMethodLock2(){
        print();
    }

    /** 非静态方法锁1 **/
    private synchronized void methodLock1(){
        print();
    }

    /** 非静态方法锁2 **/
    private synchronized void methodLock2(){
        print();
    }

    /** 对象锁1 **/
    public void objectLock1(){
        synchronized(this) {
            print();
        }
    }

    /** 对象锁1 **/
    public void objectLock2(){
        synchronized(this) {
            print();
        }
    }

    /** 类锁1 **/
    public void classLock1(){
        synchronized(this.getClass()) {
            print();
        }
    }

    /** 类锁1 **/
    public void classLock2(){
        synchronized(this.getClass()) {
            print();
        }
    }

    public static void main(String[] args){
        final Test001_LockTest lock = new Test001_LockTest();

        // 方法锁：同一个类，不用方法.(这里的staticMethodLock1和staticMethodLock2都是类锁(静态方法锁就是类锁).肯定存在竞争关系.)
        // 结果：产生竞争
			/*
				线程1（静态方法锁1）：0
				线程1（静态方法锁1）：1
				线程1（静态方法锁1）：2
				线程1（静态方法锁1）：3
				线程2（静态方法锁2）：0
				线程2（静态方法锁2）：1
				线程2（静态方法锁2）：2
				线程2（静态方法锁2）：3
			*/
//        new Thread("线程1（静态方法锁1）"){
//            public void run(){
//                staticMethodLock1();
//            }
//        }.start();
//
//        new Thread("线程2（静态方法锁2）"){
//            public void run(){
//                staticMethodLock2();
//            }
//        }.start();

        // 类锁：两个对象，竞争同一个类，不同方法 
        // 两个线程请求同一个对象的同步方法. 都在竞争该对象的对象锁.
        // 结果：产生竞争
	          /*
	            	线程3（非静态方法锁1）：0
					线程3（非静态方法锁1）：1
					线程3（非静态方法锁1）：2
					线程3（非静态方法锁1）：3
					线程4（非静态方法锁2）：0
					线程4（非静态方法锁2）：1
					线程4（非静态方法锁2）：2
					线程4（非静态方法锁2）：3
	           */
        new Thread("线程3（非静态方法锁1）"){
            public void run(){
                lock.methodLock1();
            }
        }.start();

        new Thread("线程4（非静态方法锁2）"){
            public void run(){
                lock.methodLock2();
            }
        }.start();

        // 对象锁：竞争同一个类的两个不同的对象，同一个方法
        //(methodLock1和methodLock1为对象锁,不同的对象之间不竞争) 两个method中调用同一个static print方法.局部变量存在各自实例中也安全.
        // 结果：不竞争
//        new Thread("线程5（对象锁1）"){
//            public void run(){
//                new Test001_LockTest().objectLock1();
//            }
//        }.start();
//
//        new Thread("线程6（对象锁2）"){
//            public void run(){
//                new Test001_LockTest().objectLock1();
//            }
//        }.start();

        // 对象锁：竞争同一个对象，不同方法
        // 结果：产生竞争
//        new Thread("线程7（对象锁1）"){
//            public void run(){
//                lock.objectLock1();
//            }
//        }.start();
//
//        new Thread("线程8（对象锁2）"){
//            public void run(){
//                lock.objectLock2();
//            }
//        }.start();

        // 类锁：两个对象，竞争同一个类，不同方法
        // 结果：产生竞争
//        new Thread("线程9（类锁1）"){
//            public void run(){
//                new Test001_LockTest().classLock1();
//            }
//        }.start();
//
//        new Thread("线程10（类锁2）"){
//            public void run(){
//                new Test001_LockTest().classLock2();
//            }
//        }.start();

    }
}
