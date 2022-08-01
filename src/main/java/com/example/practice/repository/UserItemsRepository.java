package com.example.practice.repository;

import com.example.practice.entity.UsersItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserItemsRepository extends JpaRepository<UsersItem, Long> {
}
