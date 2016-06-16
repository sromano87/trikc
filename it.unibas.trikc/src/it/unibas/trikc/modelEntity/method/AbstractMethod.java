package it.unibas.trikc.modelEntity.method;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class AbstractMethod {
	
	@Element
	private String name; 
		
	public String getFullName() {
		return name;
	}
	
	public void setFullName(String name) {
		this.name = name;
	}
	
}
