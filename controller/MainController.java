package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.CompanyModel;
import models.Department;
import views.MainView;
import views.WarningPopup;

import javax.swing.*;

public class MainController {
    CompanyModel model;
    MainView view;

    public MainController(CompanyModel model, MainView view) {
        this.model = model;
        this.view = view;
    }

    // COMPANY SECTION UI ELEMENTS
    @FXML
    private Text companyNameTxt;
    @FXML
    private Text companyDescriptionTxt;
    @FXML
    private Text departmentCountTxt;
    @FXML
    private Text teamCountTxt;
    @FXML
    private Text employeeCountTxt;
    @FXML
    private Text companySalaryBudgetTxt;
    @FXML
    private Text companySalaryExpenseTxt;
    @FXML
    private TextField salaryBudgetField;
    @FXML
    private ChoiceBox<Department> departmentsChoiceBox;
    @FXML
    private Text selectedDepartmentName;
    @FXML
    private Text departmentDescriptionTxt;
    @FXML
    private Text selectedDepartmentBudget;
    @FXML
    private Text selectedDepartmentExpenses;
    @FXML
    private Text selectedDepartmentTeamCount;
    @FXML
    private Text selectedDepartmentEmployeeCount;

    @FXML
    private void deleteDepartment(ActionEvent event) {
        if (!(departmentsChoiceBox.getSelectionModel().getSelectedItem() == null)) {
            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(view.getStage());
            dialog.setTitle("Deletion Confirmation");
            dialog.setResizable(false);
            TextFlow confirmationText = new TextFlow();
            Text text1 = new Text("Are you sure you would like to delete the " );
            Text departmentName = new Text(departmentsChoiceBox.getSelectionModel().getSelectedItem().toString());
            Text text2 = new Text(" department? This is irreversible.");
            departmentName.setStyle("-fx-font-weight: bold");
            confirmationText.getChildren().add(text1);
            confirmationText.getChildren().add(departmentName);
            confirmationText.getChildren().add(text2);
            Button setDepartmentDescriptionBtn = new Button();
            setDepartmentDescriptionBtn.textProperty().set("Delete Department");
            setDepartmentDescriptionBtn.setOnAction(e -> {
                Department department = departmentsChoiceBox.getSelectionModel().getSelectedItem();
                departmentsChoiceBox.getItems().remove(department);
                this.model.removeDepartment(department);
                selectedDepartmentName.setText("Selected Department: ");
                departmentDescriptionTxt.setText("");
                selectedDepartmentTeamCount.setText("Number of Teams: ");
                selectedDepartmentEmployeeCount.setText("Number of Employees: ");
                selectedDepartmentBudget.setText("Department Salary Budget: ");
                selectedDepartmentExpenses.setText("Department Salary Expense: ");
                this.departmentCountTxt.setText("Number of Departments: " + this.model.getDepartments().size());
                departmentsChoiceBox.getSelectionModel().select(null);
                dialog.close();
            });
            HBox topHbox = new HBox(confirmationText);
            topHbox.setSpacing(10);
            HBox bottomHbox = new HBox(setDepartmentDescriptionBtn);
            bottomHbox.setSpacing(10);
            VBox dialogVbox = new VBox(topHbox,
                    confirmationText, bottomHbox);
            dialogVbox.setPadding(new Insets(20, 20, 20, 20));
            dialogVbox.setSpacing(10);
            Scene dialogScene = new Scene(dialogVbox);
            dialog.setScene(dialogScene);
            dialog.show();
        } else {
            WarningPopup.createWarningPopup("No Department Selected", "Select a department from the dropdown first to delete.", this.view.getStage());
        }
    }

    @FXML
    private void displaySelectedDepartment(ActionEvent event) {
        if (!(departmentsChoiceBox.getSelectionModel().getSelectedItem() == null)) {
            Department choice = departmentsChoiceBox.getSelectionModel().getSelectedItem();
            selectedDepartmentName.setText("Selected Department: " + choice.getName());
            departmentDescriptionTxt.setText(choice.getDescription());
            selectedDepartmentTeamCount.setText("Number of Teams: " + choice.getTeams().size());
            selectedDepartmentEmployeeCount.setText("Number of Employees: " + choice.getEmployees().size());
            selectedDepartmentBudget.setText("Department Salary Budget: " + choice.getBudget());
            selectedDepartmentExpenses.setText("Department Salary Expense: " + choice.getExpense());
        } else {
            WarningPopup.createWarningPopup("No Department Selected", "Select a department from the dropdown first to view.", this.view.getStage());
        }
    }

