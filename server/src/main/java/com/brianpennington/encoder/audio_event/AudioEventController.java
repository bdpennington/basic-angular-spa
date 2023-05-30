package com.brianpennington.encoder.audio_event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/event")
public class AudioEventController {

    private final AudioEventRepository audioEventRepository;

    @Autowired
    public AudioEventController(final AudioEventRepository audioEventRepository) {
        this.audioEventRepository = audioEventRepository;
    }

    // AudioEvent CRUD operations

    @GetMapping("/")
    public @ResponseBody Page<AudioEvent> getAllAudioEvents(
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "10") final int size,
            @RequestParam(defaultValue = "createdAt") final String sortBy,
            @RequestParam(defaultValue = "asc") final String order) {

        final Sort.Direction direction = "asc".equalsIgnoreCase(order) ? Sort.Direction.ASC : Sort.Direction.DESC;
        final Sort sort = Sort.by(direction, sortBy);
        final PageRequest pageRequest = PageRequest.of(page, size, sort);

        return this.audioEventRepository.findAll(pageRequest);
    }

    @GetMapping("/{id}")
    public @ResponseBody AudioEvent getAudioEvent(@PathVariable("id") final Integer id) {
        return this.audioEventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid AudioEvent ID: " + id));
    }

    @PostMapping("/")
    public @ResponseBody AudioEvent createAudioEvent(@RequestBody final AudioEvent AudioEvent) {
        return this.audioEventRepository.save(AudioEvent);
    }

    @PatchMapping("/{id}")
    public @ResponseBody AudioEvent updateAudioEvent(@PathVariable("id") final Integer id,
            @RequestBody final AudioEvent updatedAudioEvent) {
        final AudioEvent AudioEvent = this.audioEventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid AudioEvent ID: " + id));

        AudioEvent.setDuration(updatedAudioEvent.getDuration());
        AudioEvent.setCart(updatedAudioEvent.getCart());

        return this.audioEventRepository.save(AudioEvent);
    }

    @DeleteMapping("/{id}")
    public void deleteAudioEvent(@PathVariable("id") final Integer id) {
        final AudioEvent AudioEvent = this.audioEventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid AudioEvent ID: " + id));

        this.audioEventRepository.delete(AudioEvent);
    }
}
