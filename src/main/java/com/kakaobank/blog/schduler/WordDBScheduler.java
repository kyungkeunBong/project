package com.kakaobank.blog.schduler;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.kakaobank.blog.repository.WordRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WordDBScheduler {
	private final WordRepository wr;
	
	public String getPartitionCode() {
		Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT+9"));
		calendar.setTimeInMillis(new Date().getTime());
		return String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
	}
	
	@PostConstruct
	public void deleteWordDB() {
		System.out.println("delete Start : " + getPartitionCode());
		wr.deleteByPartitionCode(getPartitionCode());
	}
	
	@Scheduled(cron="0 0 0 * * *")
	public void scheduleJob() {
		System.out.println("delete Start : " + getPartitionCode());
		this.deleteWordDB();
	}
}
