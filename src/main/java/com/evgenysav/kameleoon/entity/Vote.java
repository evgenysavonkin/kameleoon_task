package com.evgenysav.kameleoon.entity;

import com.evgenysav.kameleoon.enums.VoteType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "votes")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@ToString
public class Vote {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "quote_id", nullable = false)
    @NonNull
    private Quote quote;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NonNull
    private User user;

    @Enumerated(EnumType.STRING)
    @NonNull
    private VoteType voteType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "vote_date", nullable = false)
    @NonNull
    private Date voteDate;
}
