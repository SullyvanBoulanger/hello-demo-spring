package fr.diginamic.hello.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.diginamic.hello.daos.CityDao;
import fr.diginamic.hello.dtos.CityDto;
import fr.diginamic.hello.entities.City;

@Service
public class CityService {
    @Autowired
    private CityDao cityDao;

    public List<City> getCities(){
        return cityDao.findAll();
    }

    public City getCityById(int id){
        return cityDao.findById(id);
    }

    public City getCityByName(String name){
        return cityDao.findByName(name);
    }

    public List<City> insertCity(CityDto cityDto){
        cityDao.insertCity(cityDto);

        return cityDao.findAll();
    }

    public List<City> modifyCity(int id, CityDto cityDto){
        if(!cityDao.updateCity(id, cityDto)){
            return null;
        }

        return cityDao.findAll();
    }

    public List<City> deleteCity(int id){
        if(!cityDao.deleteCity(id)){
            return null;
        }

        return cityDao.findAll();
    }
}
