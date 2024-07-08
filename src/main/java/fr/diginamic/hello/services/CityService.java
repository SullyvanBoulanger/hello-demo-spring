package fr.diginamic.hello.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.diginamic.hello.daos.CityDao;
import fr.diginamic.hello.dtos.CityDtoFromFront;
import fr.diginamic.hello.entities.City;

@Service
public class CityService extends SuperService<Integer, City, CityDtoFromFront> {
    @Autowired
    private CityDao cityDao;

    public City getByName(String name) {
        return cityDao.findByName(name);
    }
}
