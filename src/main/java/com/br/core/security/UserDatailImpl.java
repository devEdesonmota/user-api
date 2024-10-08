package com.br.core.security;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.br.domain.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class UserDatailImpl implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UUID userId;
	
	private Boolean active;
	
	private String nome;

	@JsonIgnore
	private String password;
	
	private String matricula;

	private String email;

	private UUID departmentId;
	
	private Collection<? extends GrantedAuthority> authorities; 
	
	public static UserDatailImpl build (User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getAuthority()))
				.collect(Collectors.toList());
		return new UserDatailImpl(
			user.getUserId(),
			user.getActive(),
			user.getNome(),
			user.getPassword(),
			user.getMatricula(),
			user.getEmail(),
			user.getDepartmentId(),
			authorities	
		);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	@Override
	public String getPassword()  {
		return this.password; 
	}

	@Override
	public String getUsername() { 
		return this.matricula;
	}
	
	@Override
	public boolean isAccountNonExpired() { return true; }

	@Override
	public boolean isAccountNonLocked()  { return true; }
		
	@Override
	public boolean isCredentialsNonExpired()  { return true; }

	@Override
	public boolean isEnabled()  { return true; }
}

