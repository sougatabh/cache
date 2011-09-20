package com.mobject.util.cache;

/**
 * 
 * @author sougata
 * This is the Cache Manager, returns the Cache object and also responsible to setting the Cache size
 *
 */
public class CacheManager {

	private static CacheManager _instance;
	private static Cache _mCache;
	private static final String CACHE_SIZE_KEY = "CACHE_SIZE";
	
	
    private int _cacheSize = 0;
    private static Object _mlock = new Object(); 
	/**
	 * Making sure Constructor can't be called from the outside 
	 */
	private CacheManager(){
		int cz = Integer.parseInt(CacheUtility.getValue(CACHE_SIZE_KEY));
		this.setCacheSize(cz);
	}
	
	
	/**
	 * Returning the Single instance of the CacheManager
	 * @return
	 */
	public static CacheManager getInstance(){
		if(_instance == null){
			synchronized (_mlock) {
				_instance = new CacheManager();	
				
			}
			
		}
		return _instance;
	}
	/**
	 * Responsible to setting cache Size, if Not set cacheSize = MAX_CACHE_SIZE
	 * @param cacheSize
	 */
	private void setCacheSize(int cacheSize){
		if(cacheSize <=0){
			throw new RuntimeException("Cache Size can't be less than/equals to 0");
		}
		synchronized (_mlock) {
			this._cacheSize = cacheSize;	
		}
		
	}
	
	/**
	 * Get the Cache Object
	 * @return
	 */
	public Cache getCache(){
		
		if(_mCache == null){
			synchronized (_mlock) {
				_mCache = new DefaultCacheImpl(_cacheSize);
			}
			
		}
		return _mCache;
	}
}
