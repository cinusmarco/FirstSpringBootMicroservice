/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2021.
 */

package com.example.ec.service;

import com.example.ec.domain.Tour;
import com.example.ec.domain.TourRating;
import com.example.ec.domain.TourRatingPk;
import com.example.ec.repo.TourRatingRepository;
import com.example.ec.repo.TourRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TourRatingService {
  private final TourRatingRepository tourRatingRepository;
  private final TourRepository tourRepository;

  public TourRatingService(final TourRatingRepository tourRatingRepository, final TourRepository tourRepository) {
    this.tourRatingRepository = tourRatingRepository;
    this.tourRepository = tourRepository;
  }

  public Page<TourRating> getAllRatingsForTour(final Integer tourId, Pageable pageable) {
    return tourRatingRepository.findByPkTourId(tourId, pageable);
  }

  public Optional<TourRating> getRating(final Integer tourId, final Integer customerId) {
    return tourRatingRepository.findByPkTourIdAndPkCustomerId(tourId, customerId);
  }

  public void createNewTourRating(final Tour tour, final Integer customerId, final int score, final String comment) {
    TourRating tourRating = new TourRating(new TourRatingPk(tour, customerId), score, comment);
    tourRatingRepository.save(tourRating);
  }

  public Optional<TourRating> getCustomerRatingForTour(final int customerId, final int tourId) {
    return tourRatingRepository.findByPkTourIdAndPkCustomerId(tourId, customerId);
  }

  public TourRating updateTourRating(final TourRating tourRating) {
    return tourRatingRepository.save(tourRating);
  }

  public void deleteTourRating(final TourRating tourRating) {
    tourRatingRepository.delete(tourRating);
  }
}
