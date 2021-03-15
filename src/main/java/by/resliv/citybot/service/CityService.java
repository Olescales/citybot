package by.resliv.citybot.service;

import by.resliv.citybot.entity.City;
import by.resliv.citybot.exceptions.NoSuchCityException;
import by.resliv.citybot.repository.CityRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static by.resliv.citybot.AppConstants.NO_SUCH_CITY;

@Slf4j
@Service
@AllArgsConstructor
public class CityService implements ICityService {

    private CityRepository cityRepository;

    @Override
    public City getCity(String cityName) {
        return cityRepository.findByName(cityName)
                .orElseThrow(() -> new NoSuchCityException(NO_SUCH_CITY));
    }

    @Override
    public List<City> getCities() {
        return cityRepository.findAll();
    }

    @Override
    public void saveCity(City city) {
        cityRepository.save(city);
    }

    @Override
    public void updateCity(City city) {
        String cityName = city.getName();
        Optional<City> cityForUpdate = cityRepository.getCityByName(cityName);
        if (cityForUpdate.isEmpty()) {
            throw new NoSuchCityException(NO_SUCH_CITY);
        }
        cityForUpdate.get().setName(city.getName());
        cityForUpdate.get().setRecommendations(city.getRecommendations());
        cityRepository.save(cityForUpdate.get());
    }

    @Override
    public void deleteCity(String cityName) {
        Optional<City> optionalCity = cityRepository.getCityByName(cityName);
        if (optionalCity.isEmpty()) {
            throw new NoSuchCityException(NO_SUCH_CITY);
        }
        cityRepository.delete(optionalCity.get());
    }
}