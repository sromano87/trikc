package it.unibas.trikc.modelEntity.compositeClass;

import java.util.List;


import it.unibas.trikc.modelEntity.Package;
import it.unibas.trikc.modelEntity.method.IMethod;

public interface IClass {
	
	public void setFullName(String name);
	public void setPackage(Package myPackage);
	public void addMethod (IMethod method);
	public String getFullName (); 
	public List<IMethod> getMethods ();
	public IMethod getMethodAt (int index); 
	public Package getPackage();
	
}
