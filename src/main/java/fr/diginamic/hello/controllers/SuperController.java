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

import fr.diginamic.hello.services.SuperService;
import jakarta.validation.Valid;

public abstract class SuperController<T, U> {
    @Autowired
    private SuperService<T, U> service;

    protected String nonExistentMessage;

    public SuperController(String missingMessage) {
        super();
        this.nonExistentMessage = missingMessage;
    }

    @GetMapping()
    public List<T> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public T getById(@PathVariable int id) {
        return service.getById(id);
    }

    @PostMapping()
    public ResponseEntity<?> post(@Valid @RequestBody U dto, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().stream().map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining("\n")));
        }

        return ResponseEntity.ok(service.insert(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable int id, @Valid @RequestBody U dto,
            BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().stream().map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining("\n")));
        }

        List<T> entities = service.modify(id, dto);
        if (entities == null)
            return ResponseEntity.badRequest().body(nonExistentMessage);

        return ResponseEntity.ok(entities);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        List<T> entities = service.delete(id);

        if (entities == null)
            return ResponseEntity.badRequest().body(nonExistentMessage);

        return ResponseEntity.ok(entities);
    }
}
