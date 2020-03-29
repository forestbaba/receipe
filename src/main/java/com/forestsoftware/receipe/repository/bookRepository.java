package com.forestsoftware.receipe.repository;

import com.forestsoftware.receipe.model.User;
import com.forestsoftware.receipe.model.bookModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import  com.forestsoftware.receipe.model.bookModel;

public interface bookRepository extends JpaRepository<bookModel, Long> {

    Page<bookModel> findByUserId(Long userId, Pageable pageable);
}
