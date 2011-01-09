package com.blogspot.nurkiewicz;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Tomasz Nurkiewicz
 * @since 09.01.11, 18:17
 */
@Configuration
public class ContextConfiguration {

	@Bean
	public DataSource dataSource() {
		final BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("org.h2.Driver");
		ds.setUrl("jdbc:h2:~/workspace/h2/spring-noxmal;DB_CLOSE_ON_EXIT=FALSE;TRACE_LEVEL_FILE=4;AUTO_SERVER=TRUE");
		ds.setUsername("sa");
		return ds;
	}

}
