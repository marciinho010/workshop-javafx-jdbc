package gui;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Seller;
import model.services.SellerService;

public class SellerListController implements Initializable, DataChangeListener {

	private SellerService service;

	@FXML
	private TableView<Seller> tableViewSeller;

	@FXML
	private TableColumn<Seller, Integer> tableColumnId;

	@FXML
	private TableColumn<Seller, String> tableColumnName;

	@FXML
	private TableColumn<Seller, Seller> tableColumnEDIT;

	@FXML
	private TableColumn<Seller, Seller> tableColumnREMOVE;

	@FXML
	private Button btnew;

	private ObservableListBase<Seller> obsList; // carregar os departamentos dentro desta lista

	@FXML
	public void onBtNewAction(ActionEvent event) { // referencia para o controle que recebeu o evento,condição para
													// acessar o stage pelo currentStage
		Stage parentStage = Utils.currentStage(event);
		Seller obj = new Seller();
		createDialogForm(obj, "/gui/SellerForm.fxml", parentStage);
	}

	public void setSellerService(SellerService service) {
		this.service = service;

		// criar metodo set para ter condição de injetar dependencia por outro lugar,
		// inverter o controle, disponibiliza alguma forma de injetar dependencia,
		// principio Solid, inversão de controle
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes(); // criação do metodo auxilizar

	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));// comando para iniciar apropriadamente o
																			// comportamento as colunas da tabela
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		// padrão do javafx para iniciar o comportamento das colunas

		Stage stage = (Stage) Main.getMainScene().getWindow(); // realizando um downcast do tipo Window(superclasse)
																// para tipo Stage
		tableViewSeller.prefHeightProperty().bind(stage.heightProperty());
		// macete para o tableView acompanhar a altura da janela
	}

	public void updateTableView() { // responsavel por acessar o serviço, carregar os departamentos e jogar os
									// departamento no obsList
		if (service == null) {
			throw new IllegalStateException("Service was null");// injeção de dependencia manual, criado exception para
																// aletar o programador se caso ele esquecer

		}
		List<Seller> list = service.findAll();
		obsList = (ObservableListBase<Seller>) FXCollections.observableArrayList(list);
		tableViewSeller.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}

	private void createDialogForm(Seller obj, String absoluteName, Stage parentStage) {
		// função para carregar a janela do formulario

		/*try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			SellerFormController controller = loader.getController(); // criando referencia para o controlador
			controller.setSeller(obj); // injetar no controlador o departamento
			controller.setSellerService(new SellerService()); // injetando o serviço
			controller.subcribeDataChangeListener(this);
			controller.updateFormData(); // carregar os dados do obj no formulario

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Seller data");
			dialogStage.setScene(new Scene(pane)); // como é uma nova stage, tem que ter uma nova scene.
			dialogStage.setResizable(false); // comando se pode ou não ser redimensonada
			dialogStage.initOwner(parentStage); // comando que solicita o Stage principal
			dialogStage.initModality(Modality.WINDOW_MODAL); // metodo que diz se vai ser modal ou outro comportamento,
																// window_modal : tela travada enquanto não fechar, não
																// poderar acessar a janela anterior
			dialogStage.showAndWait();

			// quando carregar uma janela de dialogo modal na frente da janela existente,
			// tem que instanciar um novo stage ou seja um palco na frente de outro palco

		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Erro loading view", e.getMessage(), Alert.AlertType.ERROR);
		}*/

		// quando cria janela de dialogo, tem que informar para ela quem quw é o stage
		// que criou a janela de dialogo
	}

	@Override
	public void onDataChanged() {
		updateTableView();

	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Seller obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/SellerForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Seller obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	private void  removeEntity(Seller obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");
		
		if(result.get() == ButtonType.OK) {
			if(service == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				service.remove(obj);
				updateTableView();
			}catch(DbIntegrityException e) {
				Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}

}
