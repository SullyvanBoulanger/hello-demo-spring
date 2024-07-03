package fr.diginamic.hello.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.hello.entities.City;

@RestController
@RequestMapping("/villes")
public class CityController {

    List<City> cities = new ArrayList<>(Arrays.asList(
            new City(0, "Nice", 343_000),
            new City(1, "Carcassonne", 47_800),
            new City(2, "Narbonne", 53_400),
            new City(3, "Lyon", 484_000),
            new City(4, "Foix", 9_700),
            new City(5, "Pau", 77_200),
            new City(6, "Marseille", 850_700),
            new City(7, "Tarbes", 40_600)));

    @GetMapping()
    public List<City> getCities() {
        return cities;
    }

    @GetMapping("/{id}")
    public City getCityById(@PathVariable int id) {
        return cities.stream().filter(city -> city.getId() == id).findFirst().orElse(null);
    }

    @PostMapping()
    public ResponseEntity<?> postCity(@RequestBody City city) {

        if (cities.stream().anyMatch(listCity -> listCity.getId() == city.getId()))
            return ResponseEntity.badRequest().body("La ville existe déjà");

        cities.add(city);
        return ResponseEntity.ok("Ville insérée avec succès");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putCityById(@PathVariable int id, @RequestBody City city) {

        City cityToModify = cities.stream().filter(listCity -> listCity.getId() == id).findFirst().orElse(null);

        if (cityToModify == null)
            return ResponseEntity.badRequest().body("La ville n'existe pas");

        cityToModify.setName(city.getName());
        cityToModify.setNumberInhabitants(city.getNumberInhabitants());

        return ResponseEntity.ok("Ville modifiée avec succès");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable int id) {
        City cityToDelete = cities.stream().filter(listCity -> listCity.getId() == id).findFirst().orElse(null);

        if (cityToDelete == null)
            return ResponseEntity.badRequest().body("La ville n'existe pas");

        cities.remove(cityToDelete);

        return ResponseEntity.ok("Ville supprimée avec succès");
    }
}
