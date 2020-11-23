package com.hs.jobrunr.api;

import java.time.Duration;
import java.time.Instant;

import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hs.jobrunr.job.SampleJobService;

@RestController
public class JobController {

	@Autowired
	private JobScheduler jobScheduler;

	@Autowired
	private SampleJobService sampleJobService;

	@GetMapping("/run-job")
	public String runJob(@RequestParam(value = "name", defaultValue = "Hello World") String name) {

		// jobScheduler.enqueue(() -> sampleJobService.execute(name));

		// 周期性作业，仅支持按分钟，每小时，每天，每周，每月和每年运行作业 详见 Cron.class
		jobScheduler.scheduleRecurrently(() -> sampleJobService.execute(name), "*/1 * * * *");

		return "Job is enqueued.";
	}

	@GetMapping("/schedule-job")
	public String scheduleJob(@RequestParam(value = "name", defaultValue = "Hello World") String name, @RequestParam(value = "when", defaultValue = "PT3H") String when) {
		/**
		 * P 开始标记
		 * 
		 * 1Y - 一年
		 * 
		 * 2M - 两个月
		 * 
		 * 10D - 十天
		 * 
		 * T - 时间和日期分的割标记
		 * 
		 * 2H - 两个小时
		 * 
		 * 30M - 三十分钟
		 * 
		 * 15S 十五秒钟
		 * 
		 * 例子，注意如果没有年月日，"T"也不能省略
		 * 
		 * P1DT1M - 一天一分钟执行一次
		 * 
		 * P1W - 一周执行一次
		 * 
		 * PT1H - 一小时执行一次
		 * 
		 * PT10S - 十秒执行一次
		 */
		// IOS8601 时间格式
		// 相关博客：https://blog.csdn.net/qq_42006120/article/details/101992163
		jobScheduler.schedule(() -> sampleJobService.execute(name), Instant.now().plus(Duration.parse(when)));

		return "Job is scheduled.";
	}

}
