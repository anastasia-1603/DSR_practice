package com.example.practice.repository;

import com.example.practice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(
            value = """
                        with recursive q as (
                            select * from category c where name = (:name)
                            union all
                            select category.* from category, q where category.parent_category_id = q.id
                        )
                        select * from q;
            """,
            nativeQuery = true)
    List<Category> getChildCategories(@Param("name") String name);

    @Query(
            value = """
                        with recursive q as (
                            select * from category c where id = (:id)
                            union all
                            select category.* from category, q where category.parent_category_id = q.id
                        )
                        select * from q;
            """,
            nativeQuery = true)
    List<Category> getChildCategories(@Param("id") Long id);

    List<Category> getCategoriesByParentCategoryId(@Param("parent_category_id") Long parentCategoryId);

    boolean existsByName(@Param("name") String name);

    Category findByName(@Param("name") String name);
}
