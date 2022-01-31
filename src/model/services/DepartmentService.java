package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentService {
	
	private DepartmentDao dao = DaoFactory.createDepartmentDao(); //criando  dependencia usando padr�o factory

	public List<Department> findAll(){
		return dao.findAll();
	}
}
