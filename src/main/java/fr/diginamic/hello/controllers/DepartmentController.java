package fr.diginamic.hello.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.hello.dtos.DepartmentDto;
import fr.diginamic.hello.entities.Department;
import fr.diginamic.hello.services.DepartmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/departements")
public class DepartmentController extends SuperController<String, Department, DepartmentDto> {

    @Autowired
    private DepartmentService departmentService;

    public DepartmentController() {
        super("Le département n'existe pas");
    }

    @GetMapping("/{id}/grandes-villes")
    public ResponseEntity<?> getBigCities(@PathVariable int id, @RequestParam int limit) {

        return ResponseEntity.ok(departmentService.getBigCities(id, limit));
    }
}
