package it.unibas.trikc.modelEntity.compositeClass;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import it.unibas.trikc.modelEntity.Package;
import it.unibas.trikc.modelEntity.method.IMethod;

@Root
public class AbstractClass implements IClass {

	@Element
	private String name;
	
	private List<IMethod> methods = new ArrayList<>(); 
	
	@Element
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

	public void setPackage(Package myPackage) {
		this.myPackage = myPackage;
	}
	
	public void addMethod (IMethod method) {
		this.methods.add(method); 
	}
	
}
