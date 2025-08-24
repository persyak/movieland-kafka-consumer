package com.ohorodnik.movieland.entity;

import com.ohorodnik.movieland.utils.enums.MovieEventType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
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
public class MovieEvent {

    @Id
    private Integer id;
    private MovieEventType movieEventType;

    @OneToOne(fetch = FetchType.LAZY)
    private MovieDetails movieDetails;
}
