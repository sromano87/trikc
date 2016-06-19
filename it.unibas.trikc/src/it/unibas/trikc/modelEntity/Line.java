package it.unibas.trikc.modelEntity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Line {
	
	@Element
	private int number;
	
	@Element
	private String id;
	
	public Line () {
	}
	
	public Line (int number, String id) {
		this.number = number; 
		this.id = id; 
	}
	
	public int getNumber() {
		return number;
	}
	
	public String getId() {
		return id;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setId(String id) {
		this.id = id;
	} 
	
}
