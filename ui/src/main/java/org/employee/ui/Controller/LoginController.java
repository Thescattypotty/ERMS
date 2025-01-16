package org.employee.ui.Controller;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import org.employee.ui.Helper.NavigationHelper;
import org.employee.ui.Interface.SwitchablePanel;
import org.employee.ui.Service.AuthenticationService;
import org.employee.ui.Payload.Request.LoginRequest;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginController extends JPanel implements SwitchablePanel{

    private final NavigationHelper navigationHelper;
    private final AuthenticationService authenticationService;

    @PostConstruct
    private void initComponents() {
        
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel emailLabel = new JLabel("Email : ");
        add(emailLabel, gbc);

        gbc.gridx = 1;
        JTextField emailField = new JTextField(15);
        add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        JLabel passwordLabel = new JLabel("Password : ");
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(15);
        add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JLabel messageLabel = new JLabel("");
        add(messageLabel, gbc);

        gbc.gridy = 3; gbc.gridwidth = 1;
        JButton loginButton = new JButton("Connect");
        loginButton.addActionListener(e -> {
            try {
                authenticationService.authenticate(
                    new LoginRequest(emailField.getText(), new String(passwordField.getPassword()))
                );
                navigationHelper.navigateTo("department");
            } catch (Exception ex) {
                messageLabel.setText(ex.getMessage());
            }
        });
        add(loginButton, gbc);
    }
    
    @Override
    public String getPanelName() {
        return "login";
    }

    @Override
    public JPanel getPanel() {
        return this;
    }
}
