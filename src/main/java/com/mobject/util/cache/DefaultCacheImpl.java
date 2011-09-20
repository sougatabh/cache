package com.mobject.util.cache;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * The Default cache is Lru Cache Implementation using HashMap
 * 
 * @author sougata
 * 
 * @param <K>
 * @param <V>
 */
public class DefaultCacheImpl<K extends Comparable, V> implements Cache<K, V>,
		Serializable {

	/**
	 * The Serial version ID
	 */
	private static final long serialVersionUID = -7955005349828452353L;

	/**
	 * The Memory Cache is synchronized 
	 */
	private Map<K, Item> _mCache = Collections
			.synchronizedMap(new HashMap<K, Item>());
	/**
	 * The lock Object
	 */
	private Object _mlock = new Object();

	/**
	 * The top Item
	 */
	private Item _mTop = new Item();
	/**
	 * The Bottom Item
	 */
	private Item _mBottom = new Item();
	/**
	 * The max size of the Cache
	 */
	private int _mmaxSize;

	public DefaultCacheImpl(int maxObjects) {
		_mmaxSize = maxObjects;
		_mTop.next = _mBottom;
		_mBottom.previous = _mTop;
	}
	
	public void put(K key, V obj) {
		put(key, obj, -1);
	}

	public void put(K key, V value, long validTime) {
		Item current = _mCache.get(key);
		if (current != null) {
			current.value = value;
			if (validTime > 0) {
				current.expires = System.currentTimeMillis() + validTime;
			} else {
				current.expires = Long.MAX_VALUE;
			}
			moveToTop(current);
			return;
		}
		if (_mCache.size() >= _mmaxSize) {
			current = _mBottom.previous;
			_mCache.remove(current.key);
			removeItem(current);
		}
		long expires = 0;
		if (validTime > 0) {
			expires = System.currentTimeMillis() + validTime;
		} else {
			expires = Long.MAX_VALUE;
		}
		Item item = new Item(key, value, expires);
		insertOnTop(item);
		_mCache.put(key, item);
	}


	@SuppressWarnings("unchecked")
	public Element[] getAll() {
		Element p[] = new Element[_mmaxSize];
		int count = 0;
		synchronized (_mlock) {
			Item current = _mTop.next;
			while (current != _mBottom) {
				p[count] = new Element(current.key, current.value);
				++count;
				current = current.next;
			}
		}
		Element np[] = new Element[count];
		System.arraycopy(p, 0, np, 0, count);
		return np;
	}

	@SuppressWarnings("unchecked")
	public V get(K key) {
		Item current = _mCache.get(key);
		if (current == null) {
			return null;
		}
		if (System.currentTimeMillis() > current.expires) {
			_mCache.remove(current.key);
			removeItem(current);
			return null;
		}
		if (current != _mTop.next) {
			moveToTop(current);
		}
		return (V) current.value;
	}


	public void remove(K key) {
		Item current = _mCache.get(key);
		if (current == null) {
			return;
		}
		_mCache.remove(key);
		removeItem(current);
	}

	public int elementsInCache() {
		return _mCache.size();
	}
	
	
	

	private void removeItem(Item item) {
		synchronized (_mlock) {
			item.previous.next = item.next;
			item.next.previous = item.previous;
		}
	}

	private void insertOnTop(Item item) {
		synchronized (_mlock) {
			item.previous = _mTop;
			item.next = _mTop.next;
			_mTop.next.previous = item;
			_mTop.next = item;
		}
	}

	private void moveToTop(Item item) {
		synchronized (_mlock) {
			item.previous.next = item.next;
			item.next.previous = item.previous;
			item.previous = _mTop;
			item.next = _mTop.next;
			_mTop.next.previous = item;
			_mTop.next = item;
		}
	}

	/**
	 * The static inner class to keep Key/Value and ttl
	 * 
	 * @author sougata
	 * 
	 */
	static class Item {
		public Item(Comparable k, Object v, long ttl) {
			key = k;
			value = v;
			expires = ttl;
		}

		public Item() {
		}

		public Comparable key;
		public Object value;
		public long expires;
		public Item previous;
		public Item next;
	}

}