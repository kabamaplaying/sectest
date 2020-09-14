package com.ipc.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class AuthServiceApplication implements CommandLineRunner
{
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    public static void main(String[] args)
    {
        SpringApplication.run(AuthServiceApplication.class, args);
    }
    
    /*
     * (non-Javadoc)
     * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
     */
    @Override
    public void run(String... args) throws Exception
    {
        String password = "12345";
        for (int i = 0; i < 4; i++)
        {
            System.out.println(passwordEncoder.encode(password));
        }
    }
    
}
