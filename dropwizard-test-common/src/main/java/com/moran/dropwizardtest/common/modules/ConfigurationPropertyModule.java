package com.moran.dropwizardtest.common.modules;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import java.util.Map;
import java.util.Properties;

public class ConfigurationPropertyModule extends AbstractModule {
    protected void configure() {
        this.initializeProperties();
    }

    private void initializeProperties() {
        Properties allProperties = new Properties();
        Properties environmentProperties = getEnvironmentProperties();
        allProperties.putAll(environmentProperties);
        Names.bindProperties(this.binder(), allProperties);
    }

    private Properties getEnvironmentProperties() {
        Map<String, String> envProps = System.getenv();
        Properties props = new Properties();
        props.putAll(envProps);
        return props;
    }

}
