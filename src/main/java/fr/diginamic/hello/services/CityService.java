package fr.diginamic.hello.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.diginamic.hello.daos.CityDao;
import fr.diginamic.hello.dtos.CityDto;
import fr.diginamic.hello.entities.City;

@Service
public class CityService extends SuperService<Integer, City, CityDto> {
    @Autowired
    private CityDao cityDao;

    public City getByName(String name) {
        return cityDao.findByName(name);
    }
}
