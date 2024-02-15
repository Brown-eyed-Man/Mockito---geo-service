package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.netology.entity.Country;

class LocalizationServiceImplTest {
    LocalizationService localizationService = new LocalizationServiceImpl();

    @ParameterizedTest
    @CsvSource(value = {
            "RUSSIA, Добро пожаловать",
            "USA, Welcome",
            "GERMANY, Welcome",
            "BRAZIL, Welcome"
    })
    public void testReturningText(Country country, String expectedMessage) {
        String actualMessage = localizationService.locale(country);
        Assertions.assertEquals(expectedMessage, actualMessage);
    }
}