package com.example.BookingApp.accommodation.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private Long accommodationId;
    @Enumerated(EnumType.ORDINAL)
    private RatingValue accommodationRating;
    private String accommodationCommentary;
    @NotBlank
    private Long roomId;
    @Enumerated(EnumType.ORDINAL)
    private RatingValue roomRating;
    private String roomCommentary;

}
