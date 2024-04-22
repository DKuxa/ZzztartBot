package org.kuxa.zzztart.repository;

import org.kuxa.zzztart.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}

