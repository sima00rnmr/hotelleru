package com.example.moattravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moattravel.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

}
//自宅PCとGITが上手く紐づいていなかったので一旦テスト