    @FXML
    private void editCompanyDetails(ActionEvent event) {
        int max_name_length = 16;
        int max_description_length = 270;
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(view.getStage());
        dialog.setTitle("Edit Company Details");
        dialog.setResizable(false);
        TextField companyNameField = new TextField();
        TextArea companyDescriptionField = new TextArea();
        Button setCompanyNameBtn = new Button();
        Button setCompanyDescriptionBtn = new Button();
        Text topNote = new Text(max_name_length + " characters remaining.");
        Text bottomNote = new Text(max_description_length + " characters remaining.");
        companyNameField.setPromptText("New company name");
        companyNameField.setOnKeyTyped(e -> {
            int length = companyNameField.getLength();
            topNote.setText(length > max_name_length ?
                    "Your input will be truncated" :
                    max_name_length - length + " characters remaining.");
        });
        companyDescriptionField.setPromptText("New company description");
        companyDescriptionField.setWrapText(true);
        companyDescriptionField.setOnKeyTyped(e -> {
            int length = companyDescriptionField.getLength();
            bottomNote.setText(length > max_description_length ?
                    "Your input will be truncated" :
                    max_description_length - length + " characters remaining.");
        });
        setCompanyNameBtn.textProperty().set("Set new name");
        setCompanyNameBtn.setOnAction(e -> {
            String name = companyNameField.getText().substring(0, Math.min(max_name_length, companyNameField.getLength()));
            model.setName(name);
            companyNameTxt.setText(name);
        });
        setCompanyDescriptionBtn.textProperty().set("Set new description");
        setCompanyDescriptionBtn.setOnAction(e -> {
            String description = companyDescriptionField.getText().substring(0, Math.min(max_description_length, companyDescriptionField.getLength()));
            model.setDescription(description);
            companyDescriptionTxt.setText(description);
        });
        HBox topHbox = new HBox(companyNameField, topNote, setCompanyNameBtn);
        topHbox.setSpacing(10);
        HBox bottomHbox = new HBox(setCompanyDescriptionBtn, bottomNote);
        bottomHbox.setSpacing(10);
        VBox dialogVbox = new VBox(topHbox,
                companyDescriptionField, bottomHbox);
        dialogVbox.setPadding(new Insets(20, 20, 20, 20));
        dialogVbox.setSpacing(10);
        Scene dialogScene = new Scene(dialogVbox);
        dialog.setScene(dialogScene);
        dialog.show();
    }
    @FXML
    private void createNewDepartment(ActionEvent event) {
        int max_name_length = 16;
        int max_description_length = 270;
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(view.getStage());
        dialog.setTitle("Create New Department");
        dialog.setResizable(false);
        TextField departmentNameField = new TextField();
        TextArea departmentDescriptionField = new TextArea();
        Button setDepartmentDescriptionBtn = new Button();
        Text topNote = new Text(max_name_length + " characters remaining.");
        Text bottomNote = new Text(max_description_length + " characters remaining.");
        departmentNameField.setPromptText("Set department name");
        departmentNameField.setOnKeyTyped(e -> {
            int length = departmentNameField.getLength();
            topNote.setText(length > max_name_length ?
                    "Your input will be truncated" :
                    max_name_length - length + " characters remaining.");
        });
        departmentDescriptionField.setPromptText("Department description");
        departmentDescriptionField.setWrapText(true);
        departmentDescriptionField.setOnKeyTyped(e -> {
            int length = departmentDescriptionField.getLength();
            bottomNote.setText(length > max_description_length ?
                    "Your input will be truncated" :
                    max_description_length - length + " characters remaining.");
        });
        setDepartmentDescriptionBtn.textProperty().set("Create department");
        setDepartmentDescriptionBtn.setOnAction(e -> {
            if (departmentNameField.getLength() == 0) {
                WarningPopup.createWarningPopup("No Name Provided", "Department name can not be empty!", dialog);
            } else {
                String name = departmentNameField.getText().substring(0, Math.min(max_name_length, departmentNameField.getLength()));
                String description = departmentDescriptionField.getText().substring(0, Math.min(max_description_length, departmentDescriptionField.getLength()));
                Department department = new Department(name, description);
                ObservableList<Department> departmentNames = departmentsChoiceBox.getItems();
                boolean containsSearchStr = departmentNames.stream().anyMatch(dep -> name.equalsIgnoreCase(dep.getName()));
                if (containsSearchStr) {
                    WarningPopup.createWarningPopup("Department Already Exists", "A department with this name already exists in this company!", dialog);
                } else {
                    this.model.addDepartment(department);
                    departmentNames.add(department);
                    this.departmentCountTxt.setText("Number of Departments: " + this.model.getDepartments().size());
                    dialog.close();
                }
            }
        });
        HBox topHbox = new HBox(departmentNameField, topNote);
        topHbox.setSpacing(10);
        HBox bottomHbox = new HBox(setDepartmentDescriptionBtn, bottomNote);
        bottomHbox.setSpacing(10);
        VBox dialogVbox = new VBox(topHbox,
                departmentDescriptionField, bottomHbox);
        dialogVbox.setPadding(new Insets(20, 20, 20, 20));
        dialogVbox.setSpacing(10);
        Scene dialogScene = new Scene(dialogVbox);
        dialog.setScene(dialogScene);
        dialog.show();
    }
    @FXML
    private void setCompanySalaryBudget(ActionEvent event) {

    }

}
