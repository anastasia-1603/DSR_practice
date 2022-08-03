package com.example.practice.repository;

import com.example.practice.dto.UsersItemDto;
import com.example.practice.dto.UsersItemKeeperDto;
import com.example.practice.entity.UsersItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsersItemRepository extends JpaRepository<UsersItem, Long> {

    UsersItem findByUserIdAndItemId(Long userId, Long itemId);
}
