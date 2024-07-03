package fr.diginamic.hello.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.hello.models.City;


@RestController
@RequestMapping("/villes")
public class CityController {
    
    @GetMapping()
    public List<City> getCities() {
        return new ArrayList<>(Arrays.asList(
            new City("Nice", 343_000), 
            new City("Carcassonne", 47_800), 
            new City("Narbonne", 53_400), 
            new City("Lyon", 484_000), 
            new City("Foix", 9_700), 
            new City("Pau", 77_200), 
            new City("Marseille", 850_700), 
            new City("Tarbes", 40_600)
        ));
    }
    
}
