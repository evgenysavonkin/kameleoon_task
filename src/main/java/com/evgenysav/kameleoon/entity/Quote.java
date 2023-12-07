package com.evgenysav.kameleoon.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "quotes")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@ToString
public class Quote {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "content")
    @NonNull
    private String content;

    @Column(name = "date_of_creating")
    @NonNull
    private Date dateOfCreating;

    @Column(name = "date_of_updating")
    @NonNull
    private Date dateOfUpdating;

    @Column(name = "link")
    @NonNull
    private String linkToUser;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NonNull
    private User user;
}
