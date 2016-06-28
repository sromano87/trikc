package it.unibas.trikc.modelEntity;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import it.unibas.trikc.modelEntity.compositeClass.IClass;

@Root
public class Package {
	
	@Element
	private String name;
	
	@Element
	private Sut mySut; 
	
	private List<IClass> classes = new ArrayList<>();
	
	public String getFullName() {
		return name;
	}
	
	public List<IClass> getClasses() {
		return classes;
	}
	
	public IClass getClassAt(int index) {
		return this.classes.get(index); 
	}

	public void setFullName(String name) {
		this.name = name;
	}

	public void setClasses(List<IClass> classes) {
		this.classes = classes;
	}
	
	public void addClass (IClass element) {
		this.classes.add(element); 
	}

	public Sut getMySut() {
		return mySut;
	}

	public void setMySut(Sut mySut) {
		this.mySut = mySut;
	}
	
	
	
}
