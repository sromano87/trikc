package it.unibas.trikc.modelEntity.compositeClass;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import it.unibas.trikc.modelEntity.Package;
import it.unibas.trikc.modelEntity.method.IMethod;

/**
 * Abstract class AbstractClass
 *
 * @author TeamDataLayer
 */

@Root
public class AbstractClass implements IClass {

	@Element
	private String name;
	
	private List<IMethod> methods = new ArrayList<>(); 
	
	@Element
	private Package myPackage;
	
	/**
	 * This method return a full name descriptor of class
	 * @return name; 
	 */
	public String getFullName () {
		return name; 
	}
	
	/**
	 * This method return a List of methods of {@link IMethod}
	 * @return methods 
	 */	
	public List<IMethod> getMethods () {
		return methods; 
	}

	/**
	 * This method return a instance of {@link IMethod} at position index 
	 * @param index
	 * @return IMethod 
	 */
	public IMethod getMethodAt (int index) {
		return methods.get(index); 
	}
	
	/**
	  * This method return a class attribute of type {@link Package}
	  * @return myPackage
	  */	
	public Package getPackage() {
		return myPackage; 
	}

	/**
	  * This method set a class attribute
	  * @param name
	  */
	public void setFullName(String name) {
		this.name = name;
	}

	/**
	 * This method set all methods 
	 * @param methods
	 */	
	public void setMethods(List<IMethod> methods) {
		this.methods = methods;
	}
	
	/**
	 * This method set a package 
	 * @param myPackage
	 */
	public void setPackage(Package myPackage) {
		this.myPackage = myPackage;
	}

	/**
	 * This method add a method 
	 * @param method
	 */	
	public void addMethod (IMethod method) {
		this.methods.add(method); 
	}
	
}
