package it.unibas.trikc.modelEntity;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import it.unibas.trikc.modelEntity.method.TestCase;

/**
 * Class DissimilarityMatrix
 *
 * @author TeamDataLayer
 */

@Root
public class DissimilarityMatrix {
	
	@ElementList
	private List<TestCase> headers = new ArrayList<>();
	
	@ElementArray
	private double[][] matrix;
	
	@Element
	private int size;
	
	/**
	 * Do not use this constructor, it is required to libraries. 
	 * Use a costructor with argument, DissimilarityMatrix(int size) for create a new instance of DissimilarityMatrix.   
	 * */
	public DissimilarityMatrix() {}
	
	/**
	 * This is constructor with arg
	 * @param size
	 */
	public DissimilarityMatrix(int size) {
		matrix = new double[size][size];
		this.size = size;
	}
	
	/**
	 * This method return a value of a matrix 
	 * @param i
	 * @param j
	 * @return matrix
	 */
	public Double getValueAt (int i, int j) {
		return matrix[i][j]; 
	}
	
	/**
	 * This method set a value of a matrix 
	 * @param i
	 * @param j
	 * @param value
	 */
	public void setValueAt (int i, int j, double value) {
		matrix[i][j] = value; 
	}
	
	/**
	 * This method return a matrix
	 * @return matrix 
	 */
	public double[][] getMatrix() {
		return matrix;
	}
	
	/**
	 * This method set the matrix
	 * @param matrix
	 */
	public void setMatrix(double[][] matrix) {
		this.matrix = matrix;
	}
	
	/**
	 * This method return a class attribute
	 * @return size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * This method set a class attribute headers with a List of TestCase
	 * @param headers
	 */
	public void setHeaders(List<TestCase> headers) {
		this.headers = headers;
	}

	/**
	 * This method set a size of class attribute
	 * @param size
	 */
	public void setSize(int size) {
		this.size = size;
	}
	
	/**
	 * This method add a header
	 * @param testCase
	 */
	public void addHeader (TestCase testCase) {
		this.headers.add(testCase); 
	}

	/**
	 * This method return the headers of the DissimilarityMatrix
	 * @return List<TestCase>
	 */
	public List<TestCase> getHeaders() {
		return headers;
	}
	
	/**
	 * This method return a header TestCase with name
	 * @param name
	 * @return TestCase
	 */
	public TestCase getHeaderByName(String name){
		for(TestCase testCase: this.getHeaders()){
			if(testCase.getFullName().equals(name)){
				return testCase;
			}
		}
		return null;
	}
	
}
