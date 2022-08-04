package com.babl.smbspringcloudgateway.redis.service;

import com.babl.smbummodel.redis.model.OauthAccessToken;
import com.babl.smbummodel.redis.repo.OAuthTokenRepo;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class OAthTokenRepoImpl implements OAuthTokenRepo {
	private RedisTemplate<String, OauthAccessToken> redisTemplate;
	private HashOperations hashOperations;
	private String redisObjectName = "OAuthToken";

	public OAthTokenRepoImpl(RedisTemplate<String, OauthAccessToken> redisTemplate) {
		this.redisTemplate = redisTemplate;
		hashOperations = redisTemplate.opsForHash();
	}

	@Override
	public void save(OauthAccessToken oAuthToken) {
		System.out.println(oAuthToken.getTimeout());
		hashOperations.put(redisObjectName,oAuthToken.getToken() , oAuthToken);
	}

	@Override
	public Map<String, OauthAccessToken> findAll() {
		return hashOperations.entries(redisObjectName);
	}

	@Override
	public OauthAccessToken findByTokenId(String id) {
		return (OauthAccessToken) hashOperations.get(redisObjectName, id);
	}

	@Override
	public void update(OauthAccessToken oAuthToken) {
		save(oAuthToken);
	}

	@Override
	public void delete(String id) {
		hashOperations.delete(redisObjectName, id);
	}
}
