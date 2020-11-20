package com.example.BookingApp.accommodation.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Accommodation accommodation;
    @Enumerated(EnumType.ORDINAL)
    private RatingValue accommodationRating;
    private String accommodationCommentary;
    @ManyToOne
    private Room room;
    @Enumerated(EnumType.ORDINAL)
    private RatingValue roomRating;
    private String roomCommentary;

}
