package com.pan.shiro;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import com.pan.util.JedisUtils;
import com.pan.util.SerializeUtils;


/**
 * @author panzhigao
 */
public class RedisSessionDAO extends AbstractSessionDAO {

	private static final String REDIS_SESSION_PREFIX = "redis-session:";
	
	public static final int DEFAULT_SECONDS=-1;
	
	private int seconds=DEFAULT_SECONDS;
	
	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
		
	private byte[] getKey(String key) {
		return (REDIS_SESSION_PREFIX + key).getBytes();
	}
	
	/**
	 * 保存session并设置过期时间
	 * @param session
	 */
	private void saveSession(Session session){
		if(session!=null&&session.getId()!=null){
			byte[] key = getKey(session.getId().toString());
			byte[] value = SerializeUtils.serialize(session);
			JedisUtils.setExpire(key, value, seconds);
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

	/**
	 * 每次扫描的数量
	 */
	private final static int SCAN_COUNT=1000;

	@Override
	public Collection<Session> getActiveSessions() {
		Set<Session> set=new HashSet<>();
		List<byte[]> scan = JedisUtils.scan((REDIS_SESSION_PREFIX + "*").getBytes(), SCAN_COUNT);
		scan.forEach(t->set.add((Session) SerializeUtils.deserialize(t)));
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
