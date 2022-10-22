package com.example.practice.repository;

import com.example.practice.entity.Category;
import com.example.practice.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {

    List<Item> findAllByCategory(Category category);

    @Override
    @EntityGraph(attributePaths = {"category", "user"})
    @NonNull
    Page<Item> findAll(@NonNull Pageable pageable);
}
