package ru.netology.data;

import lombok.SneakyThrows;

import java.sql.DriverManager;

public class DataBaseManager {
    @SneakyThrows
    public static String[] getAmountAndStatusPaymentTransaction() {
        String[] amountAndStatusPaymentTransaction = null;
        String request = "SELECT amount, status FROM payment_entity ORDER BY created DESC LIMIT 1;";
        try (
                var conn = DriverManager
                        .getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
                var getAmountAndStatus = conn.prepareStatement(request)
        ) {
            try (var amountAndStatus = getAmountAndStatus.executeQuery()) {
                if (amountAndStatus.next()) {
                    amountAndStatusPaymentTransaction = new String[]{
                            amountAndStatus.getString("amount"),
                            amountAndStatus.getString("status")
                    };
                }
            }
        }
        return amountAndStatusPaymentTransaction;
    }

    @SneakyThrows
    public static String getStatusCreditRequest() {
        String statusCreditRequest = null;
        String request = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
        try (
                var conn = DriverManager
                        .getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
                var getStatus = conn.prepareStatement(request)
        ) {
            try (var status = getStatus.executeQuery()) {
                if (status.next()) {
                    statusCreditRequest = status.getString("status");
                }
            }
        }
        return statusCreditRequest;
    }
}
