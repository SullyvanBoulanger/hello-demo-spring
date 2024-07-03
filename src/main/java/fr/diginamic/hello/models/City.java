package fr.diginamic.hello.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class City {
    private int id;
    private String name;
    private long numberInhabitants;
}
