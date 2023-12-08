package com.evgenysav.kameleoon.service;

import com.evgenysav.kameleoon.entity.Quote;
import com.evgenysav.kameleoon.entity.Vote;
import com.evgenysav.kameleoon.enums.VoteType;
import com.evgenysav.kameleoon.repository.QuoteRepository;
import com.evgenysav.kameleoon.repository.VoteRepository;
import com.evgenysav.kameleoon.util.QuoteNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class VoteService {
    private final VoteRepository voteRepository;
    private final QuoteRepository quoteRepository;

    public VoteService(VoteRepository voteRepository, QuoteRepository quoteRepository) {
        this.voteRepository = voteRepository;
        this.quoteRepository = quoteRepository;
    }

    @Transactional
    public void createVote(long quoteId, VoteType voteType) {
        Optional<Quote> quoteOpt = quoteRepository.findById(quoteId);
        if (quoteOpt.isEmpty()) {
            throw new QuoteNotFoundException(quoteId);
        }
        Quote quote = quoteOpt.get();
        if (quote.getVotes() == null) {
            quote.setVotes(new ArrayList<>());
        }
        Vote vote = new Vote(quote, quote.getUser(), voteType, new Date());
        quote.getVotes().add(vote);
        voteRepository.save(vote);
    }
}
