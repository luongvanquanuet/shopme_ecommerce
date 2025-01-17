package com.shopme.admin.user;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import com.shopme.admin.security.*;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

import jakarta.transaction.Transactional;
@Transactional
@Service
public class UserService {
	@Autowired
	private UserRepository repo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository rolePepo;
	public List<User> listAll(){
		return (List<User>) repo.findAll();
	}

	public List<Role> listAllRole(){
		return (List<Role>) rolePepo.findAll();
	}
	
	public User save(User user) {
		boolean isUpdatingUser = (user.getId() != null);

		if (isUpdatingUser) {
			User existingUser = repo.findById(user.getId()).get();

			if (user.getPassword().isEmpty()) {
				user.setPassword(existingUser.getPassword());
			} else {
				encodePassword(user);
			}

		} else {		
			encodePassword(user);
		}

		 return repo.save(user);
	}
	
	  private void encodePassword(User user) { 
		  String encodedPassword =
	  passwordEncoder.encode(user.getPassword());
	  user.setPassword(encodedPassword); 
	  }
		/*
		 * public boolean isEmailUnique(String email) { User userByEmail =
		 * repo.getUserByEmail(email);
		 * 
		 * return userByEmail == null; }
		 */
		public boolean isEmailUnique(String email, Integer id) {
			User userByEmail = repo.getUserByEmail(email);
			//User userByEmail = userRepo.getUserByEmail(email);

			//return userByEmail == null;
			if (userByEmail == null) return true;

			boolean isCreatingNew = (id == null);

			if (isCreatingNew) {
				if (userByEmail != null) return false;
			} else {
				if (userByEmail.getId() != id) {
					return false;
				}
			}

			return true;

			//return userByEmail == null;
		}

		public User get(Integer id) throws UserNotFoundException {
			try {
			return repo.findById(id).get();
			}
			catch (NoSuchElementException ex) {
				throw new UserNotFoundException("Could not find any user with ID \" + id");
				// TODO: handle exception
			}
		}
		
		public  void updateUserEnabledStatus(Integer id, boolean enabled) {
			repo.updateEnabledStatus(id, enabled);
		}
	 
}
