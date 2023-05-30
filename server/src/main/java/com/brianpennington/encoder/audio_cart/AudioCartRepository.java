package com.brianpennington.encoder.audio_cart;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called audioCartRepository
// CRUD refers Create, Read, Update, Delete

public interface AudioCartRepository
        extends CrudRepository<AudioCart, Integer>, PagingAndSortingRepository<AudioCart, Integer> {

}
