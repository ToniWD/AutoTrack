package com.AutoTrack.Repository.RepositoryDB;

import com.AutoTrack.Domain.Person;
import com.AutoTrack.Repository.Interfaces.PersonRepository;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PersonDBRepository extends AbsRepositoryCRUD<Person, Long> implements PersonRepository {
    @Autowired
    public PersonDBRepository(EntityManagerFactory emf) {
        super(emf, Person.class);
    }
}
