package com.pci.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pci.entity.MtUser;

@Repository
public interface UserRepository extends JpaRepository<MtUser, String> {

	public Optional<MtUser> findByUsercode(String usercode);
	
}
