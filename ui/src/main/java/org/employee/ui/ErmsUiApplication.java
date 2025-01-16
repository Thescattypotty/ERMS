package org.employee.ui;

import javax.swing.SwingUtilities;

import org.employee.ui.Helper.NavigationHelper;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ErmsUiApplication {

    public static ConfigurableApplicationContext applicationContext;
	public static void main(String[] args) {

        SpringApplicationBuilder builder = new SpringApplicationBuilder(ErmsUiApplication.class);
        
        builder.headless(false);
        builder.web(WebApplicationType.NONE);
        
        applicationContext = builder.run();
        
        SwingUtilities.invokeLater(() -> {
            NavigationHelper navigationHelper = applicationContext.getBean(NavigationHelper.class);
            navigationHelper.startApplication();
        });
	}

}
