package org.employee.ui.Controller;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.employee.ui.Helper.NavigationHelper;
import org.employee.ui.Interface.SwitchablePanel;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DepartmentController extends JPanel implements SwitchablePanel{

    private final NavigationHelper navigationHelper;

    @PostConstruct
    private void initComponents() {
        JButton button = new JButton("Department Data");
        button.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Data: " + "cc");
        });
        JButton button1 = new JButton("Go To Login");
        button1.addActionListener(e -> {
            navigationHelper.navigateTo("login");
        });
        add(button);
        add(button1);
    }

    @Override
    public String getPanelName() {
        return "department";
    }

    @Override
    public JPanel getPanel() {
        return this;
    }
}
