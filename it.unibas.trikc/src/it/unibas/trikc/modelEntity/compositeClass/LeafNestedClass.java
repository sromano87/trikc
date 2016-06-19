package it.unibas.trikc.modelEntity.compositeClass;

public class LeafNestedClass extends AbstractClass {
	
	private IClass parent; 
	
	public IClass getParent() {
		return parent; 
	}

	public void setParent(IClass parent) {
		this.parent = parent;
	}
	
}
