package com.ada_avanada.project_one.repository;

import com.ada_avanada.project_one.dto.SearchDTO;
import com.ada_avanada.project_one.entity.Product;
import com.ada_avanada.project_one.entity.QProduct;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductFilterRepository extends QuerydslRepositorySupport {
    @PersistenceContext
    private EntityManager em;
    public ProductFilterRepository() {
        super(Product.class);
    }

    public List<Product> filter(SearchDTO search) {
        QProduct product = QProduct.product;
        List<Predicate> predicates = new ArrayList<>();
        if (search != null) {
            if (search.title() != null) {
                predicates.add(product.title.like("%" + search.title() + "%"));
            }
            if (search.category() != null) {
                predicates.add(product.category.likeIgnoreCase("%" + search.category() + "%"));
            }
            if (search.brand() != null) {
                predicates.add(product.brand.like("%" + search.brand() + "%"));
            }
        }

        return new JPAQueryFactory(em)
                .selectFrom(product)
                .where(predicates.toArray(new Predicate[0]))
                .fetch();
    }
}
