package com.hs.jobrunr;

import javax.annotation.PostConstruct;

import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.hs.jobrunr.job.SampleJobService;

@SpringBootApplication
@Import(MainConfiguration.class)
public class MainApplication {

	@Autowired
	private JobScheduler jobScheduler;

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

	// 开启一个周期性作业，仅支持按分钟，每小时，每天，每周，每月和每年运行作业
	@PostConstruct
	public void scheduleRecurrently() {
		// Cron.daily();
		// Cron.every5minutes();
		// */1 * * * * ： 每分钟执行
		// 0/1 * * * * * ：每秒执行 ,此表达式只支持5||6位，和quartz的并不同，也不支持问号
		jobScheduler.<SampleJobService> scheduleRecurrently(x -> x.execute("666"), "0/1 * * * * *");

	}
}
