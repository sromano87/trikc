package it.unibas.trikc.coverage;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;


/**
 *	Customized URLClassLoader extending URLClassLoader.
 *	It is used to load all the SUT classes.
 * 
 * @author  TeamCoverage
 * @see java.net.URLClassLoader
 */

public class MemoryClassLoader extends URLClassLoader {

	public MemoryClassLoader(URL[] urls, ClassLoader parent) {
		super(urls, parent);
	}

	private final Map<String, byte[]> definitions = new HashMap<String, byte[]>();

	/**
	 * Adds a in-memory representation of a class.
	 * 
	 * @param name
	 *            name of the class
	 * @param bytes
	 *            class definition
	 */
	public void addDefinition(final String name, final byte[] bytes) {
		definitions.put(name, bytes);
	}

	@Override
	protected Class<?> loadClass(final String name, final boolean resolve)
			throws ClassNotFoundException {
		final byte[] bytes = definitions.get(name);
		if (bytes != null) {
			return defineClass(name, bytes, 0, bytes.length);
		}
		return super.loadClass(name, resolve);
	}

}

