package com.mobject.util.cache;

import com.mobject.util.cache.Cache;
import com.mobject.util.cache.CacheManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class CacheTest extends TestCase {

	
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public CacheTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( CacheTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testBasicCache()throws Exception
    {
        CacheManager manager = CacheManager.getInstance();
        Cache lruCache = manager.getCache();
        lruCache.put("Name", "Sougata");
        String value  = (String)lruCache.get("Name");
        assertEquals("Value does not match","Sougata", value);
    }
    
    public void testCacheSize()throws Exception
    {
        CacheManager manager = CacheManager.getInstance();
        Cache lruCache = manager.getCache();
        lruCache.put("Name", "Sougata");
        lruCache.put("Company", "Yahoo");
        assertEquals("Cache Size Does Not Match",2, lruCache.elementsInCache());
    }
    
    public void testRemoveCache()throws Exception
    {
        CacheManager manager = CacheManager.getInstance();
        
        Cache lruCache = manager.getCache();
        lruCache.put("Name", "Sougata");
        lruCache.put("Company", "Yahoo");
        lruCache.remove("Name");
        assertEquals("Cache Size Does Not Match",1, lruCache.elementsInCache());
    }
    
    
    public void testTtlCache()throws Exception
    {
        CacheManager manager = CacheManager.getInstance();
        
        Cache lruCache = manager.getCache();
        lruCache.put("Name", "Sougata",2000);
        
        Thread.sleep(2001);
        assertEquals("Cache Still Keepijg the data",null, lruCache.get("Name"));
    }
    
    
    public void testTtlWithinCache()throws Exception
    {
        CacheManager manager = CacheManager.getInstance();
        
        Cache lruCache = manager.getCache();
        lruCache.put("Name", "Sougata",2000);
        
        Thread.sleep(1990);
        assertEquals("Cache Still Keepijg the data","Sougata", lruCache.get("Name"));
    }
    
    
    
    
    
    
    public void testCache()throws Exception
    {
        CacheManager manager = CacheManager.getInstance();
        Cache lruCache = manager.getCache();
        lruCache.put("Name", "Sougata",1000);
        //Thread.sleep(1001);
        CacheManager manager1 = CacheManager.getInstance();
        Cache lruCache1 = manager1.getCache();
        
        String value  = (String)lruCache.get("Name");
        assertEquals("Cache Does not Have Proper Value","Sougata", lruCache.get("Name"));
        

    }

    
    
    
}
