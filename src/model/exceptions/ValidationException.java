package model.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private Map<String, String> errors = new HashMap<>(); // criado map para guardar os erros de cada campo do formulário 1° String : nome do campo / 2° String : mensagem de erro
	
	public ValidationException(String msg) {
		super(msg);
	}

	public Map<String, String> getErrors(){
		return errors;
	}
	
	public void addError(String fieldName, String errorMessage) {
		errors.put(fieldName, errorMessage);
	}
	
	//Exceção personalizada carregando um coleção contendo todos os erros possiveis
}
