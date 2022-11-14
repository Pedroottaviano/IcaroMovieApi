package com.example.primerApi.controller;

import com.example.primerApi.model.Movie;
import com.example.primerApi.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/{id}")
    public Movie findMovieById(@PathVariable("id") Long id){
        return movieService.findMovieById(id);
    }

    @GetMapping("/all")
    public List<Movie> findAllMovies(){
        return movieService.findAllMovies();
    }

    @PostMapping("/addMovie")
    public Movie addMovie(@RequestBody Movie movie){
        return movieService.addMovie(movie);
    }

    @PutMapping("/updateMovie/{id}")
    public Optional<Movie> updateMovie(@RequestBody Movie movie, @PathVariable Long id){
        return movieService.updateMovie(movie, id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteMovie(@PathVariable("id") Long id){
        movieService.deleteMovie(id);
    }
}
