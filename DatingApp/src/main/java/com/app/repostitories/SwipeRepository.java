package com.app.repostitories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.Swipe;

public interface SwipeRepository extends JpaRepository<Swipe, Long>{

}
