package com.pci.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pci.entity.TrDailyreport;
@Repository
public interface DailyreportRepository extends JpaRepository<TrDailyreport, Integer> {
	public Optional<TrDailyreport> findByDailyreportid(Integer dailyreportid);

}
