package com.cooksys.twitter.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class Context {
    private Tweet target;

    private List<Tweet> before;

    private List<Tweet> after;
}
