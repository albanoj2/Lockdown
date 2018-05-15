package com.lockdown.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DataSynchronizerService {
	
	@Autowired
	private DataSynchronizer synchronizer;

	@Scheduled(fixedRate = 10000)
	public void synchronize() {
		synchronizer.synchornize();
	}
}
