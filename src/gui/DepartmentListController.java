package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable {
	
	private DepartmentService service;
	
	@FXML
	private TableView<Department> tableViewDepartment;
	
	@FXML
	private TableColumn<Department, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Department, String> tableColumnName;
	
	@FXML
	private Button btnew;
	
	private ObservableListBase<Department> obsList; // carregar  os departamentos dentro desta lista
	
	
	@FXML
	public void onBtNewAction() {
		
	}

	public void setDepartmentService(DepartmentService service) {
		this.service = service;
		
		//criar metodo set para ter condição de injetar dependencia por outro lugar,
		//inverter o controle, disponibiliza alguma forma de  injetar dependencia, principio Solid, inversão de controle
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
	
	public void updateTableView() { // responsavel por  acessar o serviço, carregar os departamentos e jogar os departamento no obsList
		if(service == null) {
			throw new IllegalStateException("Service was null");//injeção de dependencia manual, criado exception para aletar o programador se caso ele esquecer
			
		}
		List<Department> list = service.findAll();
		obsList =  (ObservableListBase<Department>) FXCollections.observableArrayList(list);
		tableViewDepartment.setItems(obsList);
		
		
		
		
		
	}

}
