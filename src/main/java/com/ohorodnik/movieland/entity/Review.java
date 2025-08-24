package com.ohorodnik.movieland.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Review {

    @Id
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private MovieDetails movieDetails;
}
