package com.example.user.hob;

public class User {
    String name;
    String surname;
    int age;
    String city;
    String url;
    String username;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User(String name, String surname, int age, String city, String url, String username) {

        this.name = name;
        this.surname = surname;
        this.age = age;
        this.city = city;
        this.url = url;
        this.username = username;
    }
}
