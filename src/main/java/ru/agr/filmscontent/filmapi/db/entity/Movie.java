package ru.agr.filmscontent.filmapi.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.agr.filmscontent.filmapi.db.meta.FilmApiMeta;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Movie entity layer
 *
 * @author Arslan Rabadanov
 */
@Entity
@Table(schema = FilmApiMeta.SCHEMA, name = FilmApiMeta.movie.name)
@Data @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(callSuper = false)
public class Movie extends BaseEntity {
    @Id
    @Column(name = FilmApiMeta.movie.fld.id)
    private Long id;

    @Column(name = FilmApiMeta.movie.fld.title)
    private String title;

    @Column(name = FilmApiMeta.movie.fld.title_en)
    private String titleEn;

    @Column(name = FilmApiMeta.movie.fld.year)
    private Short year;

    @Column(name = FilmApiMeta.movie.fld.imdbID)
    private String imdbID;

    @Enumerated(EnumType.STRING)
    @Column(name = FilmApiMeta.movie.fld.type)
    private MovieType type;

    @Column(name = FilmApiMeta.movie.fld.poster)
    private String poster;

    @Column(name = FilmApiMeta.movie.fld.description)
    private String description;

    @Column(name = FilmApiMeta.movie.fld.country)
    private String country;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = FilmApiMeta.movie_genre.name, schema = FilmApiMeta.SCHEMA,
            joinColumns = {
                @JoinColumn(name = FilmApiMeta.movie_genre.fld.movie_id, referencedColumnName = FilmApiMeta.movie.fld.id)
            },
            inverseJoinColumns = {
                @JoinColumn(name = FilmApiMeta.movie_genre.fld.genre_id, referencedColumnName = FilmApiMeta.genre.fld.id)
    })
    private List<Genre> genres;

    public enum MovieType {
        movie,
        series
    }
}
