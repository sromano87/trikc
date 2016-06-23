package it.unibas.trikc.modelEntity;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * @author riccardogiuzio
 *
 */
@Root
public class Sut {
	
	@Element
	private String name;
	
	private List<Package> packages = new ArrayList<>(); 
	
	public String getFullName () {
		return this.name; 
	}

	public List<Package> getPackages() {
		return packages;
	}
	
	public Package getPackageAt(int index) {
		return this.packages.get(index); 
	}

	public void setFullName(String name) {
		this.name = name;
	}

	public void setPackages(List<Package> packages) {
		this.packages = packages;
	}
	
	public void addPackage (Package element) {
		this.packages.add(element); 
	}
		
}
