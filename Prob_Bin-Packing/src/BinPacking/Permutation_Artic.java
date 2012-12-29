package BinPacking;

/**
 * @author CHIBANE MOURAD classe Permutation_Artic, Retourne les
 *         caracthéristiques de deux articles appartenants à deux boites
 *         différentes pour vérifier une possible permutation
 * 
 */

public class Permutation_Artic {
	// Article et boite sources
	Article Arti_1;
	Boite Boite_1;
	// Article et boite cibles
	Article Arti_2;
	Boite Boite_2;

	public Permutation_Artic(Article Arti_1, Article Arti_2, Boite Boite_1,
			Boite Boite_2) {
		this.Arti_2 = Arti_2;
		this.Arti_1 = Arti_1;
		this.Boite_1 = Boite_1;
		this.Boite_2 = Boite_2;
	}

	public Permutation_Artic(Article Arti_2, Boite Boite_1, Boite Boite_2) {
		this.Arti_2 = Arti_2;
		this.Arti_1 = null;
		this.Boite_1 = Boite_1;
		this.Boite_2 = Boite_2;
	}

	/**
	 * Getter & Setter
	 * */
	public Article getArti_1() {
		return Arti_1;
	}

	public void setArti_1(Article Arti_1) {
		this.Arti_1 = Arti_1;
	}

	public Article getArti_2() {
		return Arti_2;
	}

	public void setArti_2(Article Arti_2) {
		this.Arti_2 = Arti_2;
	}

	public Boite getBoite_1() {
		return Boite_1;
	}

	public void setBoite_1(Boite Boite_1) {
		this.Boite_1 = Boite_1;
	}

	public Boite getBoite_2() {
		return Boite_2;
	}

	public void setBoite_2(Boite Boite_2) {
		this.Boite_2 = Boite_2;
	}
}
