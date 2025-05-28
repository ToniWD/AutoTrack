package com.AutoTrack.Service.Services;

import com.AutoTrack.Repository.Interfaces.RepositoryCRUD;
import com.AutoTrack.Repository.Interfaces.VehicleRepository;
import com.AutoTrack.Service.Interfaces.CRUDServices;
import com.AutoTrack.Service.ServiceException;
import jakarta.validation.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public abstract class AbsCrudService<E, ID> implements CRUDServices<E, ID> {

    private static final Logger logger = LogManager.getLogger();
    private final RepositoryCRUD<E,ID> repo;

    public AbsCrudService(RepositoryCRUD<E, ID> repo) {
        this.repo = repo;
    }

    @Override
    public Optional<E> save(E entity) {
        if(entity == null) throw new ServiceException("Entity is null");

        try {
            return repo.save(entity);
        }
        catch (ValidationException e)
        {
            logger.error("Invalid entity: " + entity + "; error: " + e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        catch (Exception e) {
            logger.error(e);
            throw new ServiceException("There is a problem");
        }
    }

    @Override
    public Optional<E> update(E entity) {
        if(entity == null) throw new ServiceException("Entity is null");

        try {
            return repo.update(entity);
        }
        catch (ValidationException e)
        {
            logger.error("Invalid entity: " + entity + "; error: " + e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        catch (Exception e) {
            logger.error(e);
            throw new ServiceException("There is a problem");
        }
    }

    @Override
    public Optional<E> deleteById(ID id) {
        if(id == null) throw new ServiceException("ID is null");

        try {
            return repo.deleteById(id);
        }
        catch (Exception e) {
            logger.error(e);
            throw new ServiceException("There is a problem");
        }
    }

    @Override
    public Optional<E> findById(ID id) {
        return repo.findById(id);
    }

    @Override
    public List<E> findAll() {
        return repo.findAll();
    }
}
