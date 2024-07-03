package fr.diginamic.hello.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.hello.models.City;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/villes")
public class CityController {

    List<City> cities = new ArrayList<>(Arrays.asList(
        new City("Nice", 343_000), 
        new City("Carcassonne", 47_800), 
        new City("Narbonne", 53_400), 
        new City("Lyon", 484_000), 
        new City("Foix", 9_700), 
        new City("Pau", 77_200), 
        new City("Marseille", 850_700), 
        new City("Tarbes", 40_600)
    ));
    
    @GetMapping()
    public List<City> getCities() {
        return cities;
    }

    @PostMapping()
    public ResponseEntity<?> postCity(@RequestBody City city) {
        
        if(cities.stream().anyMatch(listCity -> listCity.getName().equals(city.getName())))
            return ResponseEntity.badRequest().body("La ville existe déjà");

        cities.add(city);
        return ResponseEntity.ok("Ville insérée avec succès");
    }
    
    
}
