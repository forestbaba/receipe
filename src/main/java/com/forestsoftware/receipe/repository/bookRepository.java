package com.forestsoftware.receipe.repository;

import com.forestsoftware.receipe.model.bookModel;
import org.springframework.data.jpa.repository.JpaRepository;
import  com.forestsoftware.receipe.model.bookModel;

public interface bookRepository extends JpaRepository<bookModel, Long> {
}
