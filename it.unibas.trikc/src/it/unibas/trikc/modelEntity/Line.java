package it.unibas.trikc.modelEntity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import it.unibas.trikc.modelEntity.method.MyMethod;

/**
 * Class Line
 *
 * @author TeamDataLayer
 */

@Root
public class Line {
	
	@Element
	private int number;
	
	@Element
	private String id;
	
	@Element
	private MyMethod myMethod; 
	
	/**
	  * This is no-arg constructor
	  */
	public Line () {
	}
	
	/**
	 * This is constructor with arg
	 * @param number
	 * @param id
	 */
	public Line (int number, String id) {
		this.number = number; 
		this.id = id; 
	}
	
	/**
	 * This method return a class attribute
	 * @return number
	 */
	public int getNumber() {
		return number;
	}
	
	/**
	 * This method return a class attribute
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * This method set a class attribute
	 * @param number
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * This method set a class attribute
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * This method return a class attribute of type {@link MyMethod}
	 * @return myMethod
	 */
	public MyMethod getMyMethod() {
		return myMethod;
	}

	/**
	  * This method set a class attribute of type {@link MyMethod}
	  * @param myMethod
	  */
	public void setMyMethod(MyMethod myMethod) {
		this.myMethod = myMethod;
	} 
	
}
