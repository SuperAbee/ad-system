package com.abee.ad.dao;

import com.abee.ad.entity.Creative;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xincong yao
 */
public interface CreativeRepository extends JpaRepository<Creative, Long> {

    Creative findByUserIdAndName(Long userId, String name);

    boolean existsByName(String name);
}
