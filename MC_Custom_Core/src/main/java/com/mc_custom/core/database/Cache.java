package com.mc_custom.core.database;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The idea of this cache is to pass in a String key following a pattern such as:
 * [type]_[id] example: players_1 or items_123
 * By doing so, we should be able to maintain different types in the same cache and also avoid collisions within the map
 */
public class Cache extends TimerTask {

	public static Cache cache = new Cache();
	private final long EXPIRE_TIME = 60 * 1000; // 1 minute
	private final int MAX_ENTRIES = 500; // Don't know what a good value is...
	private final LinkedHashMap<String, CacheValue> cache_map = new LinkedHashMap<String, CacheValue>((MAX_ENTRIES * 10 / 7), 0.7f, true) {

		/**
		 * Checks to see if the last entry in the list should be removed
		 *
		 * @param eldest the last entry in the list
		 * @return true when the entry should be removed
		 */
		protected boolean removeEldestEntry(Map.Entry eldest) {
			if (eldest.getValue() instanceof CacheValue) {
				CacheValue cv = (CacheValue) eldest;
				return cv.getExpireTime() <= System.currentTimeMillis(); // if expired time is less than the current time then it is expired
			}
			return size() > MAX_ENTRIES;
		}
	};

	/**
	 * Internal Constructor for the Cache Singleton
	 */
	private Cache() {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(this, EXPIRE_TIME, EXPIRE_TIME);
	}

	public static Cache getInstance() {
		return cache;
	}

	public synchronized void expireEntries() {
		long now = System.currentTimeMillis();
		Iterator<String> iterator = cache_map.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			long elapsed = 0;
			elapsed = Long.parseLong(key.split("_")[2]);
			if (now - elapsed > EXPIRE_TIME) {
				iterator.remove();
			}
		}
	}

	public synchronized Object get(String key) {
		return cache_map.get(key).getValue();
	}

	public synchronized void put(String key, CacheValue value) {
		cache_map.put(key, value);
	}

	public synchronized void clearCache() {
		cache_map.clear();
	}

	public synchronized boolean inCache(String key) {
		return cache_map.containsKey(key);
	}

	@Override
	public void run() {
		expireEntries();
	}

	public class CacheValue<V> {
		private V value;
		private long expire_time;
		private long created;

		/**
		 * Creates a new CachedValue
		 *
		 * @param value       The value to cache
		 * @param expire_time The time in milliseconds to expire this value
		 */
		protected CacheValue(V value, long expire_time) {
			this.value = value;
			created = System.currentTimeMillis();
			this.expire_time = created + expire_time;
		}

		/**
		 * Returns the value contained in this CachValue
		 *
		 * @return the value
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Gets the time this value was created
		 *
		 * @return The time this was created
		 */
		public long getCreated() {
			return created;
		}

		/**
		 * Gets the time that this value should expire
		 *
		 * @return Time in milliseconds from January 1, 1970 UTC.
		 */
		public long getExpireTime() {
			return expire_time;
		}
	}
}