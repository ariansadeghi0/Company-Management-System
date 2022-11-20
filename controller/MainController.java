package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
                String name = departmentNameField.getText().substring(0, Math.min(max_description_length, departmentNameField.getLength()));
                String description = departmentDescriptionField.getText().substring(0, Math.min(max_description_length, departmentDescriptionField.getLength()));
//            model.setDescription(description);
//            companyDescriptionTxt.setText(description);
                Department department = new Department(name, description);
                this.model.addDepartment(department);
                System.out.println(this.model.getDepartments());
                dialog.close();
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
