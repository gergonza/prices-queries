package com.product.price.query.repository;

import com.product.price.query.domain.entity.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueryRepository extends JpaRepository<Detail, Long> {}
