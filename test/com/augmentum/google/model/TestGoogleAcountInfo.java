package com.augmentum.google.model;

import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import org.junit.Test;

public class TestGoogleAcountInfo extends BaseTest<GoogleAccountInfo>{

	@Test
	public void testFindByUnique(){
		GoogleAccountInfo record = GoogleAccountInfo.dao.findByUnique();
		assertNotNull(record);
	}
	
	@Test
	public void testUUID(){
		System.out.println(UUID.randomUUID());
	}
}
