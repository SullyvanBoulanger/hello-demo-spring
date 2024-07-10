package fr.diginamic.hello;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import fr.diginamic.hello.entities.City;
import fr.diginamic.hello.entities.Department;
import fr.diginamic.hello.services.CityService;
import fr.diginamic.hello.services.DepartmentService;

@SpringBootApplication
public class FillDatabase implements
        CommandLineRunner {

    @Autowired
    private CityService cityService;

    @Autowired
    private DepartmentService departmentService;

    @Value("${database.initialization}")
    private boolean isDatabaseInit;

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(FillDatabase.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }

    @Override
    public void run(String... args) throws Exception {

        if (isDatabaseInit) {
            System.out.println("Database initialised");
            return;
        }

        Path path = Paths.get("./src/main/resources/recensement.csv");

        ArrayList<City> cities = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(path);
            lines.remove(0);

            lines.forEach(line -> {
                String[] splittedLine = line.split(";");

                String departmentCode = splittedLine[2].trim();
                Department department = departmentService.getById(departmentCode);
                if (department == null) {
                    department = new Department();
                    department.setCode(departmentCode);
                    departmentService.insertFromEntity(department);
                }

                City city = new City();
                city.setName(splittedLine[6]);
                city.setCode(splittedLine[5]);
                city.setNumberInhabitants(Integer.parseInt(splittedLine[9].replaceAll(" ", "")));
                city.setDepartment(department);
                cities.add(city);
            });

            List<City> sortedCities = new ArrayList<>(cities.stream()
                    .sorted(Comparator.comparingLong(City::getNumberInhabitants).reversed())
                    .limit(1000)
                    .toList());
                    
            sortedCities.forEach(cityService::insertFromEntity);
            
            System.out.println("Database filled");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
