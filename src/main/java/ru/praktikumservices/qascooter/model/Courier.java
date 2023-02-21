package ru.praktikumservices.qascooter.model;

import org.apache.commons.lang3.RandomStringUtils;

//Данные для авторизации курьера
public class Courier {

    private String login;
    private String password;
    private String firstname;

    public Courier(String login, String password, String firstname) {
        this.login = login;
        this.password = password;
        this.firstname = firstname;
    }

    //пустой конструктор для сериализации gson
    public Courier() {
    }

    public Courier(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void generateRandomLogin(int length){
        this.login = RandomStringUtils.randomAlphanumeric(length);
    }

    public void generateRandomPassword(int length) {
        this.password = RandomStringUtils.randomAlphanumeric(length);
    }

    public void generateRandomFirstname(int minLength, int maxLength) {
        this.firstname = RandomStringUtils.randomAlphabetic(minLength,maxLength);
    }
}
