package com.app.repostitories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.Report;

public interface ReportRepository extends JpaRepository<Report, Long>{

}
