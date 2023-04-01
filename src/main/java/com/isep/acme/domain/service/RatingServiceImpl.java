package com.isep.acme.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isep.acme.domain.model.Rating;
import com.isep.acme.domain.repository.RatingRepository;

import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService{

    @Autowired
    RatingRepository repository;

    public Optional<Rating> findByRate(Double rate){
        return repository.findByRate(rate);
    }

}
