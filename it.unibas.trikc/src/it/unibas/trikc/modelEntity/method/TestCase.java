package it.unibas.trikc.modelEntity.method;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import it.unibas.trikc.modelEntity.Line;

@Root
public class TestCase extends AbstractMethod {
	
	@ElementList
	private List<Line> coveredLines = new ArrayList<>();

	public List<Line> getCoveredLines() {
		return coveredLines;
	}
	
	public Line getCoveredLineAt (int index) {
		return coveredLines.get(index); 
	}

	public void setCoveredLines(List<Line> coveredLines) {
		this.coveredLines = coveredLines;
	}
	
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
