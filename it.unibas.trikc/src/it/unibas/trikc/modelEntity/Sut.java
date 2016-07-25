package it.unibas.trikc.modelEntity;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Class Sut
 *
 * @author TeamDataLayer
 */
@Root
public class Sut {
	
	@Element
	private String name;
	
	private List<Package> packages = new ArrayList<>(); 
	
	/**
	 * This method return a full name descriptor of Class
	 * @return name; 
	 */
	public String getFullName () {
		return this.name; 
	}

	/**
	 * This method return a List of instance of type {@link Package}
	 * @return packages
	 */
	public List<Package> getPackages() {
		return packages;
	}
	
	/**
	 * This method return a instance of {@link Package} at position index
	 * @param index
	 * @return Package
	 */
	public Package getPackageAt(int index) {
		return this.packages.get(index); 
	}

	/**
	  * This method set a class attribute
	  * @param name
	  */
	public void setFullName(String name) {
		this.name = name;
	}

	/**
	 * This method set a List Package
	 * @param packages
	 */
	public void setPackages(List<Package> packages) {
		this.packages = packages;
	}
	
	/**
	 * This method add a element of type {@link Package}
	 * @param element
	 */
	public void addPackage (Package element) {
		this.packages.add(element); 
	}
		
}
