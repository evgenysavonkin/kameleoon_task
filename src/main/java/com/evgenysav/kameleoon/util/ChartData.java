package com.evgenysav.kameleoon.util;

import com.evgenysav.kameleoon.enums.VoteType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChartData {
    private Date voteDate;
    private VoteType voteType;
}
