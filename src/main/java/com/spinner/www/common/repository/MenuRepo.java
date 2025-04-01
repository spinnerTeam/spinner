package com.spinner.www.common.repository;

import com.spinner.www.common.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MenuRepo extends JpaRepository<Menu, Long> {
    Optional<Menu> findByMenuIdx(Long menuIdx);

    List<Menu> findAllByMenuParentIdxIsNotNull();
}
