package com.example.primerApi.service;

import com.example.primerApi.exception.MovieNotFoundException;
import com.example.primerApi.model.Movie;
import com.example.primerApi.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MovieService {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> findAllMovies(){
        return movieRepository.findAll();
    }

    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }
    public Movie findMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));
    }
    public void deleteMovie(Long id) {
        Optional<Movie> deletedMovie = movieRepository.findById(id);
        if(deletedMovie.isEmpty()){
            throw new MovieNotFoundException(id);
        } else {
            movieRepository.deleteById(id);
        }
    }
    public Optional<Movie> updateMovie(Movie newMovie, Long id) {
        Optional<Movie> updatedMovie = movieRepository.findById(id);
        if ((updatedMovie.isEmpty())){
            throw new MovieNotFoundException(id);
        } else {
            return updatedMovie.map(movie -> {
                movie.setTitle(newMovie.getTitle());
                movie.setDirector(newMovie.getDirector());
                movie.setBanner(newMovie.getBanner());
                movie.setGender(newMovie.getGender());
                return movieRepository.save(movie);
            });
        }
    }
}
