package com.jos.community.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * Save Spring ApplicationContext to static variable, Get ApplicaitonContext in anywhere.
 */
public class SpringContextHolder implements ApplicationContextAware,DisposableBean{

	private static ApplicationContext applicationContext;
	
	private static Logger logger = LoggerFactory.getLogger(SpringContextHolder.class);
	
	/**
	 * Implement ApplicationContextAware Interface, Inject Context to Static Variable.
	 */
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		logger.debug("Inject ApplicationContext to SpringContextHolder:" + applicationContext);
		if (SpringContextHolder.applicationContext != null) {
			logger.warn("ApplicationContext in SpringContextHolder will be replace, the original ApplicationContext is:"
					+ SpringContextHolder.applicationContext);
		}
		SpringContextHolder.applicationContext = applicationContext;
	}

	/**
	 * get bean from static applicationContext by name
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName){
		assertContextInjected();
		return (T) SpringContextHolder.applicationContext.getBean(beanName);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz){
		assertContextInjected();
		return (T) SpringContextHolder.applicationContext.getBean(clazz);
	}
	/**
	 * Get ApplicationContext saved as static variable.
	 */
	public static ApplicationContext getApplicationContext() {
		assertContextInjected();
		return applicationContext;
	}
	
	/**
	 * Ensure ApplicationContext is not null
	 */
	private static void assertContextInjected() {
		if (applicationContext == null) {
			throw new IllegalStateException("applicaitonContext have not been injected,Please define SpringContextHolder in applicationContext.xml");
		}
	}

	public void destroy() throws Exception {
		SpringContextHolder.clear();
	}
	
	/**
	 * Reset static ApplicationContext to Null.
	 */
	public static void clear() {
		logger.debug("Clear ApplicationContext in SpringContextHolder:" + applicationContext);
		applicationContext = null;
	}
}
