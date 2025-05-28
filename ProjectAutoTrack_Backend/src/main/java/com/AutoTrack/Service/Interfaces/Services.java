package com.AutoTrack.Service.Interfaces;

import com.AutoTrack.Domain.User;

import java.util.Optional;

public interface Services {

    Optional<User> findUserByUsername(String username);
}
