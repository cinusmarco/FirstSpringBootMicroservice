package com.example.ec.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * A Classification of Tours.
 *
 * <p>Created by Mary Ellen Bowman
 */
@Entity
@Table(name = "TOUR_PACKAGE")
public class TourPackage {
  @Id
  @Column(name = "CODE")
  private String code;

  @Column(name = "NAME")
  private String name;

  protected TourPackage() {}

  public TourPackage(String code, String name) {
    this.code = code;
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "TourPackage{" + "code='" + code + '\'' + ", name='" + name + '\'' + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TourPackage that = (TourPackage) o;
    return Objects.equals(code, that.code) && Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, name);
  }
}
