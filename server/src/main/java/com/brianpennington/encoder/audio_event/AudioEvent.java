package com.brianpennington.encoder.audio_event;

import com.brianpennington.encoder.audio_cart.AudioCart;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.PreRemove;
import java.util.Date;

@Entity
public class AudioEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long createdAt;

    private long updatedAt;

    @Column(nullable = true)
    private long deletedAt;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private AudioCart cart;

    private long duration;

    public AudioEvent() {
    }

    public AudioEvent(final AudioCart cart, final long duration) {
        this.cart = cart;
        this.duration = duration;
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

    public AudioCart getCart() {
        return this.cart;
    }

    public void setCart(final AudioCart cart) {
        this.cart = cart;
    }

    public long getDuration() {
        return this.duration;
    }

    public void setDuration(final long duration) {
        this.duration = duration;
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
