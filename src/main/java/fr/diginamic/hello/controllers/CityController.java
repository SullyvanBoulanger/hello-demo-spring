package fr.diginamic.hello.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.hello.dtos.CityDto;
import fr.diginamic.hello.entities.City;

@RestController
@RequestMapping("/villes")
public class CityController extends SuperController<City, CityDto> {
    public CityController() {
        super("La ville n'existe pas");
    }
}
