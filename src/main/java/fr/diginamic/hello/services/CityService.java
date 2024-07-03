package fr.diginamic.hello.services;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.diginamic.hello.entities.City;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Service
public class CityService {
    @PersistenceContext
    private EntityManager entityManager;

    public List<City> findAll(){
        TypedQuery<City> query = entityManager.createQuery("SELECT c FROM City c", City.class);
        return query.getResultList();
    }
}
