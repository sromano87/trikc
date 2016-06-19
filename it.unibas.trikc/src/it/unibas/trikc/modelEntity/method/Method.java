package it.unibas.trikc.modelEntity.method;

import java.util.ArrayList;
import java.util.List;

import it.unibas.trikc.modelEntity.Line;
import it.unibas.trikc.modelEntity.compositeClass.IClass;

public class Method extends AbstractMethod {
	
	private List<Line> lines = new ArrayList<>(); 
	private IClass myClass;
	private String descriptor;
	
	public List<Line> getLines() {
		return lines;
	}
	
	public Line getLineAt(int index) {
		return lines.get(index); 
	}
	
	public IClass getMyClass() {
		return myClass;
	}

	public void setLines(List<Line> lines) {
		this.lines = lines;
	}

	public void setMyClass(IClass myClass) {
		this.myClass = myClass;
	} 
	
	public void addLine (Line line) {
		this.lines.add(line); 
	}
	
	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	} 
	
	public String getDescriptor() {
		return descriptor;
	}
	
}
