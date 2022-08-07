package com.example.practice.specifications;


import com.example.practice.dto.PageableSearchItemDto;
import com.example.practice.dto.SearchUserDto;
import com.example.practice.entity.Category;
import com.example.practice.entity.Item;
import com.example.practice.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ItemSpecification {

    public static Specification<Item> getItemSpecification(PageableSearchItemDto itemCriteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(itemCriteria.getName())) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + itemCriteria.getName() + "%"));
            }
            if (StringUtils.isNotBlank(itemCriteria.getCategoryName())) {

                Join<Item, Category> categoryJoin = root.join("category");
                predicates.add(criteriaBuilder.isTrue(categoryJoin.get("id").in(itemCriteria.getChildCategoriesIds())));
            }
            if (itemCriteria.getUser() != null) {
                SearchUserDto user = itemCriteria.getUser();
                Join<Item, User> userJoin = root.join("user");
                if (StringUtils.isNotBlank(user.getName())) {
                    predicates.add(criteriaBuilder.equal(userJoin.get("name"), user.getName()));
                }
                if (StringUtils.isNotBlank(user.getSurname())) {
                    predicates.add(criteriaBuilder.equal(userJoin.get("surname"), user.getSurname()));
                }
                if (StringUtils.isNotBlank(user.getPatronymic())) {
                    predicates.add(criteriaBuilder.equal(userJoin.get("patronymic"), user.getPatronymic()));
                }
                if (StringUtils.isNotBlank(user.getEmail())) {
                    predicates.add(criteriaBuilder.equal(userJoin.get("email"), user.getEmail()));
                }
            }
            return query.where(predicates.toArray(new Predicate[0])).getRestriction();
        };
    }

}
