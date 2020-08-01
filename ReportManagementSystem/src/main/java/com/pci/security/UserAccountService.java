package com.pci.security;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pci.entity.MtUser;
import com.pci.repository.CompanyRepository;
import com.pci.repository.RoleRepository;
import com.pci.repository.UserRepository;
@Service
public class UserAccountService implements UserDetailsService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String usercode) throws UsernameNotFoundException {
		// TODO 自動生成されたメソッド・スタブ
		if(usercode == null || "".equals(usercode)) {
			throw new UsernameNotFoundException("usercode is empty");
		}
		Optional<MtUser> exist = userRepository.findByUsercode(usercode);
		if(exist==null) {
			throw new UsernameNotFoundException("user is not found "+ usercode);
		}
		MtUser user = exist.get();
		return new UserAccount(user,getAuthorities(user));
	}
	
	private Collection<GrantedAuthority> getAuthorities(MtUser user){
		if(user.getMtRole().getRolename().equals("ADMIN")) {
			return AuthorityUtils.createAuthorityList("ROLE_ADMIN");
		}else if(user.getMtRole().getRolename().equals("TRAINER")) {
			return AuthorityUtils.createAuthorityList("ROLE_TRAINER");
		}else if(user.getMtRole().getRolename().equals("STUDENT")){
			return AuthorityUtils.createAuthorityList("ROLE_STUDENT");
		}else{
			return AuthorityUtils.createAuthorityList("ROLE_CUSTOMER");
		}
	}
	@Transactional
	public void registerAdmin(String usercode,String lastname,String firstname,String password,String companycode) {
		userRepository.save(new MtUser(usercode,lastname,firstname,passwordEncoder.encode(password),companyRepository.findByCompanycode(companycode).get(),roleRepository.findByRolecode("1")));
	}
	@Transactional
	public void registerTrainer(String usercode,String lastname,String firstname,String password,String companycode) {
		userRepository.save(new MtUser(usercode,lastname,firstname,passwordEncoder.encode(password),companyRepository.findByCompanycode(companycode).get(),roleRepository.findByRolecode("2")));
	}
	@Transactional
	public void registerStudent(String usercode,String lastname,String firstname,String password,String companycode) {
		userRepository.save(new MtUser(usercode,lastname,firstname,passwordEncoder.encode(password),companyRepository.findByCompanycode(companycode).get(),roleRepository.findByRolecode("3")));
	}
	@Transactional
	public void registerCustomer(String usercode,String lastname,String firstname,String password,String companycode) {
		userRepository.save(new MtUser(usercode,lastname,firstname,passwordEncoder.encode(password),companyRepository.findByCompanycode(companycode).get(),roleRepository.findByRolecode("4")));
	}
}
