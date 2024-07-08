package fr.diginamic.hello.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.hello.dtos.CityDtoForFront;
import fr.diginamic.hello.dtos.CityDtoFromFront;
import fr.diginamic.hello.dtos.mappers.CityDtoMapper;
import fr.diginamic.hello.entities.City;
import fr.diginamic.hello.repositories.CityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/villes")
public class CityController extends SuperController<Integer, City, CityDtoFromFront> {
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityDtoMapper cityDtoMapper;

    public CityController() {
        super("La ville n'existe pas");
    }

    @GetMapping("/test/{id}")
    public CityDtoForFront geCityDto(@PathVariable int id) {
        return cityDtoMapper.toDto(cityRepository.findById(id).orElse(null));
    }
    

    @GetMapping("/nom")
    public ResponseEntity<?> getCitiesStartingWith(@RequestParam String name) {
        return ResponseEntity.ok(cityRepository.findByNameStartingWith(name));
    }

    @GetMapping("/superieur")
    public ResponseEntity<?> getCitiesWithInhabitantsGreaterThan(@RequestParam int min) {
        return ResponseEntity.ok(cityRepository.findByNumberInhabitantsGreaterThan(min));
    }

    @GetMapping("/entre")
    public ResponseEntity<?> getCitiesWithInhabitantsBetween(@RequestParam int min, @RequestParam int max) {
        return ResponseEntity.ok(cityRepository.findByNumberInhabitantsBetween(min, max));
    }

    @GetMapping("/departement/{code}/superieur")
    public ResponseEntity<?> getCitiesByDepartmentWithInhabitantsGreaterThan(@PathVariable String code, @RequestParam int min) {
        return ResponseEntity.ok(cityRepository.findByNumberInhabitantsGreaterThanAndDepartmentCode(min, code));
    }

    @GetMapping("/departement/{code}/entre")
    public ResponseEntity<?> getCitiesByDepartmentWithInhabitantsBetween(@PathVariable String code, @RequestParam int min, @RequestParam int max) {
        return ResponseEntity.ok(cityRepository.findByNumberInhabitantsBetweenAndDepartmentCode(min, max, code));
    }

    @GetMapping("/departement/{code}/top")
    public ResponseEntity<?> getTopNCitiesFromDepartment(@PathVariable String code, @RequestParam int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return ResponseEntity.ok(cityRepository.findAllByDepartmentCodeOrderByNumberInhabitantsDesc(code, pageable));
    }
}
