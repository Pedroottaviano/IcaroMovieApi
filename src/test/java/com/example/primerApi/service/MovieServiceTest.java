package com.example.primerApi.service;

import com.example.primerApi.exception.MovieNotFoundException;
import com.example.primerApi.model.Gender;
import com.example.primerApi.model.Movie;
import com.example.primerApi.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
    private MovieService testedMovieService;

    @BeforeEach
    void setUp(){
        System.out.println("Esto se ejecuta antes de cada test");
        testedMovieService = new MovieService(movieRepository);
    }

    @Test
    void findAllMoviesTest() {
        testedMovieService.findAllMovies();
        verify(movieRepository).findAll();
    }

    @Test
    void addMovieTest(){
        Movie movie = new Movie(5L, "La venganza del 5",  Gender.ACCION, "Stephen King", "13212312.jpg");
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);
        assertNotNull(testedMovieService.addMovie(new Movie()));
    }

    @Test
    void findMovieByIdTest(){
        Long movieId = 5L;
        Movie mockedMovie = new Movie(movieId, "Aravinth P", Gender.ACCION, "Hii", "aravinth.p@email.com");
        doReturn(Optional.of(mockedMovie)).when(movieRepository).findById(movieId);
        // Make the service call
        Movie movieByService = testedMovieService.findMovieById(movieId);
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
            testedMovieService.findMovieById(wrongMovieId);
        });

        String expectedMessage = "could not find movie with id: "+ wrongMovieId;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
