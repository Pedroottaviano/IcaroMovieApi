package com.example.primerApi.service;

import com.example.primerApi.exception.MovieNotFoundException;
import com.example.primerApi.model.Movie;
import com.example.primerApi.repository.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.yaml.snakeyaml.emitter.Emitter;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;
    private MovieService underTest;

    @BeforeEach
    void setUp(){
        underTest = new MovieService(movieRepository);
    }

    @Test
    void findAllMoviesTest() {
        underTest.findAllMovies();
        verify(movieRepository).findAll();
    }

    @Test
    void addMovieTest(){
        Movie movie = new Movie(5L, "La venganza del 5",  "suspenso", "Stephen King", "13212312.jpg");
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);
        assertNotNull(underTest.addMovie(new Movie()));
    }

    @Test
    void findMovieByIdTest(){
        Long movieId = 5L;
        Movie mockedMovie = new Movie(movieId, "Aravinth P", "este", "Hii", "aravinth.p@email.com");
        doReturn(Optional.of(mockedMovie)).when(movieRepository).findById(movieId);
        // Make the service call
        Movie movieByService = underTest.findMovieById(movieId);
        // Assert the response
        assertNotNull(movieByService,"Employee with employeeId : "+movieId+" not found");
        assertEquals(movieId, movieByService.getId());
        assertEquals(mockedMovie.getTitle(), movieByService.getTitle());
        assertEquals(mockedMovie.getDirector(), movieByService.getDirector());
        assertEquals(mockedMovie.getGender(), movieByService.getGender());
        assertEquals(mockedMovie.getBanner(), movieByService.getBanner());
    }

    @Test
    void findMovieByIdFailedTest() {
        Long wrongMovieId = 5L;
        Exception exception = assertThrows(MovieNotFoundException.class, () -> {
            underTest.findMovieById(wrongMovieId);
        });

        String expectedMessage = "could not find movie with id: "+ wrongMovieId;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
