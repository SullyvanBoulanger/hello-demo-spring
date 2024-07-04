package fr.diginamic.hello.daos;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.diginamic.hello.dtos.DepartmentDto;
import fr.diginamic.hello.entities.City;
import fr.diginamic.hello.entities.Department;
import jakarta.persistence.TypedQuery;

@Repository
public class DepartmentDao extends SuperDao<Department, DepartmentDto> {

    @Autowired
    private CityDao cityDao;

    @Override
    public List<Department> findAll() {
        TypedQuery<Department> query = entityManager.createQuery("SELECT d FROM Department d", Department.class);
        return query.getResultList();
    }

    @Override
    public Department findById(int id) {
        return entityManager.find(Department.class, id);
    }

    public Department findByName(String name) {
        TypedQuery<Department> query = entityManager.createQuery("SELECT d FROM Department d WHERE d.name LIKE :name",
                Department.class);
        query.setParameter("name", name);

        try {
            return query.getResultList().getFirst();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Transactional
    public void insert(DepartmentDto departmentDto) {
        Department department = new Department();
        department.setName(departmentDto.getName());
        bindCityToDepartment(department, departmentDto.getCitiesIds());

        entityManager.persist(department);
    }

    @Override
    @Transactional
    public boolean update(int id, DepartmentDto departmentDto) {
        Department department = findById(id);

        if (department == null)
            return false;

        department.setName(departmentDto.getName());
        bindCityToDepartment(department, departmentDto.getCitiesIds());

        return true;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        Department department = findById(id);

        if (department == null)
            return false;

        entityManager.remove(department);

        return true;
    }

    @Transactional
    private void bindCityToDepartment(Department department, int[] citiesIds) {
        Set<City> cities = new HashSet<>();

        for (int id : citiesIds) {
            City city = cityDao.findById(id);

            if (city != null) {
                cityDao.updateDepartment(id, department);
                cities.add(city);
            }
        }

        department.setCities(cities);
    }
}
