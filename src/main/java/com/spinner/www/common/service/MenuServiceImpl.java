package com.spinner.www.common.service;

import com.spinner.www.common.entity.Menu;
import com.spinner.www.common.repository.MenuRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepo menuRepo;

    /**
     * menuIdx로 Menu 조회
     * @param menuIdx Long
     * @return Menu
     */
    @Override
    public Optional<Menu> getMenuByMenuIdx(Long menuIdx) {
        return menuRepo.findByMenuIdx(menuIdx);
    }

    /**
     * 전체 메뉴 조회
     * @return List<Menu>
     */
    @Override
    public List<Menu> getAll() {
        return menuRepo.findAll();
    }

    /**
     * 전체 관심분야 조회(부모 메뉴가 있는 경우)
     * @return List<Menu>
     */
    @Override
    public List<Menu> getAllInterest() {
        return menuRepo.findAllByMenuParentIdxIsNotNull();
    }
}
