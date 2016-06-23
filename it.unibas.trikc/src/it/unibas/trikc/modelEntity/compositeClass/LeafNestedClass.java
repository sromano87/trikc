package it.unibas.trikc.modelEntity.compositeClass;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class LeafNestedClass extends AbstractClass {
	
	@Element
	private IClass parent; 
	
	public IClass getParent() {
		return parent; 
	}

	public void setParent(IClass parent) {
		this.parent = parent;
	}
	
}
