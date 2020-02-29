package ca.amazing.exchangeproject.config;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

@Configuration
public class WebConfig {

  @Bean
  public Hibernate5Module datatypeHibernateModule() {
    return new Hibernate5Module();
  }
  
  @Bean
  public Jackson2ObjectMapperBuilderCustomizer guavaJacksonModule() {
      return builder -> builder.modules( new GuavaModule() );
  }

}
