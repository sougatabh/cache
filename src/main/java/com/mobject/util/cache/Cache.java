package com.mobject.util.cache;


/**
 * The Cache interface for all Cache operation
 * @author sougata
 *
 * @param <K>
 * @param <V>
 */
public interface Cache<K extends Comparable, V> {
	  
	/**
	 * Used Put the Object for the key value in the cache.
	 * @param key
	 * @param value
	 */
	   void put(K key, V value);
	  /**
	   * Used to get the value stored in the cache earlier,
	   * returns null if data not found for the given key
	   * @param key
	   * @return
	   */
	   V get(K key);
	   /**
	    * Used to keep the data for a expire time ,if expire time is <=0 data never 
	    * gets expires, else it get expires after specific millisecond
	    * @param key
	    * @param object
	    * @param expiretime
	    */
	   void put(K key, V object, long expiretime);
	   /**
	    * Used to remove the element from the cache
	    * @param key
	    */
	   void remove(K key);
	   /**
	    * Used to get the all the element<key/value> of the cache
	    * @return
	    */
	   Element[] getAll();
	   /**
	    * Get the size of the Cache
	    * @return
	    */
	   int elementsInCache();
}