package org.ss.vendorapi.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.ss.vendorapi.entity.UserMasterEntity;

public class CustomUserDetails implements UserDetails {
	
	private UserMasterEntity userMasterEntity;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(userMasterEntity.getRole());
		return List.of(simpleGrantedAuthority);
	}

	public CustomUserDetails(UserMasterEntity userMasterEntity) {
		super();
		this.userMasterEntity = userMasterEntity;
	}

	@Override
	public String getPassword() {
		return userMasterEntity.getPassword();
	}

	@Override
	public String getUsername() {
		return userMasterEntity.getUserId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
