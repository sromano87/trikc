package it.unibas.trikc.modelEntity.compositeClass;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class CompositeClass extends AbstractClass {
	
	@ElementList
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
