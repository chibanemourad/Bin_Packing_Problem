package BinPacking;

import java.util.ArrayList;

/**
 * @author CHIBANE MOURAD classe Article, implementation des caractéristiques
 *         d'un Article et ses méthodes
 * 
 */
public class Article {
	int num; // indice de l'article
	float poid; // poids de l'article

	public Article() {
		// TODO Auto-generated constructor stub
		this.num = -1;
		this.poid = 0;
	}

	public Article(int i, float j) {
		this.num = i;
		this.poid = j;
	}

	public Article(Article article) {
		// TODO Auto-generated constructor stub
		this.num = article.getNum();
		this.poid = article.getPoid();
	}

	public boolean Hors_liste(ArrayList<Article> A) {
		if (A.indexOf(this) == -1)
			return true;
		else
			return false;
	}

	/**
	 * Getter & Setter
	 * */
	public void setPoid(float p) {
		this.poid = p;
	}

	public float getPoid() {
		return this.poid;
	}

	public int getNum() {
		return this.num;
	}

	public void setNum(int i) {
		this.num = i;
	}

}
