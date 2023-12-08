package com.evgenysav.kameleoon.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

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

    @Column(name = "date_of_creation")
    @NonNull
    private Date dateOfCreating;

    @Column(name = "date_of_update")
    @NonNull
    private Date dateOfUpdate;

    @Column(name = "link_to_user")
    @NonNull
    private String linkToUser;

    @Column(name = "link_to_votes")
    @NonNull
    private String linkToVotes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NonNull
    private User user;

    @OneToMany(mappedBy = "quote", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Vote> votes;
}
