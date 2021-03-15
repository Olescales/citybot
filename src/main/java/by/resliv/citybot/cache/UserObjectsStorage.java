package by.resliv.citybot.cache;

import by.resliv.citybot.entity.City;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.WeakHashMap;

@Component
public class UserObjectsStorage {

    private Map<Integer, City> cities = new WeakHashMap<>();

    public City getCity(Integer userID) {
        return cities.get(userID);
    }

    public void putCity(Integer userID, City city) {
        cities.put(userID, city);
    }

    public void removeCityFromStorage(Integer userID) {
        cities.remove(userID);
    }
}
