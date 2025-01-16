package org.employee.ui.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import org.employee.ui.Helper.NavigationHelper;
import org.employee.ui.Interface.SwitchablePanel;
import org.employee.ui.Payload.Request.DepartmentRequest;
import org.employee.ui.Payload.Response.DepartmentResponse;
import org.employee.ui.Service.AuthenticationService;
import org.employee.ui.Service.DepartementService;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DepartmentController extends JPanel implements SwitchablePanel{

    private final NavigationHelper navigationHelper;
    private final AuthenticationService authenticationService;
    private final DepartementService departementService;

    private JTable departmentTable;
    private DefaultTableModel tableModel;
    private int currentPage = 0;
    private int pageSize = 10;
    private String sortBy = "name";
    private String direction ="asc";

    private JButton prevButton, nextButton;
    private JLabel pageLabel;


    @PostConstruct
    private void initComponents() {
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

        // Table Model
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name"}, 0);
        departmentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(departmentTable);
        add(scrollPane, BorderLayout.CENTER);

        // Panel des boutons
        JPanel buttonPanel = new JPanel();
        JButton createButton = new JButton("Créer Département");
        JButton updateButton = new JButton("Mettre à Jour Département");
        JButton deleteButton = new JButton("Supprimer Département");
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
        createButton.addActionListener(e -> openCreateModal());
        updateButton.addActionListener(e -> openUpdateModal());
        deleteButton.addActionListener(e -> deleteSelectedDepartment());
        refreshButton.addActionListener(e -> loadDepartments());

        // Actions des boutons de pagination
        prevButton.addActionListener(e -> {
            if (currentPage > 0) {
                currentPage--;
                loadDepartments();
            }
        });

        nextButton.addActionListener(e -> {
            currentPage++;
            loadDepartments();
        });
    }
    
    public void loadDepartments() {
        List<DepartmentResponse> departments = departementService.getDepartments(currentPage, pageSize, sortBy, direction);
        tableModel.setRowCount(0);
        for (DepartmentResponse dept : departments) {
            tableModel.addRow(new Object[]{dept.id(), dept.name()});
        }
        pageLabel.setText("Page: " + (currentPage + 1));
        prevButton.setEnabled(currentPage > 0);

        List<DepartmentResponse> nextDepartments = departementService.getDepartments(currentPage + 1, pageSize, sortBy,
                direction);
        nextButton.setEnabled(!nextDepartments.isEmpty());
    }

    private void openCreateModal() {
        JDialog createDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Créer Département", true);
        createDialog.setSize(300, 200);
        createDialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Nom : ");
        JTextField nameField = new JTextField(15);
        JButton saveButton = new JButton("Enregistrer");

        gbc.gridx = 0; gbc.gridy = 0;
        createDialog.add(nameLabel, gbc);
        gbc.gridx = 1;
        createDialog.add(nameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        createDialog.add(saveButton, gbc);

        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            if (!name.isEmpty()) {
                departementService.createDepartment(new DepartmentRequest(name));
                createDialog.dispose();
                loadDepartments();
            } else {
                JOptionPane.showMessageDialog(createDialog, "Le nom ne peut pas être vide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        createDialog.setLocationRelativeTo(this);
        createDialog.setVisible(true);
    }

    private void openUpdateModal() {
        int selectedRow = departmentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un département.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String id = (String) tableModel.getValueAt(selectedRow, 0);
        DepartmentResponse department = departementService.getDepartment(id);
        if (department == null) {
            JOptionPane.showMessageDialog(this, "Département introuvable.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog updateDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Mettre à Jour Département", true);
        updateDialog.setSize(300, 200);
        updateDialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Nom : ");
        JTextField nameField = new JTextField(15);
        nameField.setText(department.name());
        JButton saveButton = new JButton("Enregistrer");

        gbc.gridx = 0; gbc.gridy = 0;
        updateDialog.add(nameLabel, gbc);
        gbc.gridx = 1;
        updateDialog.add(nameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        updateDialog.add(saveButton, gbc);

        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            if (!name.isEmpty()) {
                departementService.updateDepartment(id, new DepartmentRequest(name));
                updateDialog.dispose();
                loadDepartments();
            } else {
                JOptionPane.showMessageDialog(updateDialog, "Le nom ne peut pas être vide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        updateDialog.setLocationRelativeTo(this);
        updateDialog.setVisible(true);
    }
    
    private void deleteSelectedDepartment() {
        int selectedRow = departmentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un département à supprimer.", "Avertissement",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String deptId = (String) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer ce département ?",
                "Confirmer Suppression", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            departementService.deleteDepartment(deptId);
            loadDepartments();
        }
    }

    private void logout(){
        authenticationService.logout();
        navigationHelper.navigateTo("login");
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
