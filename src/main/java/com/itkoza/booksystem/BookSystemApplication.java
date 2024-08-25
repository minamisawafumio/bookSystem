package com.itkoza.booksystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.itkoza.fm.businessLogic.common.NetUtil;

@SpringBootApplication
public class BookSystemApplication {

	//https://www.youtube.com/watch?v=LGugFPdo-tU&t=1987s
	public static void main(String[] args) {

		String ip = NetUtil.getInstance().getIp();
		System.out.println("http://" +  ip + ":18080/");
		SpringApplication.run(BookSystemApplication.class, args);
	}

}
