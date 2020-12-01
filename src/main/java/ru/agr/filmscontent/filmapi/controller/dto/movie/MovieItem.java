package ru.agr.filmscontent.filmapi.controller.dto.movie;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.agr.filmscontent.filmapi.controller.dto.genre.GenreItem;
import ru.agr.filmscontent.filmapi.db.entity.Movie;

import java.io.Serializable;
import java.util.List;

/**
 *
 *
 * @author Arslan Rabadanov
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MovieItem implements Serializable {
    private Long id;

    private String title;

    private String titleEn;

    private String year;

    private String imdbID;

    private Movie.MovieType type;

    private String poster;

    private String description;

    private String country;

    private String video;

    private List<GenreItem> genres;
}
