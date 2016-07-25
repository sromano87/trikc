package it.unibas.trikc.modelEntity.method;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Class AbstractClass
 *
 * @author TeamDataLayer
 */

@Root
public class AbstractMethod {
	
	@Element
	private String name; 
	
	/**
	 * This method return a full name descriptor of Class
	 * @return name; 
	 */	
	public String getFullName() {
		return name;
	}
	
	/**
	  * This method set a class attribute
	  * @param name
	  */
	public void setFullName(String name) {
		this.name = name;
	}
	
}
