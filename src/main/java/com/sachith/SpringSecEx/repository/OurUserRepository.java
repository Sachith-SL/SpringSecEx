package com.sachith.SpringSecEx.repository;

import com.sachith.SpringSecEx.model.OurUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OurUserRepository extends JpaRepository<OurUser,Integer>{

    Optional<OurUser> findByEmail(String email);
}
