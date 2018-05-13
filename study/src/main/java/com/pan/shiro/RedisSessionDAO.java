package com.pan.shiro;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import com.pan.util.JedisUtils;
import com.pan.util.SerializeUtils;


public class RedisSessionDAO extends AbstractSessionDAO {

	private static final String REDIS_SESSION_PREFIX = "redis-session:";

	private byte[] getKey(String key) {
		return (REDIS_SESSION_PREFIX + key).getBytes();
	}
	
	private void saveSession(Session session){
		if(session!=null&&session.getId()!=null){
			byte[] key = getKey(session.getId().toString());
			byte[] value = SerializeUtils.serialize(session);
			JedisUtils.setExpire(key, value, 3600);
		}
	}
	
	@Override
	public void update(Session session) throws UnknownSessionException {
		saveSession(session);
	}

	@Override
	public void delete(Session session) {
		if(session==null||session.getId()==null){
			return;
		}
		byte[] key = getKey(session.getId().toString());
		JedisUtils.decreaseKey(key);
	}

	@Override
	public Collection<Session> getActiveSessions() {
		Set<byte[]> keys = JedisUtils.getByteKeys((REDIS_SESSION_PREFIX+"*").getBytes());
		Set<Session> set=new HashSet<>();
		for (byte[] key : keys) {
			byte[] bs = JedisUtils.get(key);
			set.add((Session) SerializeUtils.deserialize(bs));
		}
		return set;
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable generateSessionId = generateSessionId(session);
		assignSessionId(session, generateSessionId);
		saveSession(session);
		return generateSessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		if(sessionId==null){
			return null;
		}
		byte[] key = getKey(sessionId.toString());
		byte[] bs = JedisUtils.get(key);
		return (Session) SerializeUtils.deserialize(bs);
	}

}
