package com.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.Match;

public interface MatchRepository extends JpaRepository<Match, Long>{

}
