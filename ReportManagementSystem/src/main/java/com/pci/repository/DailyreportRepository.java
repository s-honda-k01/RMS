package com.pci.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pci.entity.TrDailyreport;
@Repository
public interface DailyreportRepository extends JpaRepository<TrDailyreport, Integer> {
	public Optional<TrDailyreport> findByDailyreportid(Integer dailyreportid);
	
	@Query("select r FROM TrDailyreport r WHERE r.mtUser1.usercode = :usercode and r.regdate = :today")
	public Optional<TrDailyreport> findReportOfTodayByUsercode(@Param("usercode") String usercode,@Param("today") Date today);

	@Query("select r FROM TrDailyreport r WHERE r.mtUser1.usercode = :usercode order by r.regdate desc")
	public List<TrDailyreport> findByUsercode(@Param("usercode") String usercode);
}
