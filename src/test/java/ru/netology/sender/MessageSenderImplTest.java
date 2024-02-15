package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.startsWith;

class MessageSenderImplTest {

    @ParameterizedTest
    @CsvSource(value = {
            "172.666.26.666, Добро пожаловать",
            "172.77.77.77, Добро пожаловать",
            "172.158.220.99, Добро пожаловать",
            "172.27.255.13, Добро пожаловать",
            "172.14.59.37, Добро пожаловать",
    })
    public void testRussianTextForRussianIP(String ip, String expectedMessage) {
        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);

        Mockito.when(geoService.byIp(startsWith("172."))).thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String actualMessage = messageSender.send(headers);

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "96.666.26.666, Welcome",
            "96.77.77.77, Welcome",
            "96.158.220.99, Welcome",
            "96.27.255.13, Welcome",
            "96.14.59.37, Welcome",
    })
    public void testAmericanTextForAmericanIP() {
        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);

        Mockito.when(geoService.byIp(startsWith("96."))).thenReturn(new Location("New York", Country.USA, null, 0));
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.677.666.99");
        String message = messageSender.send(headers);

        Assertions.assertEquals("Welcome", message);
    }

}