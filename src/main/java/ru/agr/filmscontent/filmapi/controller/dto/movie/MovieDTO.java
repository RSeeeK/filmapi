package ru.agr.filmscontent.filmapi.controller.dto.movie;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Getter
public class MovieDTO implements Serializable {
    private final List<MovieItem> search;

    private final Long totalResults;

    private final Boolean response;
}
