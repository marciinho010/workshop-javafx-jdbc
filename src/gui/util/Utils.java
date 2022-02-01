package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {
	
	public static Stage currentStage(ActionEvent event) {
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}
	
	public static Integer tryParsetoInt(String str) { //para ajudar a converter o valor da caixa para inteiro
		try {
			return Integer.parseInt(str);
		}catch(NumberFormatException e) { //caso estoure uma exceção de numero invalido, irá retornar null
			return null;
		}
	}
}
