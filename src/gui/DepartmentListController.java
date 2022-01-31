package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
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
	public void onBtNewAction(ActionEvent event) { // referencia para o controle que recebeu o evento,condi��o para acessar o stage pelo currentStage
		Stage parentStage = Utils.currentStage(event);
		createDialogForm("/gui/DepartmentForm.fxml", parentStage);
	}

	public void setDepartmentService(DepartmentService service) {
		this.service = service;
		
		//criar metodo set para ter condi��o de injetar dependencia por outro lugar,
		//inverter o controle, disponibiliza alguma forma de  injetar dependencia, principio Solid, invers�o de controle
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	initializeNodes(); //cria��o do metodo auxilizar
		
	}


	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));// comando para iniciar apropriadamente o comportamento as colunas da tabela
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
			//padr�o do javafx para iniciar o comportamento das colunas
		
		Stage stage =(Stage) Main.getMainScene().getWindow(); // realizando um downcast do tipo Window(superclasse) para tipo Stage
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
			//macete para o tableView acompanhar a altura da janela
	}
	
	public void updateTableView() { // responsavel por  acessar o servi�o, carregar os departamentos e jogar os departamento no obsList
		if(service == null) {
			throw new IllegalStateException("Service was null");//inje��o de dependencia manual, criado exception para aletar o programador se caso ele esquecer
			
		}
		List<Department> list = service.findAll();
		obsList =  (ObservableListBase<Department>) FXCollections.observableArrayList(list);
		tableViewDepartment.setItems(obsList);
	}

	private void createDialogForm(String absoluteName ,Stage parentStage) {
		//fun��o para carregar a janela do formulario
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Department data");
			dialogStage.setScene(new Scene(pane)); // como � uma nova stage, tem que ter uma nova scene.
			dialogStage.setResizable(false); // comando se pode ou n�o ser redimensonada 
			dialogStage.initOwner(parentStage); // comando que solicita o Stage principal
			dialogStage.initModality(Modality.WINDOW_MODAL); //metodo que diz se vai ser modal ou outro comportamento, window_modal : tela travada enquanto n�o fechar, n�o poderar acessar a janela anterior
			dialogStage.showAndWait();
			
			//quando carregar uma janela de dialogo modal na frente da janela existente, tem que instanciar um novo stage ou seja um palco na frente de outro palco
			
		}catch(IOException e) {
			Alerts.showAlert("IO Exception", "Erro loading view", e.getMessage(), Alert.AlertType.ERROR);
		}
		
		//quando cria janela de dialogo, tem que informar para ela quem quw � o stage que criou a janela de dialogo
	}
}
