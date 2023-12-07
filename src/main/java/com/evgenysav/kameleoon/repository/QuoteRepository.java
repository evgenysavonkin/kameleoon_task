package com.evgenysav.kameleoon.repository;

import com.evgenysav.kameleoon.entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {

    @Query("SELECT MAX(q.id) FROM Quote q")
    Optional<Long> findMaxQuoteId();
}
