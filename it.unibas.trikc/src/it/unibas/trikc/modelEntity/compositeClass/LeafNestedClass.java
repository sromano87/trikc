package it.unibas.trikc.modelEntity.compositeClass;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Class LeafNestedClass
 *
 * @author TeamDataLayer
 */
 
@Root
public class LeafNestedClass extends AbstractClass {
	
	@Element
	private IClass parent; 
	
	/**
	 * This method return a class attribute of type {@link IClass}
	 * @return parent
	 */
	public IClass getParent() {
		return parent; 
	}

	/**
	 * This method set a class attribute of type {@link IClass}
	 * @param parent
	 */
	public void setParent(IClass parent) {
		this.parent = parent;
	}
	
}
