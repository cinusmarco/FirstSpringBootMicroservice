/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2021.
 */

package com.example.ec.web;

import com.example.ec.domain.Tour;
import com.example.ec.domain.TourRating;
import com.example.ec.service.TourRatingService;
import com.example.ec.service.TourService;
import com.example.ec.web.dto.RatingDTO;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tours/{tourId}/ratings")
public class TourRatingController {
  private final TourService tourService;
  private final TourRatingService tourRatingService;

  public TourRatingController(final TourService tourService, final TourRatingService tourRatingService) {
    this.tourService = tourService;
    this.tourRatingService = tourRatingService;
  }

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public void createTourRating(
      @PathVariable(value = "tourId") int tourId, @RequestBody @Validated RatingDTO ratingDTO) {
    final Tour tour = verifyTour(tourId);
    tourRatingService.createNewTourRating(
        tour, ratingDTO.getCustomerId(), ratingDTO.getScore(), ratingDTO.getComment());
  }

  @GetMapping
  public Page<RatingDTO> getAllRatingForTour(@PathVariable(value = "tourId") int tourId, final Pageable pageable) {
    verifyTour(tourId);
    final Page<TourRating> ratings = tourRatingService.getAllRatingsForTour(tourId, pageable);
    return new PageImpl<>(
        ratings.get().map(RatingDTO::new).collect(Collectors.toList()), pageable, ratings.getTotalElements());
  }

  @GetMapping(path = "/average")
  public Map<String, Double> getAverage(@PathVariable(value = "tourId") int tourId) {
    return Map.of(
        "average",
        getAllRatingForTour(tourId, Pageable.unpaged()).stream()
            .mapToInt(RatingDTO::getScore)
            .average()
            .orElseThrow(() -> new NoSuchElementException("Tour " + tourId + " has not yet been rated")));
  }

  @PutMapping
  public RatingDTO updateWithPut(@PathVariable int tourId, @RequestBody @Validated RatingDTO ratingDTO) {
    final TourRating tourRating = verifyTourRating(tourId, ratingDTO.getCustomerId());
    tourRating.setScore(ratingDTO.getScore());
    tourRating.setComment(ratingDTO.getComment());
    return new RatingDTO(tourRatingService.updateTourRating(tourRating));
  }

  @PatchMapping
  public RatingDTO updateWithPatch(@PathVariable int tourId, @RequestBody @Validated RatingDTO ratingDTO) {
    final TourRating tourRating = verifyTourRating(tourId, ratingDTO.getCustomerId());
    if (ratingDTO.getScore() != null) {
      tourRating.setScore(ratingDTO.getScore());
    }
    if (ratingDTO.getComment() != null) {
      tourRating.setComment(ratingDTO.getComment());
    }
    return new RatingDTO(tourRatingService.updateTourRating(tourRating));
  }

  @DeleteMapping(path = "/{customerId}")
  public void delete(@PathVariable final int tourId, @PathVariable final int customerId) {
    final TourRating tourRating = verifyTourRating(tourId, customerId);
    tourRatingService.deleteTourRating(tourRating);
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NoSuchElementException.class)
  public String return404(NoSuchElementException noSuchElementException) {
    return noSuchElementException.getLocalizedMessage();
  }

  private Tour verifyTour(int tourId) {
    return tourService.getTour(tourId).orElseThrow(() -> new NoSuchElementException("Tour doesn't exists " + tourId));
  }

  private TourRating verifyTourRating(int tourId, int customerId) {
    return tourRatingService
        .getCustomerRatingForTour(customerId, tourId)
        .orElseThrow(() -> new NoSuchElementException("Customer " + customerId + " has not yet rated Tour " + tourId));
  }
}
