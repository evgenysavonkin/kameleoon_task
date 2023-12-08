package com.evgenysav.kameleoon.controller;

import com.evgenysav.kameleoon.entity.Quote;
import com.evgenysav.kameleoon.entity.User;
import com.evgenysav.kameleoon.enums.VoteType;
import com.evgenysav.kameleoon.service.QuoteService;
import com.evgenysav.kameleoon.service.UserService;
import com.evgenysav.kameleoon.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
public class MainController {

    private final UserService userService;
    private final QuoteService quoteService;

    public MainController(UserService userService, QuoteService quoteService) {
        this.userService = userService;
        this.quoteService = quoteService;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/quotes")
    public ResponseEntity<Quote> createQuote(@RequestBody Quote quote) {
        Quote createdQuote = quoteService.createQuote(quote);
        return new ResponseEntity<>(createdQuote, HttpStatus.CREATED);
    }

    @GetMapping("/quotes/{id}")
    public ResponseEntity<Quote> findQuoteById(@PathVariable long id) {
        Quote quote = quoteService.findQuoteById(id);
        return new ResponseEntity<>(quote, HttpStatus.CREATED);
    }

    @GetMapping("/quotes")
    public ResponseEntity<List<Quote>> getAllQuotes() {
        return new ResponseEntity<>(quoteService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/quotes/{id}")
    public ResponseEntity<Quote> updateQuote(@PathVariable long id, @RequestBody Quote updatedQuote) {
        Quote modifiedQuote = quoteService.updateQuote(id, updatedQuote);
        return new ResponseEntity<>(modifiedQuote, HttpStatus.OK);
    }

    @DeleteMapping("/quotes/{id}")
    public ResponseEntity<Void> deleteQuote(@PathVariable long id) {
        quoteService.deleteQuote(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/quotes/random")
    public ResponseEntity<Quote> getRandomQuote() {
        return new ResponseEntity<>(quoteService.getRandomQuote(), HttpStatus.OK);
    }

    @GetMapping("/quotes/top10")
    public ResponseEntity<List<Quote>> getTop10Quotes() {
        return new ResponseEntity<>(quoteService.getTenBestQuotes(), HttpStatus.OK);
    }

    @GetMapping("/quotes/worst10")
    public ResponseEntity<List<Quote>> getWorst10Quotes() {
        return new ResponseEntity<>(quoteService.getTenWorstQuotes(), HttpStatus.OK);
    }

    @GetMapping("/quotes/{id}/getEvoData")
    public ResponseEntity<List<ChartData>> getEvolutionOfVotes(@PathVariable long id) {
        return new ResponseEntity<>(quoteService.getVoteEvolutionData(id), HttpStatus.OK);
    }

    @GetMapping("/quotes/{id}/vote/{voteType}")
    public ResponseEntity<Void> voteOnQuote(@PathVariable long id, @PathVariable VoteType voteType) {
        quoteService.voteOnQuote(id, voteType);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<EntityNotFoundResponse> handleQuoteNotFoundException(QuoteNotFoundException e) {
        return responseEntityMethod(e);
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<EntityNotFoundResponse> handleUserNotFoundException(UserNotFoundException e) {
        return responseEntityMethod(e);
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<EntityNotFoundResponse> handleUpdateQuoteException(QuoteUpdateException e) {
        return responseEntityMethod(e);
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<EntityNotFoundResponse> handleEmptyVotesListForChartData(NoVotesForDataException e){
        return responseEntityMethod(e);
    }

    private ResponseEntity<EntityNotFoundResponse> responseEntityMethod(Exception e) {
        EntityNotFoundResponse response = new EntityNotFoundResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
