package it.unibas.trikc.modelEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author riccardogiuzio
 *
 */
public class Sut {
	
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
