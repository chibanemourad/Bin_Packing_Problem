package BinPacking;

import java.util.ArrayList;

/**
 * @author CHIBANE MOURAD classe Boite implementation des caractéristiques d'une
 *         boite et ses méthodes
 * 
 */
public class Boite {
	int numBoite; // Numero de la boite
	float chargeAccu; // Charge accumulée
	float cap;
	ArrayList<Article> t = new ArrayList<Article>();

	/**
	 * Essayer d'ajouter un article s'il reste de l'espace dans la boite
	 * 
	 * @param Article
	 * @return <code>true</code> ou <code>false</code>
	 * */
	public boolean ajouter(Article a) {

		if (a.getPoid() + this.chargeAccu <= this.cap) {
			t.add(a);
			this.chargeAccu = this.chargeAccu + a.getPoid();
			return true;
		}

		return false;
	}

	public Boite(Boite b) {
		// TODO Auto-generated constructor stub
		this.cap = b.getCap();
		this.numBoite = b.getNumBoite();
		this.chargeAccu = b.getChargeAccu();
		this.t.addAll(b.getT());
	}

	/**
	 * Récuperer la acharge accumulée de la boite
	 * 
	 * @return chargeAccu
	 * */
	public void calcule_poids_actuel() {
		float poids = 0;
		for (Article a : t) {
			poids += a.getPoid();
		}
		this.chargeAccu = poids;
	}

	public Boite(float a, int i) {
		this.cap = a;
		this.numBoite = i;
		this.chargeAccu = 0;
	}

	/**
	 * Getter & Setter
	 * */

	public int getNumBoite() {
		return numBoite;
	}

	public void setNumBoite(int numBoite) {
		this.numBoite = numBoite;
	}

	public float getCap() {
		return cap;
	}

	public void setCap(float cap) {
		this.cap = cap;
	}

	public ArrayList<Article> getT() {
		return t;
	}

	public void setT(ArrayList<Article> t) {
		this.t = t;
	}

	public float getChargeAccu() {
		// TODO Auto-generated method stub
		return this.chargeAccu;
	}

	public void setChargeAccu(float chargeAccu) {
		this.chargeAccu = chargeAccu;
	}

}
