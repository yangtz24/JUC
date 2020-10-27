package com.ytz.thread.juc;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName: LockDemo
 * @Description: 锁范围
 * 1.标准访问------->sendMail()/sendSms()
 * 2.sendMail暂停4秒---------->sendMail()/sendSms()
 * 3.新增非同步方法------------->say()/sendMail()
 * 4.两个phone对象sendMail暂停4秒------------>sendSms()/sendMail()
 * 5.两个静态方法，同一个Phone对象------------>sendMail()/sendSms()
 * 6.两个静态方法，两个Phone对象------------->sendMail()/sendSms()
 * 7.一个静态，一个普通方法，同一个Phone对象------------->sendSms()/sendMail()
 * 8.一个静态，一个普通方法，两个Phone对象--------------->sendSms()/sendMail()
 * <p>
 * 锁的范围：
 * this(当前对象)：普通同步方法
 * Class(类上)：静态同步方法
 * 同步代码块：括号里的配置对象
 * @author: yangtianzeng
 * @date: 2020/4/6 22:58
 */
public class LockDemo {

    public static void main(String[] args) throws InterruptedException {

        Phone phone = new Phone();
        Phone phone2 = new Phone();

        new Thread(() -> {
            try {
                phone.sendMail();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "A").start();

        TimeUnit.MILLISECONDS.sleep(100);

        new Thread(() -> {
            try {
                //phone.sendSms();
                phone2.sendSms();
                phone.say();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "B").start();
    }
}

class Phone {
    public static synchronized void sendMail() throws InterruptedException {
        TimeUnit.SECONDS.sleep(4L);
        System.out.println("sendMail()***************************");

    }

    public static synchronized void sendSms() {
        System.out.println("sendSms()***************************");
    }

    public void say() {
        System.out.println("say()***************************");
    }
}
