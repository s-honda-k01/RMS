package com.pci.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pci.entity.MtReportstatus;
@Repository
public interface ReportstatusRepository extends JpaRepository<MtReportstatus, String> {
	public Optional<MtReportstatus> findByReportstatus(String reportstatus);
}
