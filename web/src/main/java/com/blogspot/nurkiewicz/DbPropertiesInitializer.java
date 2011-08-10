package com.blogspot.nurkiewicz;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.DatabaseConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import javax.sql.DataSource;

/**
 * @author Tomasz Nurkiewicz
 * @since 07.08.11, 20:04
 */
public class DbPropertiesInitializer implements ApplicationContextInitializer<ConfigurableWebApplicationContext> {

	public void initialize(ConfigurableWebApplicationContext ctx) {
		try {
			ctx.getEnvironment().getPropertySources().addFirst(
					new CommonsConfigurationPropertySourceAdapter(
							databaseConfiguration()
					)
			);
		} catch (ConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	private Configuration databaseConfiguration() throws ConfigurationException {
		return new DatabaseConfiguration(configurationDataSource(), "Configuration", "key", "value");
	}

	private DataSource configurationDataSource() throws ConfigurationException {
		final PropertiesConfiguration bootstrapProps = new PropertiesConfiguration(getClass().getResource("/bootstrap.properties"));
		final BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("org.h2.Driver");
		ds.setUrl(bootstrapProps.getProperty("cfgJdbcUrl").toString());
		ds.setUsername(bootstrapProps.getProperty("cfgJdbcUserName").toString());
		return ds;
	}

}
