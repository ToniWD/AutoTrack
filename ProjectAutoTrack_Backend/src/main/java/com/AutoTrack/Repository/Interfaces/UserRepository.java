package com.AutoTrack.Repository.Interfaces;

import com.AutoTrack.Domain.User;

import java.util.Optional;

public interface UserRepository extends RepositoryCRUD<User, Long>{

    /**
     * Return the user with the specified username
     *
     * @return {@code Optional} of user if there is a user with this username,
     *                          or an {@code Optional.empty()}
     * */
    Optional<User> findByUsername(String username);

    /**
     * Saves the given entity. If the entity is new, it will be inserted;
     * if it already exists, it will be updated.
     * Entity should have a raw password, this method will encode the password.
     * @param entity the entity to save
     * @return an {@code Optional} containing the saved entity, or empty if the operation failed
     *
     * @throws jakarta.validation.ValidationException if entity is invalid
     */
    @Override
    Optional<User> save(User entity);
}
