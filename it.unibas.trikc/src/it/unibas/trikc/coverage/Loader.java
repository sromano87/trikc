package it.unibas.trikc.coverage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibas.trikc.modelEntity.Package;
import it.unibas.trikc.modelEntity.Sut;
import it.unibas.trikc.modelEntity.TestSuite;
import it.unibas.trikc.modelEntity.compositeClass.CompositeClass;
import it.unibas.trikc.modelEntity.compositeClass.IClass;
import it.unibas.trikc.modelEntity.compositeClass.LeafNestedClass;


/**
 * Instance of the pattern singleton.
 * It is to explore the structure of the SUT
 * It is also responsible for initializing the {@link MemoryClassLoader}
 * 
 * @author TeamCoverage
 */
public class Loader {

	private String binPath;
	private String testSuiteName;
	private Map<String, Package> mapClassesPackages = new HashMap<String,Package>();
	
	private static Loader singleton = new Loader();

	private Loader() {};
	
	public static Loader getInstance() {
		return singleton;
	}
	
	protected String getBinPath() {
		return binPath;
	}
	public void setBinPath(String binPath) {
		this.binPath = binPath;
	}
	
	protected String getTestSuiteName() {
		return testSuiteName;
	}
	public void setTestSuiteName(String testSuiteName) {
		this.testSuiteName = testSuiteName;
	}
	
	public Map<String, Package> getMapClassesPackages() {
		return this.mapClassesPackages;
	}
	
	public Package findClassesPackageByKey(String key) {
		return mapClassesPackages.get(key);
	}
	
	/**
	 * Explores the structure of the SUT 
	 * to find packages and classes 
	 * 
	 * @param coverage:
	 *           object in which to put properties
	 *            
	 * @throws java.lang.Exception
	 */
	public void load(ICoverage coverage) throws Exception {
		findPackages(binPath);
		coverage.setClassesPackages(mapClassesPackages);
		MemoryClassLoader memoryClassLoader = initializeURLClassLoader();
		TestSuite testSuite = new TestSuite();
		testSuite.setFullName(testSuiteName);
		coverage.setTestSuite(testSuite);
		coverage.setBinPath(binPath);
		coverage.setMemoryClassLoader(memoryClassLoader);
	}
	
	public void findPackages(String directoryName) {
		Sut sut = new Sut();
		File directory = new File(directoryName);
	    File[] allFile = directory.listFiles();
	    for (File file : allFile) {
	    	if (file.isFile()) {
	    		String[] tmp = file.getAbsolutePath().split("/");
		    	if(tmp[tmp.length-1].contains("class")) {
		    		String fileTemp = createStringFileTemp(file);
		    		String clazzName = createClassName(fileTemp);
		    	    String packageName = createPackageName(clazzName, fileTemp);
		    	   
			    	if(!packageName.contains("test")) {
			    		Package pakage = findClassesPackageByKey(packageName);
			    		if(mapClassesPackages.isEmpty()) {
			    			sut.setFullName(packageName);
			    		}
			    		if(pakage == null) {
			    			pakage = createPackage(packageName);
			    			mapClassesPackages.put(packageName, pakage);
			    			addClassToPackage(pakage, clazzName);
			    			pakage.setMySut(sut);
			    			sut.addPackage(pakage);
				    	} else {
				    		addClassToPackage(pakage, clazzName);
				    	}
			    		
			    	} 
		    	}
	        }else if (file.isDirectory()) {
	            findPackages(file.getAbsolutePath());
	        }
	    }
	
	
	}
	
	public String createStringFileTemp(File file) {
		String fileTemp = file.getAbsolutePath().substring(binPath.length()+1,file.getAbsolutePath().length());
    	if(fileTemp.contains("/")){
	    	fileTemp = fileTemp.replace("/", ".");
    	}else{
	    	fileTemp = fileTemp.replace("\\", ".");
    	}
    	return fileTemp;
	}
	
	public String createClassName(String fileTemp) {
    	String[] classTemp = fileTemp.split("\\.");
    	String className =	classTemp[classTemp.length-2];
    	return className;
	}
	
	public String createPackageName(String clazzName, String fileTemp) {
    	String packageName = fileTemp.substring(0, fileTemp.length() - clazzName.length()-7);
    	return packageName;
	}
	
	public Package createPackage(String packageName) {
		Package pakage = new Package();
		pakage.setFullName(packageName);
		return pakage;
	}
	
	public IClass createClass() {
		CompositeClass clazz = new CompositeClass();
		return clazz;
	}
	
	public void addClassToPackage(Package pakage, String className) {
		if(className.contains("$")) {
			IClass clazz = new LeafNestedClass();
			clazz.setFullName(pakage.getFullName()+"." + className);
			clazz.setPackage(pakage);
			pakage.addClass(clazz);
		} else {
			IClass clazz = new CompositeClass();
			clazz.setFullName(pakage.getFullName()+"." + className);
			clazz.setPackage(pakage);
			pakage.addClass(clazz);
		}
		
	}

	public MemoryClassLoader initializeURLClassLoader() throws Exception {
		File file = new File(binPath);
		URL url = file.toURI().toURL();
		URL[] urls = {url};
		MemoryClassLoader memoryClassLoader = new MemoryClassLoader(urls, getClass().getClassLoader());
		return memoryClassLoader;
	}
	
	/**Don't use this method*/
	public void reset() {
		singleton = new Loader();
	}
}
