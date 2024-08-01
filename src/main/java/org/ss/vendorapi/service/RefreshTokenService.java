package org.ss.vendorapi.service;

import java.time.Instant;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ss.vendorapi.entity.UserMasterEntity;
import org.ss.vendorapi.exceptions.TokenRefreshException;
import org.ss.vendorapi.exceptions.UserNotFoundException;
import org.ss.vendorapi.modal.RefreshToken;
import org.ss.vendorapi.modal.User;
import org.ss.vendorapi.repository.RefreshTokenRepository;
import org.ss.vendorapi.repository.UserMasterRepository;
import org.ss.vendorapi.repository.UserRepository;

@Service
public class RefreshTokenService {

	@Value("${jwt.refreshExpirationMs}")
	private Long refreshTokenDurationMs;

	@Value("${jwt.expirationMs}")
	private Long jwtExpirationMs;

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Autowired
	private UserRepository userRepository;



	@Autowired
	private UserMasterRepository registerUserRepository;

	public Optional<RefreshToken> findByToken(String token) {
		return refreshTokenRepository.findByToken(token);
	}

	public RefreshToken createRefreshToken(String userId) {
		RefreshToken refreshToken = new RefreshToken();
		User objUser = userRepository.findByKnoAndDiscomName(userId.split("_")[0],userId.split("_")[1]).orElseThrow(() -> new UserNotFoundException("User not found!!"));
		refreshToken.setUser(objUser.getKno() + "_" + objUser.getDiscomName());
		refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs*1000));

		String encodedJwtExpirtionMS = new String(Base64.getEncoder().encode(String.valueOf(jwtExpirationMs*1000).getBytes()));

		refreshToken.setToken(UUID.randomUUID().toString() + "." + encodedJwtExpirtionMS);

		refreshToken = refreshTokenRepository.save(refreshToken);

		return refreshToken;
	}

	public RefreshToken createRefreshTokenByUserId(String userId) {
		RefreshToken refreshToken = new RefreshToken();
		UserMasterEntity objUser=registerUserRepository.findByUserId(userId);
		if(objUser==null) 
		{
			throw new UserNotFoundException("user not found");
		}
		else 
		{
			refreshToken.setUser(objUser.getUserId());
			refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs*1000));

			String encodedJwtExpirtionMS = new String(Base64.getEncoder().encode(String.valueOf(jwtExpirationMs*1000).getBytes()));

			refreshToken.setToken(UUID.randomUUID().toString() + "." + encodedJwtExpirtionMS);

			refreshToken = refreshTokenRepository.save(refreshToken);

		}
		return refreshToken;
	}

	public RefreshToken createRefreshTokenByMobileNumber(String mobileNumber) {
		RefreshToken refreshToken = new RefreshToken();
		UserMasterEntity objUser=registerUserRepository.findByPhone(mobileNumber);
		if(objUser==null) {
			throw new UserNotFoundException("user not found");
		}else {
			refreshToken.setUser(objUser.getPhone());
			refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs*1000));

			String encodedJwtExpirtionMS = new String(Base64.getEncoder().encode(String.valueOf(jwtExpirationMs*1000).getBytes()));

			refreshToken.setToken(UUID.randomUUID().toString() + "." + encodedJwtExpirtionMS);

			refreshToken = refreshTokenRepository.save(refreshToken);

		}
		return refreshToken;
	}

	public RefreshToken verifyExpiration(RefreshToken token) {
		String userId = token.getUser();
		if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
			refreshTokenRepository.deleteByUser(userId);
			throw new TokenRefreshException(token.getToken(),
					"Refresh token was expired. Please make a new signin request");
		} else {
			refreshTokenRepository.deleteByUser(userId);
			return this.createRefreshToken(userId);
		}
	}

	@Transactional
	public int deleteByUserId(String userId) {
		User objUser = userRepository.findByKnoAndDiscomName(userId.split("_")[0],userId.split("_")[1]).orElseThrow(() -> new UserNotFoundException("User not found!!"));

		return refreshTokenRepository.deleteByUser(objUser.getKno() + "_" + objUser.getDiscomName());
	}

	public RefreshToken createRefreshTokenByEmail(String email) {
		RefreshToken refreshToken = new RefreshToken();
		UserMasterEntity objUser=registerUserRepository.findByEmail(email);
		if(objUser==null) {
			throw new UserNotFoundException("user not found");
		}else {
			refreshToken = refreshTokenRepository.findByUser(email).orElse(new RefreshToken());
			refreshToken.setUser(objUser.getEmail());
			refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs*1000));

			String encodedJwtExpirtionMS = new String(Base64.getEncoder().encode(String.valueOf(jwtExpirationMs*1000).getBytes()));

			refreshToken.setToken(UUID.randomUUID().toString() + "." + encodedJwtExpirtionMS);

			refreshToken = refreshTokenRepository.save(refreshToken);

		}
		return refreshToken;
	
	
	}}

	

