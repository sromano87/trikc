package it.unibas.trikc.modelEntity.compositeClass;

import java.util.ArrayList;
import java.util.List;

public class CompositeClass extends AbstractClass {
	
	private List<IClass> classes = new ArrayList<>(); 
	
	public List<IClass> getChildren () {
		return classes; 
	}
	
	public IClass getChildAt (int index) {
		return this.classes.get(index); 
	}

	public void setClasses(List<IClass> classes) {
		this.classes = classes;
	}
	
	public void addClass (IClass element) {
		this.classes.add(element);
	}
	
}
