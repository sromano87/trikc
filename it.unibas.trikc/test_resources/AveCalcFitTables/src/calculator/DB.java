package calculator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * DB class.
 * This class is a container of all the informazion of the file db.xml (the database).
 * db.xml contains all the information concerning exams. 
 * @author andima
 * 
 * */
public class DB implements Constants {
	
	private File file;
	private Collection examsList;
	private double average;
	private int cfu;
	private double degree;
	
	
	public DB() {
		this("db");
	}
	
	public DB(String dbName){
		file = new File(dbName + ".xml");
		examsList = new ArrayList();
		cfu = 0;
		average = 0.0;
		degree = 0.0;
	}
	
	public void load(){
		DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
		DocumentBuilder build = null;
		try {
			build = fac.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return;
		}
		Document doc = null;
		try {
			doc = build.parse(file);
		} catch (SAXException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		Element elem = doc.getDocumentElement();
		
		NodeList list = elem.getElementsByTagName("exams");
		if (list == null) {
			JOptionPane.showMessageDialog(null, "File format not valid",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		elem = (Element) list.item(0);
		list = elem.getElementsByTagName("exam");
		examsList = new ArrayList();
		Exam e;
		for (int i = 0; i < list.getLength(); i++) {
			elem = (Element) list.item(i);
			e = new Exam();
			e.setName(elem.getAttribute("name"));
			e.setCfu(Integer.parseInt(elem.getAttribute("cfu")));
			e.setMaked(Boolean.parseBoolean(elem.getAttribute("maked")));
			if(e.isMaked()){
				e.setVote(Integer.parseInt(elem.getAttribute("vote")));
				e.setLode(Boolean.parseBoolean(elem.getAttribute("laude")));
			}
			examsList.add(e);
		}
		elem = (Element) doc.getDocumentElement().getElementsByTagName("statistics").item(0);
		
		average = Double.parseDouble(elem.getAttribute("average"));
		degree = Double.parseDouble(elem.getAttribute("degree"));
		cfu = Integer.parseInt(elem.getAttribute("cfu"));
	}
	
	public void save(Collection exams, double average, int cfu, double degree){
		DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = fact.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO
			e.printStackTrace();
		}
		Document doc = builder.newDocument();
		Element root = doc.createElement(TITLE.toLowerCase());
		root.setAttribute("version", VERSION);
		
		Element examsElement = doc.createElement("exams");
		
		
		Element element;
		Exam exam;
		Iterator it = exams.iterator();
		while (it.hasNext()) {
			exam = (Exam) it.next();
			element = doc.createElement("exam");
			//System.out.println(exam.getName());
			element.setAttribute("name", exam.getName());
			element.setAttribute("cfu", "" + exam.getCfu());
			if(exam.isMaked()){
				element.setAttribute("maked", "true");
				element.setAttribute("vote", "" + exam.getVote());
				element.setAttribute("laude", exam.isLode() ? "true" : "false");
			} 
			else 
				element.setAttribute("maked", "false");
			examsElement.appendChild(element);
		}
		root.appendChild(examsElement);
		
		element = doc.createElement("statistics");
		element.setAttribute("average", ""+average);
		element.setAttribute("cfu", ""+cfu);
		element.setAttribute("degree", ""+degree);
		
		root.appendChild(element);
		
		TransformerFactory transFac = TransformerFactory.newInstance();
		Transformer trans = null;
		try {
			trans = transFac.newTransformer();
		} catch (TransformerConfigurationException e1) {
			// TODO
			e1.printStackTrace();
		}
		
		DOMSource source = new DOMSource(root);
		StreamResult result = new StreamResult(file);
		
		try {
			trans.transform(source, result);
		} catch (TransformerException e2) {
			// TODO
			e2.printStackTrace();
		}
		//Collections.copy((List) examsList, (List) exams); TODO
	}
	
	public File getFile() {
		return file;
	}
	
	public double getAverage() {
		return average;
	}
	
	public int getCfu() {
		return cfu;
	}
	
	public double getDegree() {
		return degree;
	}
	
	public Collection getExamsList() {
		return examsList;
	}

	public Collection getOnlyPositiveExams() {
		Collection result = new ArrayList();
		Object[] exams = getExamsList().toArray();
		
		for(int i=0;i<exams.length;i++) {
			if (((Exam) exams[i]).getVote() >= 17) {
				result.add(exams[i]);
			}
		}
		return result;
	}
}
