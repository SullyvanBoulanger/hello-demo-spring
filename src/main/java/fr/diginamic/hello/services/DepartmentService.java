package fr.diginamic.hello.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.diginamic.hello.daos.DepartmentDao;
import fr.diginamic.hello.dtos.DepartmentDto;
import fr.diginamic.hello.entities.Department;

@Service
public class DepartmentService extends SuperService<Department, DepartmentDto> {

    @Autowired
    private DepartmentDao departmentDao;

    public Department getByName(String name) {
        return departmentDao.findByName(name);
    }
}
