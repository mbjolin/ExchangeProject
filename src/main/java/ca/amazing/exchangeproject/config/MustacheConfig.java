package ca.amazing.exchangeproject.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;

import com.samskivert.mustache.Mustache;

/**
 * https://stackoverflow.com/questions/29766577/spring-mvc-and-mustache-change-mustaches-compiler-default-null-value
 */
@Configuration
public class MustacheConfig {

  @Bean
  public BeanPostProcessor mustacheHackerBeanPostProcessor() {
      return new BeanPostProcessor() {
          private static final String BEAN_NAME = "mustacheCompiler";

          @Override
          public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
              return bean;
          }

          @Override
          public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
              if (ClassUtils.isAssignable(bean.getClass(), Mustache.Compiler.class) || BEAN_NAME.equals(beanName)) {
                  Mustache.Compiler compiler = (Mustache.Compiler) bean;
                  return compiler.defaultValue("").nullValue("");
              }

              return bean;
          }
      };
  }
}