package com.mazhar.blogs.app.entities;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="post")
@Data
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer postId;

    @Column(length=1000)
    private String content;

    @Column(name="post_title",length=100,nullable=false )
    private String title;

    private String imageName;

    private Date addedDate;
    
    @ManyToOne
    @JoinColumn(name ="category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy="post" ,cascade=CascadeType.ALL)
    private List<Comment> comments;
}
