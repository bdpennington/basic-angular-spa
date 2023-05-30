package com.brianpennington.encoder.audio_cart;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.PreRemove;
import java.util.Date;

@Entity
public class AudioCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long createdAt;

    private long updatedAt;

    @Column(nullable = true)
    private long deletedAt;

    private String title;

    private String artist;

    public AudioCart() {
    }

    public AudioCart(final String title, final String artist) {
        this.title = title;
        this.artist = artist;
    }

    public long getId() {
        return this.id;
    }

    public long getCreatedAt() {
        return this.createdAt;
    }

    public long getUpdatedAt() {
        return this.updatedAt;
    }

    public long getDeletedAt() {
        return this.deletedAt;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getArtist() {
        return this.artist;
    }

    public void setArtist(final String artist) {
        this.artist = artist;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = this.getCurrentTimestamp();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = this.getCurrentTimestamp();
    }

    @PreRemove
    protected void onDelete() {
        this.deletedAt = this.getCurrentTimestamp();
    }

    private long getCurrentTimestamp() {
        return new Date().getTime() / 1000L; // Unix epoch timestamp in seconds
    }
}
