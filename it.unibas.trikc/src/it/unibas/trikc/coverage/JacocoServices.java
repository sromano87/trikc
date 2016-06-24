package it.unibas.trikc.coverage;


import java.io.InputStream;

import org.jacoco.core.analysis.Analyzer;
import org.jacoco.core.analysis.CoverageBuilder;
import org.jacoco.core.data.ExecutionDataStore;
import org.jacoco.core.data.SessionInfoStore;
import org.jacoco.core.instr.Instrumenter;
import org.jacoco.core.runtime.IRuntime;
import org.jacoco.core.runtime.LoggerRuntime;
import org.jacoco.core.runtime.RuntimeData;



/**
 * Class of service used as a bridge to the library JaCoCo
 * 
 * 
 * @author TeamCoverage
 * 
 * @see it.unibas.trikc.coverage.Coverage
 * @see org.jacoco.core.analysis.Analyzer
 * @see	org.jacoco.core.analysis.CoverageBuilder
 * @see org.jacoco.core.data.ExecutionDataStore
 * @see org.jacoco.core.data.SessionInfoStore
 * @see org.jacoco.core.instr.Instrumenter
 * @see org.jacoco.core.runtime.IRuntime
 * @see org.jacoco.core.runtime.LoggerRuntime
 * @see org.jacoco.core.runtime.RuntimeData
 */
public class JacocoServices {

	private RuntimeData runtimeData = new RuntimeData();
	private SessionInfoStore sessionInfoStore = new SessionInfoStore();
	private ExecutionDataStore executionDataStore = new ExecutionDataStore();
	private IRuntime runtime = new LoggerRuntime();
	private MemoryClassLoader memoryClassLoader;
	
	/**
	 * Return the test class that has been instrumented by JaCoCo
	 * This method also instruments the source class and adds both to the {@link MemoryClassLoader}
	 * 
	 * 
	 * @param className:
	 *            name of the source class to instrument
	 * @param testClassName:
	 *            name of the test class to instrument
	 * 
	 * @see it.unibas.trikc.coverage.MemoryClassLoader
	 */
	public Class<?> createInstrument(String className, String testClassName) throws Exception {
		runtime = new LoggerRuntime();
		
		this.memoryClassLoader = Loader.getInstance().initializeURLClassLoader();
		
		final Instrumenter instr = new Instrumenter(runtime);
				
		final byte[] instrumented = instr.instrument(getClassAsStream(className), className);
		final byte[] instrumentedTest = instr.instrument(getClassAsStream(testClassName), testClassName);
		this.runtimeData = new RuntimeData();
		this.runtime.startup(runtimeData);
		
		memoryClassLoader.addDefinition(className, instrumented);
		memoryClassLoader.addDefinition(testClassName, instrumentedTest);
		Class<?> testClassToCoverage = memoryClassLoader.loadClass(testClassName);
	
		this.inizializeExecutionDataStore();
		this.inizializeSessionInfoStore();
		
		return testClassToCoverage;
	}

	public void runtimeStartup() throws Exception {
		this.runtimeData = new RuntimeData();
		this.runtime.startup(runtimeData);
	}
	
	public void runtimeShutdown() {
		runtime.shutdown();
	}
	
	public void inizializeSessionInfoStore() {
		this.sessionInfoStore = new SessionInfoStore();
	}
	
	public void inizializeExecutionDataStore() {
		this.executionDataStore = new ExecutionDataStore();
	}

	/**
	 * Return the result of the coverage 
	 * The coverage is calculated by the {@link Analyzer}
	 * The result of the coverage is saved in a {@link CoverageBuilder}
	 * 
	 * 
	 * @return coverageBuilder:
	 * 			
	 * @param className:
	 *            name of the source class to analyze
	 */
	public CoverageBuilder collectAnalysis(String className) throws Exception {
		this.runtimeData.collect(this.executionDataStore, this.sessionInfoStore, false);
		CoverageBuilder coverageBuilder = new CoverageBuilder();
		Analyzer analyzer = new Analyzer(this.executionDataStore, coverageBuilder);
		analyzer.analyzeClass(getClassAsStream(className), className);
		return coverageBuilder;
		
	}
	
	private InputStream getClassAsStream(final String className) throws Exception {
		final String resourceName = className.replace('.', '/') + ".class";
		InputStream i = memoryClassLoader.getResourceAsStream(resourceName);
		return i;
	}

	public MemoryClassLoader getMemoryClassLoader() {
		return memoryClassLoader;
	}

	public void setMemoryClassLoader(MemoryClassLoader memoryClassLoader) {
		this.memoryClassLoader = memoryClassLoader;
	}

}
