package by.resliv.citybot.service;

import by.resliv.citybot.entity.City;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ICityService {

    City getCity(String cityName);

    List<City> getCities();

    void saveCity(City city);

    void updateCity(City city);

    void deleteCity(String city);
}
