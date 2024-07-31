package org.ss.vendorapi.modal;

import java.io.Serializable;
import java.util.Collection;

import org.json.JSONObject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "CONSUMERMASTER")
public class User implements Serializable,UserDetails {/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "kno")
	private String kno;
	
	@Column(name = "discom_name")
	private String discomName;
	
	@Column(name = "u_password")
	private String password;
	
	@Column(name = "email_id")
	private String emailId;
	
	private long mobile;
	
	@Transient
	private String username;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return  this.password;
	}   

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public String toString() {
		JSONObject returnObj = new JSONObject();
		 returnObj.put("kno", this.kno);
         returnObj.put("discomName", this.discomName);
         returnObj.put("password", this.password);
       
        return returnObj.toString();
	}
	
}
