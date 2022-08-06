package com.example.practice.repository;

import com.example.practice.entity.Possession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PossessionRepository extends JpaRepository<Possession, Long> {

    Possession findByUserIdAndItemId(Long userId, Long itemId);

    Possession findByItemId(Long itemId);

    List<Possession> findByUserId(Long userId);
}
