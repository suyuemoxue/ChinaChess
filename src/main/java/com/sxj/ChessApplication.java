package com.sxj;

import com.sxj.mapper.GameMapper;
import com.sxj.mapper.MessageMapper;
import com.sxj.mapper.PlayMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

//@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@SpringBootApplication
@MapperScan(basePackages = "com.sxj.mapper")

//@EnableAutoConfiguration
//扫描注解
//@ComponentScan({"com.sxj.*"})

public class ChessApplication {
	public static void main(String[] args) {
		SpringApplication.run(ChessApplication.class, args);
	}


	@Autowired
	private MessageMapper messageMapper;
	@Autowired
	private PlayMapper playMapper;

	@Autowired
	private GameMapper gameMapper;
	// springboot启动前执行
	@PostConstruct
	public void postConstruct(){
		System.out.println("--------------SpringApplication.postConstruct--------------");
		Integer delete;
		delete = messageMapper.delete();
		if (delete>0){
			System.out.println("清除Message表");
		}
		delete = playMapper.delete();
		if (delete>0){
			System.out.println("清除play表");
		}
		delete = gameMapper.delete();
		if (delete>0){
			System.out.println("清理game表");
		}
		System.out.println("--------------SpringApplication.postConstruct--------------");
	}
}
