package fr.diginamic.hello.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import fr.diginamic.hello.exceptions.BadRequestException;
import fr.diginamic.hello.services.SuperService;
import jakarta.validation.Valid;

public abstract class SuperController<S, T, U> {
    @Autowired
    private SuperService<S, T, U> service;

    @Autowired
    private JpaRepository<T, S> repository;

    protected String nonExistentMessage;

    public SuperController(String missingMessage) {
        super();
        this.nonExistentMessage = missingMessage;
    }

    @GetMapping()
    public List<T> getAll(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);

        return repository.findAll(pageable).toList();
    }

    @GetMapping("/{id}")
    public T getById(@PathVariable S id) {
        return service.getById(id);
    }

    @PostMapping()
    public ResponseEntity<?> post(@Valid @RequestBody U dto, BindingResult result) throws BadRequestException {

        checkErrors(result);

        return ResponseEntity.ok(service.insertFromDto(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable S id, @Valid @RequestBody U dto,
            BindingResult result) throws BadRequestException {

        checkErrors(result);

        List<T> entities = service.modify(id, dto);
        if (entities == null)
            return ResponseEntity.badRequest().body(nonExistentMessage);

        return ResponseEntity.ok(entities);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable S id) {
        List<T> entities = service.delete(id);

        if (entities == null)
            return ResponseEntity.badRequest().body(nonExistentMessage);

        return ResponseEntity.ok(entities);
    }

    protected void checkErrors(BindingResult result) throws BadRequestException {
        if (result.hasErrors()) {
            throw new BadRequestException(result.getAllErrors().stream().map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining("\n")));
        }
    }

    @ExceptionHandler(BadRequestException.class)
    private ResponseEntity<String> badRequestExceptionHandler(BadRequestException badRequestException) {
        return ResponseEntity.badRequest().body((badRequestException.getMessage()));
    }
}
