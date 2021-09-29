/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2021.
 */

package com.example.ec.web.dto;

import com.example.ec.domain.TourRating;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RatingDTO {

  @Min(0)
  @Max(5)
  private final Integer score;

  @Size(max = 255)
  private final String comment;

  @NotNull private final Integer customerId;

  public RatingDTO(final Integer score, final String comment, final Integer customerId) {
    this.score = score;
    this.comment = comment;
    this.customerId = customerId;
  }

  public RatingDTO(TourRating tourRating) {
    this(tourRating.getScore(), tourRating.getComment(), tourRating.getPk().getCustomerId());
  }

  public Integer getScore() {
    return score;
  }

  public String getComment() {
    return comment;
  }

  public Integer getCustomerId() {
    return customerId;
  }
}
