package org.employee.ui.Helper;

import org.employee.ui.ErmsUiApplication;
import org.employee.ui.Controller.MainController;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NavigationHelper{
    
    public void navigateTo(String panelName){
        log.info("Navigating to panel: {}", panelName);
        ErmsUiApplication.applicationContext.getBean(MainController.class).switchToPanel(panelName);
    }

    public void startApplication(){
        ErmsUiApplication.applicationContext.getBean(MainController.class).setVisible(true);
        navigateTo("login");
    }
}
