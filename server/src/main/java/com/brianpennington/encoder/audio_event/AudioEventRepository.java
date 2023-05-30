package com.brianpennington.encoder.audio_event;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called audioEventRepository
// CRUD refers Create, Read, Update, Delete

public interface AudioEventRepository
        extends CrudRepository<AudioEvent, Integer>, PagingAndSortingRepository<AudioEvent, Integer> {

}
