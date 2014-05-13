package com.augmentum.google.model;

import java.util.UUID;

import org.junit.Test;

public class TestGoogleAcountInfo extends BaseTest<GoogleAccountInfo>{

	@Test
	public void testFindByUnique(){
		System.out.println("====DAO : " + GoogleAccountInfo.dao.findById(1L));
		GoogleAccountInfo record = GoogleAccountInfo.dao.findByUnique();
		System.out.println(record);
		assertNotNull(record);
	}
	
	@Test
	public void testUUID(){
		System.out.println(UUID.randomUUID());
	}
	
	
	
	
	
	
}
