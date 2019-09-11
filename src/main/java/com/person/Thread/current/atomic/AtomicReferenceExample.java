package com.person.Thread.current.atomic;

import com.person.Thread.current.User;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public class AtomicReferenceExample {

    public static AtomicReference<User> ref = new AtomicReference();
    public static AtomicReference<String> stringRef = new AtomicReference("apple");

    public static void main(String[] args) {
        User user1 = new User("小芳", 18);
        ref.set(user1);
        //通过getAndSet设置新值返回旧值
        User user = ref.getAndSet(new User("小明", 22));
        System.out.println(user);
        User user2 = ref.get();
        boolean newUser = ref.compareAndSet(user2, new User("二娃", 8));
        System.out.println(newUser);
        User newInstance = ref.get();
        //通过updateAndGet设置新值返回新值
        User user3 = ref.updateAndGet(new UnaryOperator<User>() {
            public User apply(User user) {
                //比较是否属于当前数值，然后返回新值
                if(newInstance.equals(user)){

                    return new User("三金", 13);
                }
                return null;
            }
        });
        System.out.println(user3);
        //通过accumulateAndGet进行累加操作
        User user4 = ref.accumulateAndGet(new User("四喜", 24), new BinaryOperator<User>() {
            @Override
            public User apply(User user, User user2) {
                if (user3.equals(user)) {
                    return user2;
                }
                return null;
            }
        });
        String strings ="";
        for(int i =0;i<2;i++){
           strings = stringRef.accumulateAndGet("string" + i, new BinaryOperator<String>() {
                @Override
                public String apply(String s, String s2) {
                    if (stringRef.get().equals(s)) {
                        return s += s2;
                    }
                    return "";
                }
            });

        }
        System.out.println(strings);
        System.out.println(user4);
    }

}
