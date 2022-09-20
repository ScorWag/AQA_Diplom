package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataGenerator;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class PaymentPage {
    private ElementsCollection headers = $$("h3");
    private SelenideElement cardNumberInput =
            $x("//*[text()='Номер карты']//parent::*[@class='input__inner']//input");
    private SelenideElement monthInput =
            $x("//*[text()='Месяц']//parent::*[@class='input__inner']//input");
    private SelenideElement yearInput =
            $x("//*[text()='Год']//parent::*[@class='input__inner']//input");
    private SelenideElement cardHolderInput =
            $x("//*[text()='Владелец']//parent::*[@class='input__inner']//input");
    private SelenideElement codeInput =
            $x("//*[text()='CVC/CVV']//parent::*[@class='input__inner']//input");
    private SelenideElement buttonOpenFormPayment =
            $x("//*[text()='Купить']//parent::*[@class='button__content']//parent::button");
    private SelenideElement buttonOpenFormCredit =
            $x("//*[text()='Купить в кредит']//parent::*[@class='button__content']//parent::button");
    private SelenideElement buttonConfirm =
            $x("//*[text()='Продолжить']//parent::*[@class='button__content']//parent::button");
    private SelenideElement notificationOk = $(".notification_status_ok .notification__content");
    private SelenideElement notificationError = $(".notification_status_error .notification__content");
    private SelenideElement cardNumberField =
            $x("//*[text()='Номер карты']/following-sibling::*[@class='input__sub']");
    private SelenideElement monthField =
            $x("//*[text()='Месяц']/following-sibling::*[@class='input__sub']");
    private SelenideElement yearField =
            $x("//*[text()='Год']/following-sibling::*[@class='input__sub']");
    private SelenideElement сardHolderField =
            $x("//*[text()='Владелец']/following-sibling::*[@class='input__sub']");
    private SelenideElement cvvCodeField =
            $x("//*[text()='CVC/CVV']/following-sibling::*[@class='input__sub']");
    private String[] errorMessages = new String[]{
            "Неверный формат",
            "Поле обязательно для заполнения",
            "Истёк срок действия карты",
            "Неверно указан срок действия карты",
            "Поле может содержать только латинские буквы в верхнем регистре, дефисы и пробелы"
    };

    public PaymentPage(String form) {
        switch (form) {
            case ("Оплата по карте"):
                this.buttonOpenFormPayment.click();
                headers.get(1).shouldHave(exactText(form));
                break;
            case ("Кредит по данным карты"):
                buttonOpenFormCredit.click();
                headers.get(1).shouldHave(exactText(form));
                break;
        }
    }

    private void sendCompletedForm(DataHelper.CardInfo info) {
        cardNumberInput.setValue(info.getCardNumber());
        monthInput.setValue(info.getMonth());
        yearInput.setValue(info.getYear());
        cardHolderInput.setValue(info.getCardHolder());
        codeInput.setValue(info.getCvvCode());
        buttonConfirm.click();
    }

    public void approvedPaymentCard(DataHelper.CardInfo info) {
        sendCompletedForm(info);
        notificationOk.shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Операция одобрена Банком."));
    }

    public void declinedPaymentCard(DataHelper.CardInfo info) {
        sendCompletedForm(info);
        notificationError.shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Ошибка! Банк отказал в проведении операции."));
    }

    public void inputImpossibilityForField(String fieldName, String value) {
        if (fieldName.equals(DataHelper.getCardNumberFieldName())) {
            cardNumberInput.setValue(value).shouldBe(empty);
            return;
        }
        if (fieldName.equals(DataHelper.getMonthFieldName())) {
            monthInput.setValue(value).shouldBe(empty);
            return;
        }
        if (fieldName.equals(DataHelper.getYearFieldName())) {
            yearInput.setValue(value).shouldBe(empty);
            return;
        }
        if (fieldName.equals(DataHelper.getCardHolderFieldName())) {
            cardHolderInput.setValue(value).shouldBe(empty);
            return;
        }
        if (fieldName.equals(DataHelper.getCvvCodeFieldName())) {
            codeInput.setValue(value).shouldBe(empty);
        }
    }

    public void invalidFormatMessageForField(String checkedField, DataHelper.CardInfo info) {
        sendCompletedForm(info);
        if (checkedField.equals(DataHelper.getCardNumberFieldName())) {
            cardNumberField.shouldBe(visible).shouldBe(exactText(errorMessages[0]));
            return;
        }
        if (checkedField.equals(DataHelper.getMonthFieldName())) {
            monthField.shouldBe().shouldBe(exactText(errorMessages[0]));
            return;
        }
        if (checkedField.equals(DataHelper.getYearFieldName())) {
            yearField.shouldBe(visible).shouldBe(exactText(errorMessages[0]));
            return;
        }
        if (checkedField.equals(DataHelper.getCardHolderFieldName())) {
            сardHolderField.shouldBe().shouldBe(exactText(errorMessages[0]));
            return;
        }
        if (checkedField.equals(DataHelper.getCvvCodeFieldName())) {
            cvvCodeField.shouldBe().shouldBe(exactText(errorMessages[0]));
        }
    }

    public void emptyErrorMessageForField(String checkedField, DataHelper.CardInfo info) {
        sendCompletedForm(info);
        if (checkedField.equals(DataHelper.getCardNumberFieldName())) {
            cardNumberField.shouldBe(visible).shouldBe(exactText(errorMessages[1]));
            return;
        }
        if (checkedField.equals(DataHelper.getMonthFieldName())) {
            monthField.shouldBe(visible).shouldBe(exactText(errorMessages[1]));
            return;
        }
        if (checkedField.equals(DataHelper.getYearFieldName())) {
            yearField.shouldBe(visible).shouldBe(exactText(errorMessages[1]));
            return;
        }
        if (checkedField.equals(DataHelper.getCardHolderFieldName())) {
            сardHolderField.shouldBe(visible).shouldBe(exactText(errorMessages[1]));
            return;
        }
        if (checkedField.equals(DataHelper.getCvvCodeFieldName())) {
            cvvCodeField.shouldBe(visible).shouldBe(exactText(errorMessages[1]));
        }
    }

    public void expiredErrorMessageForField(String field, DataHelper.CardInfo info) {
        sendCompletedForm(info);
        if (field.equals(DataHelper.getMonthFieldName())) {
            monthField.shouldBe(visible).shouldHave(exactText(errorMessages[2]));
            return;
        }
        if (field.equals(DataHelper.getYearFieldName())) {
            yearField.shouldBe(visible).shouldHave(exactText(errorMessages[2]));
        }
    }

    public void invalidDataErrorMessageForMonthField(DataHelper.CardInfo info) {
        sendCompletedForm(info);
        monthField.shouldBe(visible).shouldHave(exactText(errorMessages[3]));
    }

    public void invalidFormatErrorMessageForCardHolderField(DataHelper.CardInfo info) {
        sendCompletedForm(info);
        сardHolderField.shouldBe(visible).shouldHave(exactText(errorMessages[4]));
    }

    public void trimInputValuesForField(String field, String value) {

        if (field.equals(DataHelper.getCardNumberFieldName())) {
            cardNumberInput.setValue(value).shouldHave(exactValue(value.substring(0, 19)));
            return;
        }
        if (field.equals(DataHelper.getMonthFieldName())) {
            monthInput.setValue(value).shouldHave(exactValue(value.substring(0, 2)));
            return;
        }
        if (field.equals(DataHelper.getYearFieldName())) {
            yearInput.setValue(value).shouldHave(exactValue(value.substring(0, 2)));
            return;
        }
        if (field.equals(DataHelper.getCardHolderFieldName())) {
            cardHolderInput.setValue(value).shouldHave(exactValue(value.substring(0, 254)));
            return;
        }
        if (field.equals(DataHelper.getCvvCodeFieldName())) {
            codeInput.setValue(value).shouldHave(exactValue(value.substring(0, 3)));
        }
    }
}
