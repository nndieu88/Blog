package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Indexed
@Entity
@Table
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Field(termVector = TermVector.YES, analyze= Analyze.YES, store= Store.NO)
    private String title;

    @Column(columnDefinition = "TEXT")
    @Field(termVector = TermVector.YES, analyze= Analyze.YES, store= Store.NO)
    private String content;

    private String metaCategory;

    private String isImportant;

    @Field(termVector = TermVector.YES, analyze= Analyze.YES, store= Store.NO)
    private String cate;

    private String metaTitle;

    private Date dateCreated;

    private Date dateUpdated;

    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

//    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<Comment> comments;
}
