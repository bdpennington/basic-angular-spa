package com.brianpennington.encoder.upload;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.brianpennington.encoder.storage.StorageFileNotFoundException;
import com.brianpennington.encoder.storage.StorageService;
import com.brianpennington.encoder.audio_cart.AudioCart;
import com.brianpennington.encoder.audio_cart.AudioCartRepository;
import com.brianpennington.encoder.audio_event.AudioEvent;
import com.brianpennington.encoder.audio_event.AudioEventRepository;
import com.brianpennington.encoder.audio_utils.AudioUtilsService;

@RestController
@RequestMapping("/upload")
public class UploadController {

    private final StorageService storageService;
    private final AudioUtilsService audioUtilsService;
    private final AudioEventRepository audioEventRepository;
    private final AudioCartRepository audioCartRepository;

    @Autowired
    public UploadController(final StorageService storageService,
            final AudioUtilsService audioUtilsService,
            final AudioEventRepository audioEventRepository,
            final AudioCartRepository audioCartRepository) {
        this.storageService = storageService;
        this.audioUtilsService = audioUtilsService;
        this.audioEventRepository = audioEventRepository;
        this.audioCartRepository = audioCartRepository;
    }

    @PostMapping("")
    public ResponseEntity<?> handleFileUpload(
            @RequestParam("file") final MultipartFile file,
            @RequestParam("artist") final String artist,
            @RequestParam("title") final String title) {

        if (!this.validateUploadData(artist, title)) {
            return ResponseEntity.badRequest().build();
        }

        final Path path = this.storageService.store(file);

        final int dur = this.audioUtilsService.getDuration(path.toString());
        if (dur == -1) {
            return ResponseEntity.internalServerError().build();
        }

        final AudioCart cart = this.audioCartRepository.save(new AudioCart(artist, title));
        final AudioEvent event = this.audioEventRepository.save(new AudioEvent(cart, dur));

        final Optional<String> encodedFilePath = this.audioUtilsService.encodeAac(path.toString(), event.getId());
        if (!encodedFilePath.isPresent()) {
            return ResponseEntity.internalServerError().build();
        }

        try {
            final URI location = new URI("http://localhost:3001/" + event.getId());
            return ResponseEntity.created(location).build();
        } catch (final URISyntaxException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(final StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

    private Boolean validateUploadData(final String artist, final String title) {
        if (artist == null || artist.isEmpty()) {
            return false;
        }
        if (title == null || title.isEmpty()) {
            return false;
        }
        return true;
    }

}
