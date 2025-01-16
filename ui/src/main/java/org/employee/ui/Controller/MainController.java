package org.employee.ui.Controller;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

import org.employee.ui.Interface.SwitchablePanel;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class MainController extends JFrame{

    private final Map<String, JPanel> panels = new HashMap<>();

    private CardLayout cardLayout;

    private final LoginController loginController;
    private final DepartmentController departmentController;

    @PostConstruct
    public void init(){
        setTitle("Main Page");
        setLayout(new GridBagLayout());
        setSize(700, 500);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(
            (dimension.width - this.getWidth())/2, 
            (dimension.height - this.getHeight())/2
            );
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();

        setLayout(cardLayout);

        registerPanel(loginController);
        registerPanel(departmentController);
    }

    private void registerPanel(SwitchablePanel panel){
        panels.put(panel.getPanelName(), panel.getPanel());
        add(panel.getPanel(), panel.getPanelName());
    }

    public void switchToPanel(String panelName){
        cardLayout.show(getContentPane(), panelName);
    }
}

