package com.evgenysav.kameleoon.service;

import com.evgenysav.kameleoon.entity.Quote;
import com.evgenysav.kameleoon.repository.QuoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional(readOnly = true)
public class QuoteService {
    private final QuoteRepository repository;

    public QuoteService(QuoteRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void save(Quote quote) {
        repository.save(quote);
    }

    public Quote findById(long id) {
        Optional<Quote> quoteOpt = repository.findById(id);
        return quoteOpt.orElseGet(Quote::new);
    }

    public Quote getRandomQuote() {
        Optional<Long> maxQuoteIdOpt = repository.findMaxQuoteId();
        if (maxQuoteIdOpt.isEmpty()) {
            return new Quote();
        }
        long maxQuoteId = maxQuoteIdOpt.get();
        Random random = new Random();
        int randomInt = random.nextInt((int) maxQuoteId) + 1;
        return findById(randomInt);
    }

    @Transactional
    public void updateQuote(Quote quote) {
        repository.save(quote);
    }

    @Transactional
    public void delete(Quote quote) {
        repository.delete(quote);
    }

    public List<Quote> findAll(){
        return repository.findAll();
    }
}
