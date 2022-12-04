package com.product.price.query.repository;

import com.product.price.query.domain.entity.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository that enables a Database API with the Source Database.
 *
 * @author Germán González
 * @version 1.0
 * @since 2022-12-03
 *
 */
@Repository
public interface QueryRepository extends JpaRepository<Detail, Long> {}
