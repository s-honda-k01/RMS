package com.pci.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pci.entity.MtCompany;
@Repository
public interface CompanyRepository extends JpaRepository<MtCompany, String> {
	public Optional<MtCompany> findByCompanycode(String companycode);
	

}
