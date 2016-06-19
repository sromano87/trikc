package it.unibas.trikc.modelEntity;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import it.unibas.trikc.modelEntity.method.TestCase;

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
	
	public DissimilarityMatrix(int size) {
		matrix = new double[size][size];
	}
	
	public Double getValueAt (int i, int j) {
		return matrix[i][j]; 
	}
	
	public void setValueAt (int i, int j, double value) {
		matrix[i][j] = value; 
	}
	
	public double[][] getMatrix() {
		return matrix;
	}
	
	public void setMatrix(double[][] matrix) {
		this.matrix = matrix;
	}
	
	public int getSize() {
		return size;
	}

	public void setHeaders(List<TestCase> headers) {
		this.headers = headers;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public void addHeader (TestCase testCase) {
		this.headers.add(testCase); 
	}
	
}
