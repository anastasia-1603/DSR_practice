package com.example.practice.repository;

import com.example.practice.entity.Category;
import com.example.practice.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {

    List<Item> findAllByCategory(Category category);

    @Override
    @EntityGraph(attributePaths = {"category", "user"})
    Page<Item> findAll(Pageable pageable);
}
