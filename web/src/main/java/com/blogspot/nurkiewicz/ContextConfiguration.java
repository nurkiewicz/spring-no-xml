package com.blogspot.nurkiewicz;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.sql.DataSource;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.AbstractJmsListeningContainer;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.jmx.export.annotation.AnnotationMBeanExporter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * @author Tomasz Nurkiewicz
 * @since 09.01.11, 18:17
 */
@Configuration
public class ContextConfiguration {

	@Resource
	private FooRequestProcessor fooRequestProcessor;

	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		final BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("org.h2.Driver");
		ds.setUrl("jdbc:h2:~/workspace/h2/spring-noxmal;DB_CLOSE_ON_EXIT=FALSE;TRACE_LEVEL_FILE=4;AUTO_SERVER=TRUE");
		ds.setUsername("sa");
		return ds;
	}

	@Bean
	public JdbcOperations jdbcOperations() {
		return new JdbcTemplate(dataSource());
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}

	@Bean
	public ConnectionFactory jmsConnectionFactory() {
		final ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
		factory.setBrokerURL("tcp://localhost:61616");
		return new PooledConnectionFactory(factory);
	}

	@Bean
	public Queue requestsQueue() {
		return new ActiveMQQueue("requests");
	}

	@Bean
	public JmsOperations jmsOperations() {
		final JmsTemplate jmsTemplate = new JmsTemplate(jmsConnectionFactory());
		jmsTemplate.setDefaultDestination(requestsQueue());
		return jmsTemplate;
	}

	@Bean
	public AbstractJmsListeningContainer jmsContainer() {
		final DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
		container.setConnectionFactory(jmsConnectionFactory());
		container.setDestination(requestsQueue());
		container.setSessionTransacted(true);
		container.setConcurrentConsumers(5);
		container.setMessageListener(messageListenerAdapter());
		return container;
	}

	private MessageListenerAdapter messageListenerAdapter() {
		final MessageListenerAdapter adapter = new MessageListenerAdapter(fooRequestProcessor);
		adapter.setDefaultListenerMethod("process");
		return adapter;
	}

	@Bean
	public AnnotationMBeanExporter annotationMBeanExporter() {
		return new AnnotationMBeanExporter();
	}

	@Bean
	public TransactionAttributeSource annotationTransactionAttributeSource() {
		return new AnnotationTransactionAttributeSource();
	}

	@Bean
	public TransactionInterceptor transactionInterceptor() {
		return new TransactionInterceptor(transactionManager(), annotationTransactionAttributeSource());
	}

}
