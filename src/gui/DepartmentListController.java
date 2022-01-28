package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;

public class DepartmentListController implements Initializable {
	
	@FXML
	private TableView<Department> tableViewDepartment;
	
	@FXML
	private TableColumn<Department, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Department, String> tableColumnName;
	
	@FXML
	private Button btnew;
	
	@FXML
	public void onBtNewAction() {
		
	}


	@Override
	public void initialize(URL url, ResourceBundle rb) {
	initializeNodes(); //criação do metodo auxilizar
		
	}


	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));// comando para iniciar apropriadamente o comportamento as colunas da tabela
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
			//padrão do javafx para iniciar o comportamento das colunas
		
		Stage stage =(Stage) Main.getMainScene().getWindow(); // realizando um downcast do tipo Window(superclasse) para tipo Stage
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
			//macete para o tableView acompanhar a altura da janela
	}

}
