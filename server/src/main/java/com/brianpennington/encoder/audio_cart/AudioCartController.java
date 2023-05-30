package com.brianpennington.encoder.audio_cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/cart")
public class AudioCartController {

    private final AudioCartRepository audioCartRepository;

    @Autowired
    public AudioCartController(final AudioCartRepository audioCartRepository) {
        this.audioCartRepository = audioCartRepository;
    }

    // AudioCart CRUD operations

    @GetMapping("/")
    public @ResponseBody Page<AudioCart> getAllAudioCarts(
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "10") final int size,
            @RequestParam(defaultValue = "createdAt") final String sortBy,
            @RequestParam(defaultValue = "asc") final String order) {

        final Sort.Direction direction = "asc".equalsIgnoreCase(order) ? Sort.Direction.ASC : Sort.Direction.DESC;
        final Sort sort = Sort.by(direction, sortBy);
        final PageRequest pageRequest = PageRequest.of(page, size, sort);

        return this.audioCartRepository.findAll(pageRequest);
    }

    @GetMapping("/{id}")
    public @ResponseBody AudioCart getAudioCart(@PathVariable("id") final Integer id) {
        return this.audioCartRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid AudioCart ID: " + id));
    }

    @PostMapping("/")
    public @ResponseBody AudioCart createAudioCart(@RequestBody final AudioCart audioCart) {
        return this.audioCartRepository.save(audioCart);
    }

    @PatchMapping("/{id}")
    public @ResponseBody AudioCart updateAudioCart(@PathVariable("id") final Integer id,
            @RequestBody final AudioCart updatedAudioCart) {
        final AudioCart audioCart = this.audioCartRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid AudioCart ID: " + id));

        audioCart.setTitle(updatedAudioCart.getTitle());
        audioCart.setArtist(updatedAudioCart.getArtist());

        return this.audioCartRepository.save(audioCart);
    }

    @DeleteMapping("/{id}")
    public void deleteAudioCart(@PathVariable("id") final Integer id) {
        final AudioCart audioCart = this.audioCartRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid AudioCart ID: " + id));

        this.audioCartRepository.delete(audioCart);
    }
}
