package com.forestsoftware.receipe.model;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
//@Data is a Lombok annotation which generates field getters and setters,
//    toString, equals and hashCode methods for you at compile time

public class bookModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String isbn;
    private String year;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private Set<User> users;

    private Long user;

    @CreationTimestamp
    private Date dateCreated;

    @UpdateTimestamp
    private Date dateUpdated;
}
