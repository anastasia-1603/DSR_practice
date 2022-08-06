package com.example.practice.specifications;


import com.example.practice.dto.SearchUserDto;
import com.example.practice.entity.Category;
import com.example.practice.entity.User;
import org.apache.commons.lang3.StringUtils;
import com.example.practice.dto.SearchItemDto;
import com.example.practice.entity.Item;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;

public class ItemSpecification {

    public static Specification<Item> getItemSpecification(SearchItemDto itemCriteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(itemCriteria.getName())) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + itemCriteria.getName() + "%"));
            }
            if (StringUtils.isNotBlank(itemCriteria.getCategoryName())) {
                Join<Item, Category> categoryJoin = root.join("category");
                predicates.add(criteriaBuilder.like(categoryJoin.get("name"), "%" + itemCriteria.getCategoryName() + "%"));
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
