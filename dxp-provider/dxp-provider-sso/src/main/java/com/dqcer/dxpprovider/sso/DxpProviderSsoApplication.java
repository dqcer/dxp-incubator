package com.dqcer.dxpprovider.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.dqcer")
public class DxpProviderSsoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DxpProviderSsoApplication.class, args);
    }


//    @Bean
//    public GenericConversionService getGenericConversionService(@Autowired GenericConversionService conversionService) {
//        conversionService.addConverter(new DateConverter());
//        System.out.println("类型转换已加入！");
//        return conversionService;
//
//    }



}
