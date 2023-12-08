package com.evgenysav.kameleoon.service;

import com.evgenysav.kameleoon.entity.Quote;
import com.evgenysav.kameleoon.entity.User;
import com.evgenysav.kameleoon.entity.Vote;
import com.evgenysav.kameleoon.enums.VoteType;
import com.evgenysav.kameleoon.repository.QuoteRepository;
import com.evgenysav.kameleoon.util.ChartData;
import com.evgenysav.kameleoon.util.NoVotesForDataException;
import com.evgenysav.kameleoon.util.QuoteNotFoundException;
import com.evgenysav.kameleoon.util.QuoteUpdateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class QuoteService {
    private final QuoteRepository quoteRepository;
    private final VoteService voteService;
    private final UserService userService;

    public QuoteService(QuoteRepository quoteRepository, VoteService voteService, UserService userService) {
        this.quoteRepository = quoteRepository;
        this.voteService = voteService;
        this.userService = userService;
    }

    @Transactional
    public Quote createQuote(Quote quote) {
        long userId = quote.getUser().getId();
        User user = userService.findUserById(userId);
        quote.setUser(user);
        if (user.getQuotes() == null) {
            user.setQuotes(new ArrayList<>());
        }
        user.getQuotes().add(quote);
        return quoteRepository.save(quote);
    }

    public Quote findQuoteById(long id) {
        Optional<Quote> quoteOpt = quoteRepository.findById(id);
        if (quoteOpt.isEmpty()) {
            throw new QuoteNotFoundException(id);
        }
        return quoteOpt.get();
    }

    public Quote getRandomQuote() {
        Optional<Long> maxQuoteIdOpt = quoteRepository.findMaxQuoteId();
        if (maxQuoteIdOpt.isEmpty()) {
            return new Quote();
        }
        long maxQuoteId = maxQuoteIdOpt.get();
        Random random = new Random();
        int randomInt = random.nextInt((int) maxQuoteId) + 1;
        return findQuoteById(randomInt);
    }

    @Transactional
    public Quote updateQuote(long id, Quote updatedQuote) {
        Optional<Quote> quoteOpt = quoteRepository.findById(id);
        if (quoteOpt.isEmpty()) {
            throw new QuoteUpdateException(id);
        }
        Quote existingQuote = quoteOpt.get();
        updateQuoteFields(existingQuote, updatedQuote);
        return quoteRepository.save(existingQuote);
    }

    private void updateQuoteFields(Quote existingQuote, Quote updatedQuote) {
        existingQuote.setContent(updatedQuote.getContent());
        existingQuote.setDateOfCreating(updatedQuote.getDateOfCreating());
        existingQuote.setDateOfUpdate(updatedQuote.getDateOfUpdate());
        existingQuote.setLinkToUser(updatedQuote.getLinkToUser());
        existingQuote.setLinkToVotes(updatedQuote.getLinkToVotes());
        //Setting user
        User user = userService.findUserById(updatedQuote.getUser().getId());
        userService.saveUser(user);
        existingQuote.setUser(user);
        existingQuote.setVotes(updatedQuote.getVotes());
    }

    @Transactional
    public void deleteQuote(long id) {
        quoteRepository.deleteById(id);
    }

    public List<Quote> findAll() {
        return quoteRepository.findAll();
    }

    @Transactional
    public void voteOnQuote(long quoteId, VoteType voteType) {
        voteService.createVote(quoteId, voteType);
    }

    public List<Quote> getTenBestQuotes() {
        List<Quote> allQuotes = findAll();

        return allQuotes.stream()
                .sorted(Comparator.comparingInt(this::calculateVotes).reversed())
                .limit(10)
                .toList();
    }

    public List<Quote> getTenWorstQuotes() {
        List<Quote> allQuotes = findAll();

        return allQuotes.stream()
                .sorted(Comparator.comparingInt(this::calculateVotes))
                .limit(10)
                .toList();
    }

    private int calculateVotes(Quote quote) {
        int upVotes = 0;
        int downVotes = 0;

        for (Vote vote : quote.getVotes()) {
            if (vote.getVoteType() == VoteType.UPVOTE) {
                upVotes++;
            } else if (vote.getVoteType() == VoteType.DOWNVOTE) {
                downVotes++;
            }
        }
        return upVotes - downVotes;
    }

    public List<ChartData> getVoteEvolutionData(long quoteId) {
        Quote quote = findQuoteById(quoteId);
        List<ChartData> chartDataList = new ArrayList<>();
        List<Vote> votes = quote.getVotes();
        if (votes == null || votes.isEmpty()) {
            throw new NoVotesForDataException(quoteId);
        }
        for (Vote vote : votes) {
            ChartData chartData = new ChartData();
            chartData.setVoteDate(vote.getVoteDate());
            chartData.setVoteType(vote.getVoteType());
            chartDataList.add(chartData);
        }
        chartDataList.sort(Comparator.comparing(ChartData::getVoteDate));
        return chartDataList;
    }
}
