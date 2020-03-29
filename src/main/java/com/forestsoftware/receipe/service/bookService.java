package com.forestsoftware.receipe.service;


import com.forestsoftware.receipe.model.User;
import com.forestsoftware.receipe.model.bookModel;
import com.forestsoftware.receipe.repository.bookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

//RequiredArgsConstructor is a Lombok annotation which generates a constructor with required fields
//        (final fields and @NonNull fields). For the above ProductService class, Lombok will generate
public class bookService {

    private final bookRepository bookRepository;

    public List<bookModel> findAll() {
        return bookRepository.findAll();
    }
    public Optional<bookModel> findById(Long id){
        return bookRepository.findById(id);
    }

    public bookModel save(bookModel bookModel){
        return bookRepository.save(bookModel);
    }


    public void deleteById(Long bookId){
        bookRepository.deleteById(bookId);
    }

//    public Page<bookModel> findByUser(User user, Pageable pageable){
//      return   bookRepository.findByUser(user,pageable);
//    }
}
