package com.example.practice.repository;

import com.example.practice.entity.Possession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PossessionRepository extends JpaRepository<Possession, Long> {

    Possession findByUserIdAndItemId(Long userId, Long itemId);
}
