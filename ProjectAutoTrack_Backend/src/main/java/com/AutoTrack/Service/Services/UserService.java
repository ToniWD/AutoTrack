package com.AutoTrack.Service.Services;

import com.AutoTrack.Domain.User;
import com.AutoTrack.Repository.Interfaces.RepositoryCRUD;
import com.AutoTrack.Repository.Interfaces.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService extends AbsCrudService<User, Long> {

    private UserRepository repo;

    public UserService(UserRepository repo) {
        super(repo);
        this.repo = repo;
    }

    public Optional<User> findUserByUsername(String username) {
        return repo.findByUsername(username);
    }
}
