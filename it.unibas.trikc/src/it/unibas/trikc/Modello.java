package it.unibas.trikc;

import java.util.HashMap;
import java.util.Map;

public class Modello {
	private static Modello singleton = new Modello();
	private Map<String,Object> mappa = new HashMap<String,Object>();
	
	private Modello() {
		
	}
	
	public static Modello getInstance() {
		return singleton;	
	}
	
	public void putBean(String key, Object bean) {
		this.mappa.put(key, bean);
	}
	
	public Object getBean(String key) {
		return this.mappa.get(key);
	}
	
}
