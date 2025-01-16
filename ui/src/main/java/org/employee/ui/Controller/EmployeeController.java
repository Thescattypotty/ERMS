package org.employee.ui.Controller;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.LocalDate;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.employee.ui.Helper.NavigationHelper;
import org.employee.ui.Interface.SwitchablePanel;
import org.employee.ui.Payload.Request.EmployeeRequest;
import org.employee.ui.Payload.Response.DepartmentResponse;
import org.employee.ui.Payload.Response.EmployeeResponse;
import org.employee.ui.Service.AuthenticationService;
import org.employee.ui.Service.DepartementService;
import org.employee.ui.Service.EmployeeService;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmployeeController extends JPanel implements SwitchablePanel{

    private final NavigationHelper navigationHelper;
    private final EmployeeService employeeService;
    private final DepartementService departementService;
    private final AuthenticationService authenticationService;

    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private int currentPage = 0;
    private int pageSize = 10;
    private String sortBy = "name";
    private String direction = "asc";
    private  String departmentId;
    private LocalDate hireDate;
    private String employmentStatus;

    private JButton prevButton, nextButton;
    private JLabel pageLabel;


    @PostConstruct
    public void initComponents(){
        setLayout(new BorderLayout());

        JPanel sideMenu = new JPanel();
        sideMenu.setLayout(new BoxLayout(sideMenu, BoxLayout.Y_AXIS));
        JButton usersButton = new JButton("Users");
        JButton departmentButton = new JButton("Department");
        JButton employeeButton = new JButton("Employee");
        JButton logoutButton = new JButton("Logout");

        usersButton.addActionListener(e -> navigationHelper.navigateTo("users"));
        departmentButton.addActionListener(e -> navigationHelper.navigateTo("department"));
        employeeButton.addActionListener(e -> navigationHelper.navigateTo("employee"));
        logoutButton.addActionListener(e -> logout());

        sideMenu.add(usersButton);
        sideMenu.add(departmentButton);
        sideMenu.add(employeeButton);
        sideMenu.add(logoutButton);

        add(sideMenu, BorderLayout.WEST);

        //Table Model
        tableModel = new DefaultTableModel(new Object[]{"Id", "Full name", "Department Name", "Hire Date", "Employment Status", "Phone Number", "Email", "Adress", "Is User"}, 0);
        employeeTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        add(scrollPane, BorderLayout.CENTER);

        // Panel Buttons
        JPanel buttonPanel = new JPanel();
        JButton createButton = new JButton("Créer Employee");
        JButton updateButton = new JButton("Mettre à Jour Employee");
        JButton deleteButton = new JButton("Supprimer Employee");
        JButton refreshButton = new JButton("Rafraîchir");

        // Ajout des composants de pagination
        prevButton = new JButton("Précédent");
        nextButton = new JButton("Suivant");
        pageLabel = new JLabel("Page: 1");

        buttonPanel.add(prevButton);
        buttonPanel.add(pageLabel);
        buttonPanel.add(nextButton);

        buttonPanel.add(createButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Actions des boutons
        createButton.addActionListener(e -> openCreateEmployeeModal());
        updateButton.addActionListener(e -> openUpdateEmployeeModal());
        deleteButton.addActionListener(e -> deleteSelectedEmployee());
        refreshButton.addActionListener(e -> loadEmployees());


        // Actions des boutons de pagination
        prevButton.addActionListener(e -> {
            if (currentPage > 0) {
                currentPage--;
                loadEmployees();
            }
        });

        nextButton.addActionListener(e -> {
            currentPage++;
            loadEmployees();
        });

    }
    
    public void loadEmployees() {
        log.info("Loading employees");
        List<EmployeeResponse> employees = employeeService.getEmployees(currentPage, pageSize, sortBy, direction, departmentId, hireDate, employmentStatus);
        tableModel.setRowCount(0);
        for(EmployeeResponse emp : employees){
            tableModel.addRow(new Object[]{emp.id(), emp.fullName(), emp.departmentName(), emp.hireDate(), emp.employmentStatus(), emp.phoneNumber(), emp.email(), emp.address(), emp.userId().isEmpty()});
        }
        pageLabel.setText("Page: " + (currentPage + 1));
        prevButton.setEnabled(currentPage > 0);

        List<EmployeeResponse> nextEmployees = employeeService.getEmployees(currentPage + 1, pageSize, sortBy, direction, departmentId, hireDate, employmentStatus);
        nextButton.setEnabled(!nextEmployees.isEmpty());

    }

    private void openCreateEmployeeModal(){
        JDialog createDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Créer Département", true);
        createDialog.setSize(1200, 700);
        createDialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Full name : ");
        JTextField nameField = new JTextField(15);
        JLabel jobTitleLabel = new JLabel("Job Title : ");
        JTextField jobTitleField = new JTextField(15);

        JLabel departmentIdLabel = new JLabel("Department Id : ");
        List<DepartmentResponse> departmentResponses = departementService.getDepartments(0, Integer.MAX_VALUE, "name", "asc");
        Map<String, String> departmentMap = departmentResponses.stream().collect(
            Collectors.toMap(DepartmentResponse::id, DepartmentResponse::name));
        JComboBox<String> departmentComboBox = new JComboBox<>(departmentMap.keySet().toArray(new String[0]));
        //JTextField departmentIdField = new JTextField(15);
        
        JLabel hireDateLabel = new JLabel("Hire Date : ");
        JTextField hireDateField = new JTextField(15);
        JLabel employmentStatusLabel = new JLabel("Employment Status : ");
        JTextField employmentStatusField = new JTextField(15);
        JLabel phoneNumberLabel = new JLabel("Phone Number : ");
        JTextField phoneNumberField = new JTextField(15);
        JLabel emailLabel = new JLabel("Email : ");
        JTextField emailField = new JTextField(15);
        JLabel addressLabel = new JLabel("Address : ");
        JTextField addressField = new JTextField(15);
        JLabel userIdLabel = new JLabel("User Id : ");
        JTextField userIdField = new JTextField(15);

        gbc.gridx = 0; gbc.gridy = 0;
        createDialog.add(nameLabel, gbc);
        gbc.gridx = 1;
        createDialog.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        createDialog.add(jobTitleLabel, gbc);
        gbc.gridx = 1;
        createDialog.add(jobTitleField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        createDialog.add(departmentIdLabel, gbc);
        gbc.gridx = 1;
        createDialog.add(departmentComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        createDialog.add(hireDateLabel, gbc);
        gbc.gridx = 1;
        createDialog.add(hireDateField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        createDialog.add(employmentStatusLabel, gbc);
        gbc.gridx = 1;
        createDialog.add(employmentStatusField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        createDialog.add(phoneNumberLabel, gbc);
        gbc.gridx = 1;
        createDialog.add(phoneNumberField, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        createDialog.add(emailLabel, gbc);
        gbc.gridx = 1;
        createDialog.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        createDialog.add(addressLabel, gbc);
        gbc.gridx = 1;
        createDialog.add(addressField, gbc);

        gbc.gridx = 0; gbc.gridy = 8;
        createDialog.add(userIdLabel, gbc);
        gbc.gridx = 1;
        createDialog.add(userIdField, gbc);

        JButton saveButton = new JButton("Enregistrer");

        gbc.gridx = 0; gbc.gridy = 9;
        gbc.gridwidth = 2;
        createDialog.add(saveButton, gbc);


        saveButton.addActionListener(e -> {
            String selectedDepartmentId = departmentMap.get((String) departmentComboBox.getSelectedItem());
            employeeService.createEmployee(
                new EmployeeRequest(nameField.getText(), jobTitleField.getText(), selectedDepartmentId,
                            LocalDate.parse(hireDateField.getText()), employmentStatusField.getText(), phoneNumberField.getText(),
                            emailField.getText(), addressField.getText(), userIdField.getText())
            );
            createDialog.dispose();
            loadEmployees();
        });
        createDialog.setLocationRelativeTo(this);
        createDialog.setVisible(true);
    }
    private void openUpdateEmployeeModal(){
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un Employee à mettre à jour.", "Avertissement",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String employeeId = (String) tableModel.getValueAt(selectedRow, 0);
        EmployeeResponse selectedEmployee = employeeService.getEmployee(employeeId);
        if (selectedEmployee == null) {
            JOptionPane.showMessageDialog(this, "Employé non trouvé.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog updateDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Mettre à Jour Employee", true);
        updateDialog.setSize(1200, 700);
        updateDialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Full name : ");
        JTextField nameField = new JTextField(selectedEmployee.fullName(), 15);
        JLabel jobTitleLabel = new JLabel("Job Title : ");
        JTextField jobTitleField = new JTextField(selectedEmployee.jobTitle(), 15);


        JLabel departmentIdLabel = new JLabel("Department Id : ");
        //JTextField departmentIdField = new JTextField(selectedEmployee.departmentName(), 15);
        List<DepartmentResponse> departments = departementService.getDepartments(0, Integer.MAX_VALUE, "name", "asc");
        Map<String, String> departmentMap = new HashMap<>();
        for (DepartmentResponse dept : departments) {
            departmentMap.put(dept.name(), dept.id());
        }
        JComboBox<String> departmentComboBox = new JComboBox<>(departmentMap.keySet().toArray(new String[0]));
        departmentComboBox.setSelectedItem(selectedEmployee.departmentName());
        
        JLabel hireDateLabel = new JLabel("Hire Date : ");
        JTextField hireDateField = new JTextField(selectedEmployee.hireDate().toString(), 15);
        JLabel employmentStatusLabel = new JLabel("Employment Status : ");
        JTextField employmentStatusField = new JTextField(selectedEmployee.employmentStatus(), 15);
        JLabel phoneNumberLabel = new JLabel("Phone Number : ");
        JTextField phoneNumberField = new JTextField(selectedEmployee.phoneNumber(), 15);
        JLabel emailLabel = new JLabel("Email : ");
        JTextField emailField = new JTextField(selectedEmployee.email(), 15);
        JLabel addressLabel = new JLabel("Address : ");
        JTextField addressField = new JTextField(selectedEmployee.address(), 15);
        JLabel userIdLabel = new JLabel("User Id : ");
        JTextField userIdField = new JTextField(selectedEmployee.userId(), 15);

        gbc.gridx = 0; gbc.gridy = 0;
        updateDialog.add(nameLabel, gbc);
        gbc.gridx = 1;
        updateDialog.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        updateDialog.add(jobTitleLabel, gbc);
        gbc.gridx = 1;
        updateDialog.add(jobTitleField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        updateDialog.add(departmentIdLabel, gbc);
        gbc.gridx = 1;
        updateDialog.add(departmentComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        updateDialog.add(hireDateLabel, gbc);
        gbc.gridx = 1;
        updateDialog.add(hireDateField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        updateDialog.add(employmentStatusLabel, gbc);
        gbc.gridx = 1;
        updateDialog.add(employmentStatusField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        updateDialog.add(phoneNumberLabel, gbc);
        gbc.gridx = 1;
        updateDialog.add(phoneNumberField, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        updateDialog.add(emailLabel, gbc);
        gbc.gridx = 1;
        updateDialog.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        updateDialog.add(addressLabel, gbc);
        gbc.gridx = 1;
        updateDialog.add(addressField, gbc);

        gbc.gridx = 0; gbc.gridy = 8;
        updateDialog.add(userIdLabel, gbc);
        gbc.gridx = 1;
        updateDialog.add(userIdField, gbc);

        JButton saveButton = new JButton("Enregistrer les modifications");

        gbc.gridx = 0; gbc.gridy = 9;
        gbc.gridwidth = 2;
        updateDialog.add(saveButton, gbc);

        saveButton.addActionListener(e -> {
            try {
                String selectedDepartmentId = departmentMap.get((String) departmentComboBox.getSelectedItem());
                employeeService.updateEmployee(employeeId,
                    new EmployeeRequest(nameField.getText(), jobTitleField.getText(), selectedDepartmentId,
                            LocalDate.parse(hireDateField.getText()), employmentStatusField.getText(), phoneNumberField.getText(),
                            emailField.getText(), addressField.getText(), userIdField.getText())
                );
                updateDialog.dispose();
                loadEmployees();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(updateDialog, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        updateDialog.setLocationRelativeTo(this);
        updateDialog.setVisible(true);
    }

    private void deleteSelectedEmployee(){
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un Employee à supprimer.", "Avertissement",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String deptId = (String) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer ce Employee ?",
                "Confirmer Suppression", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            employeeService.deleteEmployee(deptId);
            loadEmployees();
        }
    }

    private void logout(){
        authenticationService.logout();
        navigationHelper.navigateTo("login");
    }

    @Override
    public String getPanelName() {
        return "employee";
    }

    @Override
    public JPanel getPanel() {
        return this;
    }
}
