package ru.netology;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.data.DataBaseManager;
import ru.netology.data.DataGenerator;
import ru.netology.data.DataHelper;
import ru.netology.page.PaymentPage;

import java.util.Locale;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class PaymentByCreditTest {

    private String formForCredit = "Кредит по данным карты";
    private String amountDB = "45000";
    private String statusApproveDB = "APPROVED";
    private String statusDeclineDB = "DECLINED";
    private static int lengthCardNumberAndHolderField = 19;
    private static int lengthMonthAndYearField = 2;
    private static int lengthCVVcodeField = 3;

    private static String[] invalidSymbolsTestDataSetForMonthAndYearField() {
        return DataGenerator.invalidSymbolsTestDataSet(lengthMonthAndYearField);
    }

    private static String[] invalidSymbolsTestDataSetForCardNumberField() {
        return DataGenerator.invalidSymbolsTestDataSet(lengthCardNumberAndHolderField);
    }

    private static String[] invalidSymbolsTestDataSetForCardHolderField() {
        return DataGenerator.invalidSymbolsTestDataSetForCardHolderField(lengthCardNumberAndHolderField);
    }

    private static String[] invalidFormatTestDataSetForCardHolderField() {
        return DataGenerator.invalidFormatTestDataSetForCardHolderField();
    }

    private static String[] invalidTestDataSetForCVVcodeField() {
        return DataGenerator.invalidSymbolsTestDataSet(lengthCVVcodeField);
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:8080");
    }

    @Test
    void shouldPayment() {
        PaymentPage page = new PaymentPage(formForCredit);
        page.approvedPaymentCard(DataHelper.getApprovedCardInfo());
        String[] expectedData = new String[]{amountDB, statusApproveDB};

        assertArrayEquals(expectedData, DataBaseManager.getAmountAndStatusPaymentTransaction());
    }

    @Test
    void shouldPaymentDeclineWithDeclinedCard() {
        PaymentPage page = new PaymentPage(formForCredit);
        page.declinedPaymentCard(DataHelper.getDeclinedCardInfo());
        String[] expectedData = new String[]{amountDB, statusDeclineDB};

        assertArrayEquals(expectedData, DataBaseManager.getAmountAndStatusPaymentTransaction());
    }

    @Test
    void shouldErrorMessageEmptyCardNumberField() {
        PaymentPage page = new PaymentPage(formForCredit);
        page.emptyErrorMessageForField(
                DataHelper.getCardNumberFieldName(),
                DataHelper.getApprovedCardInfoWithCustomField(DataHelper.getCardNumberFieldName(), "")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidSymbolsTestDataSetForCardNumberField")
    void shouldDoNotAllowInputCardNumberFieldInvalidValue(String value) {
        PaymentPage page = new PaymentPage(formForCredit);
        page.inputImpossibilityForField(DataHelper.getCardNumberFieldName(), value);
    }

    @Test
    void shouldErrorMessageCardNumberFieldOneZero() {
        PaymentPage page = new PaymentPage(formForCredit);
        page.invalidFormatMessageForField(
                DataHelper.getCardNumberFieldName(),
                DataHelper.getApprovedCardInfoWithCustomField(DataHelper.getCardNumberFieldName(), "0")
        );
    }

    @Test
    void shouldPaymentDeclineWithZeroValueInCardNumberField() {
        PaymentPage page = new PaymentPage(formForCredit);
        page.declinedPaymentCard(
                DataHelper.getApprovedCardInfoWithCustomField(
                        DataHelper.getCardNumberFieldName(),
                        "0000 0000 0000 0000"
                )
        );
    }

    @Test
    void shouldPaymentDeclineWithRandomCardNumber() {
        PaymentPage page = new PaymentPage(formForCredit);
        page.declinedPaymentCard(
                DataHelper.getApprovedCardInfoWithCustomField(
                        DataHelper.getCardNumberFieldName(),
                        DataGenerator.generateCardNumber()
                )
        );
    }

    @Test
    void shouldTrimOverLimitDigitsInCardNumberField() {
        PaymentPage page = new PaymentPage(formForCredit);
        page.trimInputValuesForField(
                DataHelper.getCardNumberFieldName(),
                DataGenerator.generateCardNumber() + DataGenerator.generateDigits(1)
        );
    }

    @Test
    void shouldErrorMessageEmptyMonthField() {
        PaymentPage page = new PaymentPage(formForCredit);
        page.emptyErrorMessageForField(
                DataHelper.getMonthFieldName(),
                DataHelper.getApprovedCardInfoWithCustomField(DataHelper.getMonthFieldName(), "")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidSymbolsTestDataSetForMonthAndYearField")
    void shouldDoNotAllowInputMonthFieldInvalidValue(String value) {
        PaymentPage page = new PaymentPage(formForCredit);
        page.inputImpossibilityForField(DataHelper.getMonthFieldName(), value);
    }

    @Test
    void shouldErrorMessageMonthFieldWithOneZero() {
        PaymentPage page = new PaymentPage(formForCredit);
        page.invalidFormatMessageForField(
                DataHelper.getMonthFieldName(),
                DataHelper.getApprovedCardInfoWithCustomField(DataHelper.getMonthFieldName(), "0")
        );
    }

    @ParameterizedTest
    @CsvSource(
            value = {
                    "00", "13"
            }
    )
    void shouldInvalidDataErrorMessageMonthFieldWithInvalidValue(String value) {
        PaymentPage page = new PaymentPage(formForCredit);
        page.invalidDataErrorMessageForMonthField(
                DataHelper.getApprovedCardInfoWithCustomField(DataHelper.getMonthFieldName(), value)
        );
    }

    @Test
    void shouldTrimOverLimitInMonthField() {
        PaymentPage page = new PaymentPage(formForCredit);
        page.trimInputValuesForField(
                DataHelper.getMonthFieldName(),
                DataGenerator.generateMonth() + DataGenerator.generateDigits(1)
        );
    }

    @Test
    void shouldEmptyErrorMessageYearField() {
        PaymentPage page = new PaymentPage(formForCredit);
        page.emptyErrorMessageForField(
                DataHelper.getYearFieldName(),
                DataHelper.getApprovedCardInfoWithCustomField(DataHelper.getYearFieldName(), "")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidSymbolsTestDataSetForMonthAndYearField")
    void shouldDoNotAllowInputYearFieldInvalidValue(String value) {
        PaymentPage page = new PaymentPage(formForCredit);
        page.inputImpossibilityForField(DataHelper.getYearFieldName(), value);
    }

    @Test
    void shouldErrorMessageYearFieldWithInvalidValue() {
        PaymentPage page = new PaymentPage(formForCredit);
        page.invalidFormatMessageForField(
                DataHelper.getYearFieldName(),
                DataHelper.getApprovedCardInfoWithCustomField(DataHelper.getYearFieldName(), "0")
        );
    }

    @Test
    void shouldExpiredMessageYearFieldWithZeroYear() {
        PaymentPage page = new PaymentPage(formForCredit);
        page.expiredErrorMessageForField(
                DataHelper.getYearFieldName(),
                DataHelper.getApprovedCardInfoWithCustomField(DataHelper.getYearFieldName(), "00")
        );
    }

    @Test
    void shouldTrimOverLimitInYearField() {
        PaymentPage page = new PaymentPage(formForCredit);
        page.trimInputValuesForField(
                DataHelper.getYearFieldName(),
                DataGenerator.generateYear() + DataGenerator.generateDigits(1)
        );
    }

    @Test
    void shouldExpiredMessageWithPastMonthInMonthField() {
        PaymentPage page = new PaymentPage(formForCredit);
        page.expiredErrorMessageForField(
                DataHelper.getMonthFieldName(),
                DataHelper.getApprovedCardInfoCustomDate(
                        1, DataGenerator.getPastTimeValue(),
                        0, DataGenerator.getCurrentTimeValue()
                )
        );
    }

    @Test
    void shouldExpiredMessageWithYearMonthInMonthField() {
        PaymentPage page = new PaymentPage(formForCredit);
        page.expiredErrorMessageForField(
                DataHelper.getYearFieldName(),
                DataHelper.getApprovedCardInfoCustomDate(
                        0, DataGenerator.getCurrentTimeValue(),
                        1, DataGenerator.getPastTimeValue()
                )
        );
    }

    @Test
    void shouldPaymentWithCurrentData() {
        PaymentPage page = new PaymentPage(formForCredit);
        page.approvedPaymentCard(
                DataHelper.getApprovedCardInfoCustomDate(
                        0, DataGenerator.getCurrentTimeValue(),
                        0, DataGenerator.getCurrentTimeValue()
                )
        );
    }

    @Test
    void shouldPaymentWithBigData() {
        PaymentPage page = new PaymentPage(formForCredit);
        page.approvedPaymentCard(
                DataHelper.getApprovedCardInfoWithCustomField(DataHelper.getYearFieldName(), "99")
        );
    }

    @Test
    void shouldErrorMessageEmptyCardHolderField() {
        PaymentPage page = new PaymentPage(formForCredit);
        page.emptyErrorMessageForField(
                DataHelper.getCardHolderFieldName(),
                DataHelper.getApprovedCardInfoWithCustomField(DataHelper.getCardHolderFieldName(), "")
        );
    }

    @Test
    void shouldPaymentWithComposeFirstNameCardHolderField() {
        PaymentPage page = new PaymentPage(formForCredit);
        page.approvedPaymentCard(
                DataHelper.getApprovedCardInfoWithCustomField(
                        DataHelper.getCardHolderFieldName(),
                        DataGenerator.generateCardHolderComposeFirstName("en", "-").toUpperCase()
                )
        );
    }

    @Test
    void shouldPaymentWithComposeLastNameCardHolderField() {
        PaymentPage page = new PaymentPage(formForCredit);
        page.approvedPaymentCard(
                DataHelper.getApprovedCardInfoWithCustomField(
                        DataHelper.getCardHolderFieldName(),
                        DataGenerator.generateCardHolderComposeLastName("en", "-").toUpperCase()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("invalidSymbolsTestDataSetForCardHolderField")
    void shouldInvalidFormatErrorMessageWithInvalidSymbolsCardHolderField(String value) {
        PaymentPage page = new PaymentPage(formForCredit);
        page.invalidFormatErrorMessageForCardHolderField(
                DataHelper.getApprovedCardInfoWithCustomField(DataHelper.getCardHolderFieldName(), value)
        );
    }

    @ParameterizedTest
    @MethodSource("invalidFormatTestDataSetForCardHolderField")
    void shouldInvalidFormatErrorMessageWithInvalidFormatCardHolderField(String value) {
        PaymentPage page = new PaymentPage(formForCredit);
        page.invalidFormatMessageForField(
                DataHelper.getCardHolderFieldName(),
                DataHelper.getApprovedCardInfoWithCustomField(
                        DataHelper.getCardHolderFieldName(), value
                )
        );
    }

    @Test
    void shouldTrimOverLimitInCardHolderField() {
        PaymentPage page = new PaymentPage(formForCredit);
        page.trimInputValuesForField(
                DataHelper.getCardHolderFieldName(),
                DataGenerator.generateCardHolder("en").toUpperCase() +
                        DataGenerator.generateSymbols(500, DataGenerator.getLatinValue()).toUpperCase()
        );
    }

    @Test
    void shouldEmptyErrorMessageCVVcodeField() {
        PaymentPage page = new PaymentPage(formForCredit);
        page.emptyErrorMessageForField(
                DataHelper.getCvvCodeFieldName(),
                DataHelper.getApprovedCardInfoWithCustomField(DataHelper.getCvvCodeFieldName(), "")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidTestDataSetForCVVcodeField")
    void shouldDoNotAllowInputCVVcodeFieldInvalidValue(String value) {
        PaymentPage page = new PaymentPage(formForCredit);
        page.inputImpossibilityForField(DataHelper.getCvvCodeFieldName(), value);
    }

    @ParameterizedTest
    @CsvSource(
            value = {
                    "0", "000"
            }
    )
    void shouldErrorMessageCVVcodeFieldWithInvalidValue(String value) {
        PaymentPage page = new PaymentPage(formForCredit);
        page.invalidFormatMessageForField(
                DataHelper.getCvvCodeFieldName(),
                DataHelper.getApprovedCardInfoWithCustomField(DataHelper.getCvvCodeFieldName(), value)
        );
    }

    @Test
    void shouldTrimOverLimitInCVVcodeField() {
        PaymentPage page = new PaymentPage(formForCredit);
        page.trimInputValuesForField(DataHelper.getCvvCodeFieldName(), DataGenerator.generateDigits(4));
    }
}
