package fr.diginamic.hello.daos;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.diginamic.hello.dtos.CityDto;
import fr.diginamic.hello.entities.City;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class CityDao {
    @PersistenceContext
    private EntityManager entityManager;

    public List<City> findAll(){
        TypedQuery<City> query = entityManager.createQuery("SELECT c FROM City c", City.class);
        return query.getResultList();
    }

    public City findById(int id){
        return entityManager.find(City.class, id);
    }

    public City findByName(String name){
        TypedQuery<City> query = entityManager.createQuery("SELECT c FROM City c WHERE c.name LIKE :name", City.class);
        query.setParameter("name", name);
        
        try {
            return query.getResultList().getFirst();
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public void insertCity(CityDto cityDto){
        City entityCity = new City();
        entityCity.setName(cityDto.getName());
        entityCity.setNumberInhabitants(cityDto.getNumberInhabitants());

        entityManager.persist(entityCity);
    }

    @Transactional
    public boolean updateCity(int id, CityDto cityDto){
        City city = findById(id);

        if(city == null)
            return false;

        city.setName(cityDto.getName());
        city.setNumberInhabitants(cityDto.getNumberInhabitants());
        
        return true;
    }

    @Transactional
    public boolean deleteCity(int id){
        City city = findById(id);

        if(city == null)
            return false;
        
        entityManager.remove(city);
        
        return true;
    }
}
