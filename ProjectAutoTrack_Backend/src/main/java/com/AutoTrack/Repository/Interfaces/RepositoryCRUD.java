package com.AutoTrack.Repository.Interfaces;

import java.util.List;
import java.util.Optional;

public interface RepositoryCRUD<T, ID> {
    /**
     * Saves the given entity. If the entity is new, it will be inserted;
     * if it already exists, it will be updated.
     *
     * @param entity the entity to save
     * @return an {@code Optional} containing the saved entity, or empty if the operation failed
     *
     * @throws jakarta.validation.ValidationException if entity is invalid
     */
    Optional<T> save(T entity);

    /**
     * Update the given entity. If there is no entity to update it will be saved as a new entity
     *
     * @param entity the entity to save
     * @return an {@code Optional} containing the saved entity, or empty if the operation failed
     *
     * @throws jakarta.validation.ValidationException if entity is invalid
     */
    Optional<T> update(T entity);

    /**
     * Deletes the entity with the specified ID from the database.
     *
     * @param id the ID of the entity to be deleted
     * @return an {@code Optional} containing the deleted entity if found and removed, or {@code Optional.empty()} otherwise
     */
    Optional<T> deleteById(ID id);

    /**
     * Finds an entity by its ID.
     *
     * @param id the ID of the entity to find
     * @return the found entity, or {@code null} if no entity was found with the given ID
     */
    Optional<T> findById(ID id);

    /**
     * Retrieves all entities from the database.
     *
     * @return a list of all entities, or an empty list if none are found
     */
    List<T> findAll();


}

