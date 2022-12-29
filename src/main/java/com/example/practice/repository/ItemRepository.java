package com.example.practice.repository;

import com.example.practice.entity.Category;
import com.example.practice.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {

    List<Item> findAllByCategory(Category category);

    List<Item> findAllByCategoryIn(List<Category> categories);

    Page<Item> findAllByCategoryIn(List<Category> categories, Pageable pageable);

    Page<Item> findAllByCategory(Category category, Pageable pageable);

    List<Item> findAllByName(String name);

//    Item findById(Long id);

    @Override
    @EntityGraph(attributePaths = {"category", "user"})
    @NonNull
    Page<Item> findAll(@NonNull Pageable pageable);

    @Query(value = "select * from item where name like %:keyword% or description like %:keyword%", nativeQuery = true)
    List<Item> findByKeyword(@Param("keyword") String keyword);

    List<Item> findAllByNameContainingIgnoreCase(String name);
}
