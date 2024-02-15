package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.netology.entity.*;

class GeoServiceImplTest {

    @ParameterizedTest
    @CsvSource(value = {
            "172.0.32.11, RUSSIA, Moscow, Lenina, 15",
            "96.12.61.777, USA, New York, , 0",
            "127.0.0.1, , , , 0",
            "96.44.183.149, USA, New York, 10th Avenue, 32",
            "172.666.666.666, RUSSIA, Moscow, , 0",
            "96.666.666.666, USA, New York, , 0",
    })
    public void testByIP(String ip, Country country, String city, String street, int building) {
        GeoService geoService = new GeoServiceImpl();
        Location actualLocation = geoService.byIp(ip);
        Assertions.assertEquals(country, actualLocation.getCountry());
        Assertions.assertEquals(city, actualLocation.getCity());
        Assertions.assertEquals(street, actualLocation.getStreet());
        Assertions.assertEquals(building, actualLocation.getBuiling());

    }

}