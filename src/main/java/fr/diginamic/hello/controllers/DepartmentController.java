package fr.diginamic.hello.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.hello.dtos.DepartmentDto;
import fr.diginamic.hello.entities.Department;

@RestController
@RequestMapping("/departements")
public class DepartmentController extends SuperController<Department, DepartmentDto> {
    public DepartmentController() {
        super("Le département n'existe pas");
    }
}
