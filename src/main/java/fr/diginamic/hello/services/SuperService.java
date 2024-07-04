package fr.diginamic.hello.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.diginamic.hello.daos.SuperDao;

@Service
public abstract class SuperService<T, U> {
    @Autowired
    private SuperDao<T, U> dao;

    public List<T> getAll() {
        return dao.findAll();
    }

    public T getById(int id) {
        return dao.findById(id);
    }

    public List<T> insert(U dto) {
        dao.insert(dto);

        return dao.findAll();
    }

    public List<T> modify(int id, U dto) {
        if (!dao.update(id, dto)) {
            return null;
        }

        return dao.findAll();
    }

    public List<T> delete(int id) {
        if (!dao.delete(id)) {
            return null;
        }

        return dao.findAll();
    }
}
