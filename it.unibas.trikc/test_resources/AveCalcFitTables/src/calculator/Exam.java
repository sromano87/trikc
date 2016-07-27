package calculator;

import java.io.Serializable;

import javax.swing.JOptionPane;

/**
 * Classe Exam.
 * Bean representing an exam.
 * 
 * @author   andima
 */

public class Exam implements Serializable {
	
	public String name;
	public int cfu;
	public int vote;
	public boolean laude;
	public boolean maked;
	
	public Exam(){
		name = "Unknow";
		cfu = -1;
		vote = -1;
		laude = false;
		maked = false;
	}
	
	public static Exam getInstance(String name, String cfu, String vote){
		Exam e = new Exam();
		e.setName(name);
		e.setCfu(Integer.parseInt(cfu));
		if (vote.trim().endsWith("laude")) {
			e.setVote(30);
			e.setLode(true);
			e.setMaked(true);
		} else if(vote.indexOf("-") == -1){
			e.setVote(Integer.parseInt(vote));
			e.setMaked(true);
		} else e.setMaked(false);
		// System.out.println(e);
		return e;
	}
	
	/**
	 * @return   Returns the cfu.
	 * @uml.property   name="cfu"
	 */
	public int getCfu() {
		return cfu;
	}
	
	/**
	 * @param cfu   The cfu to set.
	 * @uml.property   name="cfu"
	 */
	public void setCfu(int cfu) {
		if (cfu <= 0)
			throw new IllegalArgumentException("Cfu must to be a positive number");
		this.cfu = cfu;
	}
	
	/**
	 * @return   Returns the lode.
	 * @uml.property   name="laude"
	 */
	public boolean isLode() {
		return laude;
	}
	
	/**
	 * @param lode   The lode to set.
	 * @uml.property   name="laude"
	 */
	public void setLode(boolean lode) {
		if (vote != 30 && lode)
			throw new IllegalArgumentException(
			"The vote must to be equals to 30 to receive a laude");
		this.laude = lode;
	}
	
	/**
	 * @return   Returns the name.
	 * @uml.property   name="name"
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name   The name to set.
	 * @uml.property   name="name"
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return   Returns the vote.
	 * @uml.property   name="vote"
	 */
	public int getVote() {
		return vote;
	}
	
	/**
	 * @param vote   The vote to set.
	 * @uml.property   name="vote"
	 */
	public void setVote(int vote) {
		if (vote < 0 || vote > 30)
			throw new IllegalArgumentException(
			"vote must to be a number < 30 and > 0");
		this.vote = vote;
	}
	
	public int hashCode(){
		return toString().hashCode();
	}
	
	public String toString(){
		return "["+name+", "+cfu+", "+vote+(laude ? " cum laude" : "")+"]";
	}
	
	public boolean equals(Object obj){
		if(!(obj instanceof Exam))
			return false;
		
		Exam e = (Exam) obj;
		
		if (e.name.equals(name) && e.cfu == cfu && e.vote == vote
				&& e.laude == laude && e.maked == maked)
			return true;
		
		return false;
	}
	
	public boolean isMaked() {
		return maked;
	}
	
	public void setMaked(boolean maked) {
		this.maked = maked;
	}
}
