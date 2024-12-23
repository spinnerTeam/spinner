package com.spinner.www.file.repository;

import com.spinner.www.file.entity.Files;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepo extends JpaRepository<Files, Long> {

//    Files findById(Long idx);
}
