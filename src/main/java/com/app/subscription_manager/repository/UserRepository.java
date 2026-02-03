package com.app.subscription_manager.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.app.subscription_manager.model.Users;

@Repository
public interface UserRepository extends MongoRepository<Users,String>  {
    
    Optional<Users> findByEmail(String email);
    Boolean existsByEmail(String email);

}
