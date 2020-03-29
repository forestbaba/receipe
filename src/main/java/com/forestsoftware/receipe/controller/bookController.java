package com.forestsoftware.receipe.controller;


import com.forestsoftware.receipe.exception.ResourceNotFoundException;
import com.forestsoftware.receipe.model.User;
import com.forestsoftware.receipe.model.bookModel;
import com.forestsoftware.receipe.repository.UserRepository;
import com.forestsoftware.receipe.repository.bookRepository;
import com.forestsoftware.receipe.service.UserDetailsImpl;
import com.forestsoftware.receipe.service.bookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/api/v1/books")
@Slf4j
@RequiredArgsConstructor

public class bookController {

    private final bookService bookService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    bookRepository bookRepository;

    @GetMapping
    public ResponseEntity<List<bookModel>> getAllBooks(){
        return ResponseEntity.ok(bookService.findAll());
    }
//    @GetMapping("/hello")
//    public String getHello(){
//        return "Hello from this spring";
//    }

    @PostMapping("/addBook")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity create(@Valid @RequestBody bookModel book, Authentication authentication) {

        UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();

        return userRepository.findById(userDetails.getId()).map(user ->{
            book.setUser(user);


            return ResponseEntity.ok( bookService.save(book));
        }).orElseThrow(()-> new ResourceNotFoundException(String.format("User not found")));

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<bookModel>getOne(@PathVariable Long id){
        if(!bookService.findById(id).isPresent()){
            ResponseEntity.status(404).eTag("Not found").build();

        }
        Optional<bookModel> bookModel1 = bookService.findById(id);
        return ResponseEntity.ok(bookModel1.get());
    }

    @PutMapping("/{bookid}")
    @PreAuthorize("hasRole('USER')")

    public ResponseEntity<bookModel> update(@PathVariable Long bookid, Authentication authentication, @Valid @RequestBody bookModel bookModel){
       if(!bookService.findById(bookid).isPresent()){

           log.error("Book with Id %s can not be found",bookid);
           ResponseEntity.badRequest().build();
       }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Optional<bookModel> bookModel1 = bookService.findById(bookid);

        if (bookModel1.get().getUser().toString() != userDetails.getId().toString()){
           Map<String, Object> errorStatus = new HashMap<String,Object>();
           errorStatus.put("error",true);
           errorStatus.put("message","Your are not authorized to edit this book");
            return new ResponseEntity(errorStatus,HttpStatus.UNAUTHORIZED);
        }
        bookModel bookModel2 =new bookModel();
        bookModel2.setAuthor(bookModel.getAuthor());
        bookModel2.setIsbn(bookModel.getIsbn());
        bookModel2.setTitle(bookModel.getTitle());
        bookModel2.setYear(bookModel.getYear());
        bookModel2.setDateCreated(bookModel1.get().getDateCreated());
        bookModel2.setId(bookModel1.get().getId());
        bookModel2.setUser(bookModel1.get().getUser());

        return ResponseEntity.ok(bookService.save(bookModel2));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<String,Object>>deleteBook(@PathVariable Long id,Authentication authentication){
        Optional<bookModel>book = bookService.findById(id);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Map<String, Object>errorMap = new HashMap<>();

        System.out.println("The id i'm looking for: ============"+id);
        if(id.toString() == "" || id.toString() == null){
            return ResponseEntity.badRequest().eTag("id is required").build();
        }
        if(!bookService.findById(id).isPresent()){
            log.error("No id found",id);
            errorMap.put("error",true);
            errorMap.put("message",String.format("Book with id %s is not found", id));
            return new ResponseEntity<Map<String, Object>>(errorMap, HttpStatus.BAD_REQUEST);

        }
        if(userDetails.getId().toString() !=  book.get().getUser().toString()){
            errorMap.put("error",false);
            errorMap.put("message",String.format("You are not authorized to delete %s ",book.get().getTitle()));
            return new ResponseEntity<>(errorMap,HttpStatus.UNAUTHORIZED);
        }
        bookService.deleteById(id);
        errorMap.put("error",false);
        errorMap.put("message",String.format("%s had been deleted",book.get().getTitle()));

        return new ResponseEntity<>(errorMap, HttpStatus.OK);

    }

    @GetMapping("/getUserBooks/{userId}")
    @PreAuthorize("hasRole('USER')")
    public Page<bookModel> getAllUsersBook(@PathVariable Long userId, Pageable pageable){

//
//        bookModel user = new bookModel();
//         userRepository.findById(userId).map(user->{
//             user.set(user);
//
//        });
        return bookRepository.findByUserId(userId,pageable);

    }
}
