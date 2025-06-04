package com.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.Report;

public interface ReportRepository extends JpaRepository<Report, Long>{

}
