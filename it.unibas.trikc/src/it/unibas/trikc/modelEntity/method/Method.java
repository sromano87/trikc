package it.unibas.trikc.modelEntity.method;

import java.util.ArrayList;
import java.util.List;

import it.unibas.trikc.modelEntity.Line;
import it.unibas.trikc.modelEntity.compositeClass.IClass;

/**
 * Class Method
 *
 * @author TeamDataLayer
 */

public class Method extends AbstractMethod {
	
	private List<Line> lines = new ArrayList<>(); 
	private IClass myClass;
	private String descriptor;
	
	/**
	 * This method return a List of lines of {@link Line}
	 * @return lines
	 */
	public List<Line> getLines() {
		return lines;
	}
	
	/**
	 * This method return a instance of {@link Line} at position index 
	 * @param index
	 * @return Line
	 */
	public Line getLineAt(int index) {
		return lines.get(index); 
	}
	
	/**
	 * This method return a class attribute of type {@link IClass}
	 * @return myClass
	 */
	public IClass getMyClass() {
		return myClass;
	}

	/**
	 * This method set a class attribute
	 * @param lines
	 */
	public void setLines(List<Line> lines) {
		this.lines = lines;
	}

	/**
	 * This method set a class attribute
	 * @param myClass
	 */
	public void setMyClass(IClass myClass) {
		this.myClass = myClass;
	} 
	
	/**
	 * This method set a class attribute
	 * @param myClass
	 */
	public void addLine (Line line) {
		this.lines.add(line); 
	}
	
	/**
	 * This method set a class attribute
	 * @param descriptor
	 */
	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	} 
	
	/**
	  * This method return a class attribute
	  * @return descriptor
	  */
	public String getDescriptor() {
		return descriptor;
	}
	
}
