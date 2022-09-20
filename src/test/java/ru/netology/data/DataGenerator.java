package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Data;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    private static Faker faker = new Faker(new Locale("en"));
    private static final String cyrillicAlphabet = "АаБбВвГгДдЕеЖжЗзИиЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЪъЫыЬьЭэЮюЯя";
    private static final String latinAlphabet = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";
    private static final String specSymbols = "`~!@#№$;%^:?&*()_-=+[]{}<>,./|";
    private static final String cyrillicValue = "ru";
    private static final String latinValue = "en";
    private static final String specSymbolsOnlyValue = "special";
    private static final String specSymbolsAndCyrillicValue = "specialRu";
    private static final String specSymbolsAndLatinValue = "specialEn";
    private static final String futureTimeValue = "future";
    private static final String pastTimeValue = "past";
    private static final String currentTimeValue = "current";

    public static String getCyrillicValue() {
        return cyrillicValue;
    }

    public static String getLatinValue() {
        return latinValue;
    }

    public static String getSpecSymbolsOnlyValue() {
        return specSymbolsOnlyValue;
    }

    public static String getSpecSymbolsAndCyrillicValue() {
        return specSymbolsAndCyrillicValue;
    }

    public static String getSpecSymbolsAndLatinValue() {
        return specSymbolsAndLatinValue;
    }

    public static String getFutureTimeValue() {
        return futureTimeValue;
    }

    public static String getPastTimeValue() {
        return pastTimeValue;
    }

    public static String getCurrentTimeValue() {
        return currentTimeValue;
    }

    public static String generateCardNumber() {
        return faker.business().creditCardNumber().replaceAll("-", " ");
    }

    public static String generateCardHolder(String local) {
        Faker newFaker = new Faker(new Locale(local));
        String firstName = newFaker.name().firstName();
        String lastName = newFaker.name().lastName();
        return firstName + " " + lastName;
    }

    public static String generateCardHolderComposeFirstName(String local, String separator) {
        Faker newFaker = new Faker(new Locale(local));
        String firstName = newFaker.name().firstName();
        String fullName = generateCardHolder(local);

        return firstName + separator + fullName;
    }

    public static String generateCardHolderComposeLastName(String local, String separator) {
        Faker newFaker = new Faker(new Locale(local));
        String lastName = newFaker.name().lastName();
        String fullName = generateCardHolder(local);
        String cardHolderComposeLastName = fullName + separator + lastName;

        return cardHolderComposeLastName.toUpperCase();
    }

    public static String generateMonth(int shift, String time) {
        LocalDate date = LocalDate.now();
        String month = date.format(DateTimeFormatter.ofPattern("MM"));
        if (time.equals(futureTimeValue)) {
            month = date.plusMonths(shift).format(DateTimeFormatter.ofPattern("MM"));
        }
        if (time.equals(pastTimeValue)) {
            month = date.minusMonths(shift).format(DateTimeFormatter.ofPattern("MM"));
        }
        return month;
    }

    public static String generateMonth(int shift) {
        return LocalDate.now().plusMonths(shift).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String generateMonth() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String generateYear(int shift, String time) {
        LocalDate date = LocalDate.now();
        String year = date.format(DateTimeFormatter.ofPattern("yy"));
        if (time.equals(futureTimeValue)) {
            year = date.plusYears(shift).format(DateTimeFormatter.ofPattern("yy"));
        }
        if (time.equals(pastTimeValue)) {
            year = date.minusYears(shift).format(DateTimeFormatter.ofPattern("yy"));
        }
        return year;
    }

    public static String generateYear(int shift) {
        return LocalDate.now().plusYears(shift).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String generateYear() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String generateSymbols(int length, String type) {
        String symbols;
        switch (type) {
            case (cyrillicValue):
                symbols = cyrillicAlphabet;
                break;
            case (latinValue):
                symbols = latinAlphabet;
                break;
            case (specSymbolsOnlyValue):
                symbols = specSymbols;
                break;
            case (specSymbolsAndCyrillicValue):
                symbols = cyrillicAlphabet + specSymbols;
                break;
            case (specSymbolsAndLatinValue):
                symbols = latinAlphabet + specSymbols;
                break;
            default:
                symbols = cyrillicAlphabet + latinAlphabet + specSymbols;
                break;
        }
        Random rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int random = rnd.nextInt(symbols.length());
            char c = symbols.charAt(random);
            sb.append(c);
        }
        return sb.toString();
    }

    public static String generateSymbols(int length) {
        String symbols = cyrillicAlphabet + latinAlphabet + specSymbols;
        Random rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int random = rnd.nextInt(symbols.length());
            char c = symbols.charAt(random);
            sb.append(c);
        }
        return sb.toString();
    }

    public static String generateDigits(int length) {
        return faker.number().digits(length);
    }

    public static String[] invalidSymbolsTestDataSet(int length) {
        return new String[]{
                generateSymbols(length, cyrillicValue),
                generateSymbols(length, latinValue),
                generateSymbols(length, specSymbolsOnlyValue),
                generateSymbols(length, specSymbolsAndCyrillicValue),
                generateSymbols(length, specSymbolsAndLatinValue),
                generateSymbols(length)
        };
    }

    public static String[] invalidSymbolsTestDataSetForCardHolderField(int length) {
        return new String[]{
                generateCardHolder("ru").toUpperCase(),
                generateCardHolder("en").toLowerCase(),
                generateCardHolder("en"),
                "0",
                generateSymbols(
                        length,
                        specSymbolsOnlyValue
                ),
                generateSymbols(
                        length,
                        specSymbolsAndCyrillicValue
                ),
                generateSymbols(
                        length,
                        specSymbolsAndLatinValue
                ),
                generateSymbols(length),
                generateDigits(length)
        };
    }

    public static String[] invalidFormatTestDataSetForCardHolderField() {
        return new String[]{
                generateCardHolder("en").toUpperCase() + " ",
                " " + generateCardHolder("en").toUpperCase(),
                "   ",
                new Faker(new Locale("en")).name().firstName().toUpperCase()
        };
    }
}
