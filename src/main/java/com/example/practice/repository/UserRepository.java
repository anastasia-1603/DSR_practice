package com.example.practice.repository;

import com.example.practice.entity.Item;
import com.example.practice.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;

public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    @NonNull
    Page<User> findAll(@NonNull Pageable pageable);

    boolean existsByEmail(String email);
}
