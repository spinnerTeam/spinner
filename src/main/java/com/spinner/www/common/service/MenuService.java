package com.spinner.www.common.service;

import com.spinner.www.common.entity.Menu;

import java.util.List;
import java.util.Optional;

public interface MenuService {

    /**
     * menuIdx로 Menu 조회
     * @param menuIdx Long
     * @return Menu
     */
    Optional<Menu> getMenuByMenuIdx(Long menuIdx);

    /**
     * 전체 메뉴 조회
     * @return List<Menu>
     */
    List<Menu> getAll();

    /**
     * 전체 관심분야 조회
     * @return List<Menu>
     */
    List<Menu> getAllInterest();
}
