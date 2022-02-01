package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;


public class DepartmentFormController implements Initializable {
	
	private Department entity;
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private Label labelErroName;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	public void setDepartment(Department entity) {
		this.entity = entity;
	}
	
	@FXML
	public void onBtSaveAction() {
		
	}
	
	@FXML
	public void onBtCancelAction() {
		
	}
	

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		
	}

	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId); // metodo para aceitar apenas numeros inteiros
		Constraints.setTextFieldMaxLength(txtName, 30); //metodo para colocar limite de caractere
		
	}
	
	public void updateFormData() {  //responsavel por pegar os dados do entity e popular as caixas do texto do formulário
		if(entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
		
		
	}
	
}
