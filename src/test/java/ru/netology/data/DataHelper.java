package ru.netology.data;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import lombok.Data;
import lombok.Value;


import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class DataHelper {

    private DataHelper() {

    }

    private static String approvedCard = "1111 2222 3333 4444";
    private static String declinedCard = "5555 6666 7777 8888";
    private static String validMonth = DataGenerator.generateMonth();
    private static String validYear = DataGenerator.generateYear(2);
    private static String validCardHolder = DataGenerator.generateCardHolder("en").toUpperCase();
    private static String validCVV = DataGenerator.generateDigits(3);
    private static final String cardNumberFieldName = "cardNumber";
    private static final String monthFieldName = "month";
    private static final String yearFieldName = "year";
    private static final String cardHolderFieldName = "cardHolder";
    private static final String cvvCodeFieldName = "CVV";

    public static String getCardNumberFieldName() {
        return cardNumberFieldName;
    }

    public static String getMonthFieldName() {
        return monthFieldName;
    }

    public static String getYearFieldName() {
        return yearFieldName;
    }

    public static String getCardHolderFieldName() {
        return cardHolderFieldName;
    }

    public static String getCvvCodeFieldName() {
        return cvvCodeFieldName;
    }

    @Value
    public static class CardInfo {
        private String cardNumber;
        private String month;
        private String year;
        private String cardHolder;
        private String cvvCode;
    }

    public static CardInfo getApprovedCardInfo() {
        return new CardInfo(approvedCard, validMonth, validYear, validCardHolder, validCVV);
    }

    public static CardInfo getDeclinedCardInfo() {
        return new CardInfo(declinedCard, validMonth, validYear, validCardHolder, validCVV);
    }

    public static CardInfo getCustomCardInfo
            (String cardNumber, String month, String year, String cardHolder, String cvvCode) {
        return new CardInfo(cardNumber, month, year, cardHolder, cvvCode);
    }

    public static CardInfo getApprovedCardInfoCustomDate
            (int shiftMonth, String timeForMonth, int shiftYear, String timeForYear) {
        String customMonth = DataGenerator.generateMonth(shiftMonth, timeForMonth);
        String customYear = DataGenerator.generateYear(shiftYear, timeForYear);
        return new CardInfo(approvedCard, customMonth, customYear, validCardHolder, validCVV);
    }

    private static CardInfo getApprovedCardInfoCheckFieldCardNumber(String value) {
        return new CardInfo(value, validMonth, validYear, validCardHolder, validCVV);
    }

    private static CardInfo getApprovedCardInfoCheckFieldMonth(String value) {
        return new CardInfo(approvedCard, value, validYear, validCardHolder, validCVV);
    }

    private static CardInfo getApprovedCardInfoCheckFieldYear(String value) {
        return new CardInfo(approvedCard, validMonth, value, validCardHolder, validCVV);
    }

    private static CardInfo getApprovedCardInfoCheckFieldCardHolder(String value) {
        return new CardInfo(approvedCard, validMonth, validYear, value, validCVV);
    }

    private static CardInfo getApprovedCardInfoCheckFieldCVVcode(String value) {
        return new CardInfo(approvedCard, validMonth, validYear, validCardHolder, value);
    }

    public static CardInfo getApprovedCardInfoWithCustomField(String field, String value) {
        switch (field) {
            case (cardNumberFieldName):
                return getApprovedCardInfoCheckFieldCardNumber(value);
            case (monthFieldName):
                return getApprovedCardInfoCheckFieldMonth(value);
            case (yearFieldName):
                return getApprovedCardInfoCheckFieldYear(value);
            case (cardHolderFieldName):
                return getApprovedCardInfoCheckFieldCardHolder(value);
            case (cvvCodeFieldName):
                return getApprovedCardInfoCheckFieldCVVcode(value);
            default:
                return getApprovedCardInfo();
        }
    }
}
