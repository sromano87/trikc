package it.unibas.trikc.modelEntity.compositeClass;

import java.util.ArrayList;
import java.util.List;

import it.unibas.trikc.modelEntity.method.IMethod;


public class AbstractClass {

	private String name; 
	private List<IMethod> methods = new ArrayList<>(); 
	private Package myPackage;
	
	public String getFullName () {
		return name; 
	}
	
	public List<IMethod> getMethods () {
		return methods; 
	}
	
	public IMethod getMethodAt (int index) {
		return methods.get(index); 
	}
	
	public Package getPackage() {
		return myPackage; 
	}

	public void setFullName(String name) {
		this.name = name;
	}

	public void setMethods(List<IMethod> methods) {
		this.methods = methods;
	}

	public void setMyPackage(Package myPackage) {
		this.myPackage = myPackage;
	}
	
	public void addMethod (IMethod method) {
		this.methods.add(method); 
	}
	
}
