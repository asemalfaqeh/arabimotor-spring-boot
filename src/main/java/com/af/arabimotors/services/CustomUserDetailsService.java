package com.af.arabimotors.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.af.arabimotors.entities.RoleEntity;
import com.af.arabimotors.entities.UserEntity;
import com.af.arabimotors.repositories.RoleRepository;
import com.af.arabimotors.repositories.UserRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public UserEntity findUserByEmail(String email) {
	    return userRepository.findByEmail(email);
	}
	
//	public void saveUser(User user, String role) {
//	    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//	    user.setEnabled(true);
//	    Role userRole = roleRepository.findByRole(role);
//	    user.setRoles(new HashSet<>(Arrays.asList(userRole)));
//	    userRepository.save(user);
//	}
	
	public void saveUser(UserEntity user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        RoleEntity userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        userRepository.save(user);
    }
	
	public void updateCurrentUser(UserEntity userEntity) {
		userRepository.save(userEntity);
	}

	
	@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByEmail(email);

        if(user != null) {
            List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
            return buildUserForAuthentication(user, authorities);
        } else {
            throw new UsernameNotFoundException("username not found");
        }

    }

    private List<GrantedAuthority> getUserAuthority(Set<RoleEntity> userRoles) {

	    Set<GrantedAuthority> roles = new HashSet<>();

	    userRoles.forEach((role) -> {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        });

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;

    }

    private UserDetails buildUserForAuthentication(UserEntity user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

}
