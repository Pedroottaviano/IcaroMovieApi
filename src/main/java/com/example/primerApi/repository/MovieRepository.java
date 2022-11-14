package com.example.primerApi.repository;

import com.example.primerApi.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository <Movie, Long>{
}


