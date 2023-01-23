package com.mazhar.blogs.app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="categories")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Category {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer categoryId;

    @Column(name = "title", nullable = false)
    private String categoryTitle;

    @Column(name = "description")
    private String categoryDescription;
    
    @OneToMany(mappedBy ="category",cascade = CascadeType.ALL ,fetch=FetchType.LAZY)
    private List<Post> postId;
}
