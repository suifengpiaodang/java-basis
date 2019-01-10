package com.github.cyk.collection;

import com.github.cyk.annotation.FlightLog;
import com.github.cyk.model.User;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component
public class Stream {

    //构造流的几种常见方法

    @Test
    public  void genStream() {
        //String [] strArray = new String[] {"a", "b", "c"};
        // 1. Individual values
        //Stream stream = Stream.of("a", "b", "c");
        // 2. Arrays
        String[] strArray = new String[]{"a", "b", "c"};
        //stream = Stream.of(strArray);
        //stream = Arrays.stream(strArray);
        // 3. Collections
        List<String> list = Arrays.asList(strArray);
        list.stream().forEach(System.out::print);
    }

    @Test
    @FlightLog
    public void toUpper(){
        List<String> wordList = Lists.newLinkedList();
        wordList.add("q");
        wordList.add("d");
        wordList.add("f");
        List<String> words = wordList.stream().map(String::toUpperCase).collect(toList());

        /*List<Integer> nums = Arrays.asList(1, 2, 3, 4);
        List<Integer> squareNums = nums.stream().map(n -> n * n).collect(Collectors.toList());
        System.out.println(squareNums);*/


        java.util.stream.Stream inputStream = java.util.stream.Stream.of(Arrays.asList(1), Arrays.asList(2, 3), Arrays.asList(4, 5, 6));
        /*java.util.stream.Stream outputStream = inputStream.flatMap((childList) -> childList.Stra());
        System.out.println(outputStream.collect(toList()));*/

    }

    // Intermediate：
    // map (mapToInt, flatMap 等)、 filter、 distinct、 sorted、 peek、 limit、 skip、 parallel、 sequential、 unordered

    // Terminal：
    // forEach、 forEachOrdered、 toArray、 reduce、 collect、 min、 max、 count、 anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 iterator

    // Short-circuiting：
    // anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 limit


    public void testMap(){
        List<String> alpha = Arrays.asList("a", "b", "c", "d");
        List<String> afterAlpha = alpha.stream().map(String::toUpperCase).collect(toList());
        System.out.println(afterAlpha.toString());

        List<Integer> nums = Arrays.asList(1, 2, 3, 4);
        List<Integer> squareNums = nums.stream().map(n -> n * n).collect(toList());
        System.out.println(squareNums);

        List<User> userList = this.genUserList();
        List<String> afterUserList = userList.stream().map(User::getUserName).collect(toList());
        List<String> aftUserList = userList.stream().map((User u)-> u.getUserName()).collect(toList());
        System.out.println(afterUserList);
        System.out.println(aftUserList);

        String[] words = new String[]{"Hello","World"};
        List<String[]> a = Arrays.stream(words).map(word -> word.split("")).distinct().collect(toList());
        System.out.println(a.get(0).toString());
        System.out.println(a.get(1).toString());

        // 对象list 转换为 其他对象list
    }


    public void testFlatMap(){
        java.util.stream.Stream inputStream = java.util.stream.Stream.of(
                Arrays.asList(1),
                Arrays.asList(2, 3),
                Arrays.asList(4, 5, 6)
        );
        //List<Integer> integerList = inputStream.flatMap((childList) -> childList.stream()).collect(toList());
        // System.out.println(integerList);



        String[] words = new String[]{"Hello","World"};
        List<String> a = Arrays.stream(words)
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(toList());
        a.forEach(System.out::print);

    }

    public void testFilter(){
        List<User> userList = this.genUserList();
        List<User> afterUserList =  userList.stream().filter((User u)->u.getUserName().length()>5).collect(toList());
        System.out.println(afterUserList);


        Integer[] sixNums = {1, 2, 3, 4, 5, 6};
        Integer[] evens = java.util.stream.Stream.of(sixNums).filter(n -> n%2 == 0).toArray(Integer[]::new);
        System.out.println(evens.toString());
    }

    //forEach 消费后不存在
    public void testForEach(){
        List<User> userList = this.genUserList();
        userList.stream().forEach(user -> System.out.println(user.getUserName()));
    }

    //findFirst findAny
    public void testFindFirst(){
        List<User> userList = this.genUserList();
        Optional<User> optionalUser =userList.stream().findFirst();
        Optional.ofNullable(optionalUser).ifPresent(System.out::println);
    }

    public void testReduce(){
        // 字符串连接，concat = "ABCD"
        String concat = java.util.stream.Stream.of("A", "B", "C", "D").reduce("", String::concat);
        System.out.println(concat);
        // 求最小值，minValue = -3.0
        double minValue = java.util.stream.Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MAX_VALUE, Double::min);
        System.out.println(minValue);
        // 求和，sumValue = 10, 有起始值
        int sumValue = java.util.stream.Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);
        System.out.println(sumValue);
        // 求和，sumValue = 10, 无起始值
        sumValue = java.util.stream.Stream.of(1, 2, 3, 4).reduce(Integer::sum).get();
        System.out.println(sumValue);
        // 过滤，字符串连接，concat = "ace"
        concat = java.util.stream.Stream.of("a", "B", "c", "D", "e", "F").
                filter(x -> x.compareTo("Z") > 0).
                reduce("", String::concat);

        System.out.println(concat);
    }

    public void testLimitAndSkip() {
        List<User> userList = new ArrayList();
        for (int i = 1; i <= 100; i++) {
            User user = new User("name" + i);
            userList.add(user);
        }
        List<String> personList2 = userList.stream().
                map(User::getUserName).limit(10).skip(3).collect(toList());
        System.out.println(personList2);
    }



    public void testMatch() {
        List<User> userList = new ArrayList();
        userList.add(new User("name" + 1, 10));

        userList.add(new User("name" + 2, 21));
        userList.add(new User("name" + 3, 34));
        userList.add(new User("name" + 4, 6));
        userList.add(new User("name" + 5, 55));
        boolean isAllAdult = userList.stream().
                allMatch(p -> p.getAge() > 18);
        System.out.println("All are adult? " + isAllAdult);
        boolean isThereAnyChild = userList.stream().
                anyMatch(p -> p.getAge() < 12);
        System.out.println("Any child? " + isThereAnyChild);
    }


    // Stream.generate

    // Stream.iterate

    // groupingBy/partitioningBy




    private List<User> genUserList(){
        List<User> userList = Lists.newLinkedList();
        User user1 =User.builder().address("bj").age(10).userName("zhangsan").build();
        User user2 =User.builder().address("sh").age(10).userName("lisi").build();
        User user3 =User.builder().address("gz").age(10).userName("wangwu").build();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        return userList;
    }




    public static void main(String[] args) {
        Stream testStream = new Stream();
        //testStream.testMap();
        //testStream.testFlatMap();
        //testStream.testFilter();
        //testStream.testForEach();
        //testStream.testFindFirst();
        //testStream.testReduce();
        //testStream.testLimitAndSkip();
        testStream.testMatch();
    }


}
