package fr.diginamic.hello.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.hello.dtos.CityDto;
import fr.diginamic.hello.entities.City;
import fr.diginamic.hello.services.CityService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/villes")
public class CityController {
    @Autowired
    private CityService cityService;

    @GetMapping()
    public List<City> getCities() {
        return cityService.getCities();
    }

    @GetMapping("/{id}")
    public City getCityById(@PathVariable int id) {
        return cityService.getCityById(id);
    }

    @PostMapping()
    public ResponseEntity<?> postCity(@Valid @RequestBody CityDto cityDto, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().stream().map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining("\n")));
        }

        return ResponseEntity.ok(cityService.insertCity(cityDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putCityById(@PathVariable int id, @Valid @RequestBody CityDto cityDto,
            BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().stream().map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining("\n")));
        }

        List<City> cities = cityService.modifyCity(id, cityDto);
        if (cities == null)
            return ResponseEntity.badRequest().body("La ville n'existe pas");

        return ResponseEntity.ok(cities);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable int id) {
        List<City> cities = cityService.deleteCity(id);

        if (cities == null)
            return ResponseEntity.badRequest().body("La ville n'existe pas");

        return ResponseEntity.ok(cities);
    }
}
