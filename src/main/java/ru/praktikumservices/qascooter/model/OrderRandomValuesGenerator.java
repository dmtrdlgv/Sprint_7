package ru.praktikumservices.qascooter.model;

import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class OrderRandomValuesGenerator {
    Faker faker = new Faker(Locale.forLanguageTag("ru"));

    public String generateRandomFirstname() {
        return faker.name().firstName();
    }

    public String generateRandomLastname() {
        return faker.name().lastName();
    }

    public String generateRandomAddress() {
        return faker.address().fullAddress().substring(7);
    }

    public String generateRandomPhone() {
        return faker.regexify("(8|\\+7)9\\d{9}");
    }

    public String generateRandomMetroStation() {
        return faker.regexify("[1-3]");
    }

    public String generateRandomComment() {
        return faker.harryPotter().character();
    }

    public String generateRandomDeliveryDate() {
        return new SimpleDateFormat("yyyy-MM-dd")
                .format(faker.date().future(1, TimeUnit.HOURS));
    }

    public String generateRandomRentTime() {
        return faker.regexify("[1-9]");
    }
}
