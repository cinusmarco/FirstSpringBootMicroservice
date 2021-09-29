/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2021.
 */

package com.example.ec.service;

import com.example.ec.domain.Difficulty;
import com.example.ec.domain.Region;
import com.example.ec.domain.Tour;
import com.example.ec.domain.TourPackage;
import com.example.ec.repo.TourPackageRepository;
import com.example.ec.repo.TourRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TourService {
  private TourRepository tourRepository;
  private TourPackageRepository tourPackageRepository;

  public TourService(TourRepository tourRepository, TourPackageRepository tourPackageRepository) {
    this.tourRepository = tourRepository;
    this.tourPackageRepository = tourPackageRepository;
  }

  public Tour createTour(
      String title,
      String description,
      String blurb,
      Integer price,
      String duration,
      String bullets,
      String keywords,
      String tourPackageName,
      Difficulty difficulty,
      Region region) {

    TourPackage tourPackage =
        tourPackageRepository
            .findByName(tourPackageName)
            .orElseThrow(() -> new RuntimeException("Tour package " + tourPackageName + " doesn't exists"));
    final Tour tour =
        new Tour(title, description, blurb, price, duration, bullets, keywords, tourPackage, difficulty, region);
    return tourRepository.save(tour);
  }

  public long total() {
    return tourRepository.count();
  }

  public Iterable<Tour> listAllTours() {
    return tourRepository.findAll();
  }

  public Optional<Tour> getTour(final int tourId) {
    return tourRepository.findById(tourId);
  }
}
