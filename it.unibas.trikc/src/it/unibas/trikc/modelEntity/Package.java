package it.unibas.trikc.modelEntity;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import it.unibas.trikc.modelEntity.compositeClass.IClass;

/**
 * Class Package
 *
 * @author TeamDataLayer
 */

@Root
public class Package {
	
	@Element
	private String name;
	
	@Element
	private Sut mySut; 
	
	private List<IClass> classes = new ArrayList<>();
	
	/**
	 * This method return a full name descriptor of Class
	 * @return name; 
	 */
	public String getFullName() {
		return name;
	}
	
	/**
	 * This method return a List of instance of type {@link IClass}
	 * @return classes
	 */
	public List<IClass> getClasses() {
		return classes;
	}
	
	/**
	 * This method return a instance of {@link IClass} at position index
	 * @param index
	 * @return IClass
	 */
	public IClass getClassAt(int index) {
		return this.classes.get(index); 
	}

	/**
	  * This method set a class attribute
	  * @param name
	  */
	public void setFullName(String name) {
		this.name = name;
	}

	/**
	 * This method set a List classes
	 * @param classes
	 */
	public void setClasses(List<IClass> classes) {
		this.classes = classes;
	}
	
	/**
	 * This method add a element of type {@link IClass}
	 * @param element
	 */
	public void addClass (IClass element) {
		this.classes.add(element); 
	}

	/**
	 * This method return a class attribute of type {@link Sut}
	 * @return mySut
	 */
	public Sut getMySut() {
		return mySut;
	}

	/**
	 * This method set a class attribute of type {@link Sut}
	 * @param mySut
	 */
	public void setMySut(Sut mySut) {
		this.mySut = mySut;
	}
	
	
	
}
