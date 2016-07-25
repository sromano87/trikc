package it.unibas.trikc.modelEntity.compositeClass;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * Class CompositeClass
 *
 * @author TeamDataLayer
 */

@Root
public class CompositeClass extends AbstractClass {
	
	@ElementList
	private List<IClass> classes = new ArrayList<>(); 
	
	/**
	 * This method return a List of instance of type {@link IClass}
	 * @return classes
	 */
	public List<IClass> getChildren () {
		return classes; 
	}
	
	/**
	 * This method return a instance of {@link IClass} at position index
	 * @param index
	 * @return IClass
	 */
	public IClass getChildAt (int index) {
		return this.classes.get(index); 
	}

	/**
	 * This method set a List classes
	 * @param classes
	 */
	public void setClasses(List<IClass> classes) {
		this.classes = classes;
	}
	
	/**
	 * This method add a element of type {@link IClass}
	 * @param element
	 */
	public void addClass (IClass element) {
		this.classes.add(element);
	}
	
}
