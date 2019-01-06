package com.github.cyk;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

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
        //list.str
    }
}
