package it.unibas.trikc.modelEntity.method;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import it.unibas.trikc.modelEntity.Line;

/**
 * Class TestCase
 *
 * @author TeamDataLayer
 */

@Root
public class TestCase extends AbstractMethod {
	
	@ElementList
	private List<Line> coveredLines = new ArrayList<>();

	/**
	 * This method return a List of coveredLines of {@link Line}
	 * @return coveredLines
	 */
	public List<Line> getCoveredLines() {
		return coveredLines;
	}

		/**
	 * This method return a instance of {@link Line} at position index 
	 * @param index
	 * @return Line
	 */
	public Line getCoveredLineAt (int index) {
		return coveredLines.get(index); 
	}

	/**
	 * This method set a List coveredLines
	 * @param coveredLines
	 */
	public void setCoveredLines(List<Line> coveredLines) {
		this.coveredLines = coveredLines;
	}
	
	/**
	 * This method add a line
	 * @param line
	 */
	public void addCoveredLine (Line line) {
		this.coveredLines.add(line); 
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(super.getFullName() + " - " + this.coveredLines.size());
		stringBuilder.append("\n");
		return stringBuilder.toString();
	}
}
