package com.j2se.Thread;
/**
 *http://blog.qianxuefeng.com/category/javabasic
 *通过本例子演示类锁/对象锁的使用:
 *1 类锁只是一个虚拟的概念,表示锁定类的静态资源.jvm中的class类可以有多个实例对象,但只有一个class类.
 *	所以类锁只有一个,而类的每个对象都有自己的对象锁. 两种锁互不干扰.
 *2 演示synchronized的使用:锁要么锁类要么锁实例，方法锁的话静态方法就是锁类，非静态就是锁实例
 *	synchronized(this)或修饰非静态方法就是锁实例(对象锁)
 *  synchronized(this.getClass())或者synchronized(类的静态变量)或者修饰静态方法.则是锁定类.
 */
public class Test001_LockTest {
	private String str1="1";
	private static String str2="2";
	private Integer i=0;
	
    public void test1(){
        synchronized(str1) {
            print2();
        }
    }
    public void test2(){
        synchronized(str2) {
            print2();
        }
    }
    public void test3(){
        synchronized(i) {
            print2();
        }
    }
	 /** 每个一秒打印一个递增的数字,打印4次 **/
    private static void print2() {
        for (int i = 0; i < 4; i++){
            System.out.println(Thread.currentThread().getName() + "：" + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
	
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

        // 方法锁：同一个类，不用方法.
        //(这里的staticMethodLock1和staticMethodLock2都是类锁(静态方法锁就是类锁).肯定存在竞争关系.)
        // JVM中只有一个类而可以有多个实例对象
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
//        new Thread("线程3（非静态方法锁1）"){
//            public void run(){
//                lock.methodLock1();
//            }
//        }.start();
//
//        new Thread("线程4（非静态方法锁2）"){
//            public void run(){
//                lock.methodLock2();
//            }
//        }.start();

        // 对象锁：竞争同一个类的两个不同的对象，同一个方法
        //(调用了两次new Test001_LockTest()初始化了2个对象实例) 两个对象锁之间互不干扰.不竞争.
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
        //注意 虽然是两个对象但是他们的方法都锁定的是 synchronized(this.getClass())即类锁.所以竞争
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

      //两个对象调用各自的相同方法,objectLock1中锁的是synchronized(this) 即当前对象锁
      //所以对象锁,不竞争
//      new Thread("线程11（对象锁1）"){
//	      public void run(){
//	          new Test001_LockTest().objectLock1();
//	      }
//	  }.start();
//	
//	  new Thread("线程12（对象锁2）"){
//	      public void run(){
//	          new Test001_LockTest().objectLock1();
//	      }
//	  }.start();
     
      //不同对象调用各自的方法, 锁定的是synchronized(str1) 即private String str1="1" 类变量
      //从效果看,应该是获取的是类锁. 争夺类变量
      /*
       * 	线程13（非静态方法锁2）：0
			线程13（非静态方法锁2）：1
			线程13（非静态方法锁2）：2
			线程13（非静态方法锁2）：3
			线程14（非静态方法锁2）：0
			线程14（非静态方法锁2）：1
			线程14（非静态方法锁2）：2
			线程14（非静态方法锁2）：3
       */
//       final Test001_LockTest test1 = new Test001_LockTest();
//	      new Thread("线程13（非静态方法锁1）"){
//	      public void run(){
//	    	  test1.test1();
//	      }
//	  }.start();
//	  
//	  final Test001_LockTest test2 = new Test001_LockTest();
//      new Thread("线程14（非静态方法锁1）"){
//	      public void run(){
//	    	  test2.test1();
//	      }
//	  }.start();

       //和上面例子类似.执行效果也一样. 唯一不同的是抢夺的是类变量的类型从String 改为Integer.说明类变量的同步都是类锁
       //争夺类锁:
       /*
        * 执行效果:
        * 	线程13（非静态方法锁1）：0
			线程13（非静态方法锁1）：1
			线程13（非静态方法锁1）：2
			线程13（非静态方法锁1）：3
			线程14（非静态方法锁1）：0
			线程14（非静态方法锁1）：1
			线程14（非静态方法锁1）：2
			线程14（非静态方法锁1）：3
        */
        final Test001_LockTest test1 = new Test001_LockTest();
 	      new Thread("线程13（非静态方法锁1）"){
 	      public void run(){
 	    	  test1.test3();
 	      }
 	  }.start();
 	  
 	  final Test001_LockTest test2 = new Test001_LockTest();
       new Thread("线程14（非静态方法锁1）"){
 	      public void run(){
 	    	  test2.test3();
 	      }
 	  }.start();
        

    }
}
