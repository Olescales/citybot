package by.resliv.citybot.repository;

import by.resliv.citybot.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    Optional<City> findByName (String cityName);

    Optional<City> getCityByName (String cityName);

    boolean existsCityByName (String cityName);
}
