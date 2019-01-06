package com.github.cyk.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String userName;
    private Integer age;
    private String address;


    public User(String name){
        userName = name;
    }

    public User(String name,int a){
        userName = name;
        age = a;
    }

}
