package com.blogspot.nurkiewicz;

import org.apache.commons.configuration.Configuration;
import org.springframework.core.env.PropertySource;

/**
 * @author Tomasz Nurkiewicz
 * @since 07.08.11, 20:20
 */
public class CommonsConfigurationPropertySourceAdapter extends PropertySource<Configuration> {
	public CommonsConfigurationPropertySourceAdapter(Configuration configuration) {
		super(configuration.getClass().getSimpleName(), configuration);
	}

	@Override
	public Object getProperty(String key) {
		return getSource().getProperty(key);
	}
}
