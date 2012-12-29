package BinPacking;

import java.util.ArrayList;

/**
 * @author CHIBANE MOURAD classe Optimisation, implementation des méthodes
 *         d'optimisations
 */
public class Optimisation {
	// liste des solutions pour obtenir un voisins tels que les boites
	static ArrayList<Permutation_Artic> Permut_1 = new ArrayList<Permutation_Artic>();
	static ArrayList<Permutation_Artic> Permut_2 = new ArrayList<Permutation_Artic>();
	static ArrayList<Article> A = new ArrayList<Article>();

	/**
	 * Methode pour vérifier si une ameliration du résultat est possible ou pas
	 * 
	 * @return <code>true</code> ou <code>false</code>
	 * */
	public boolean Amelioration_Possible(ArrayList<Boite> B) {
		boolean possible = true;
		for (Boite b : B) {
			for (Boite bb : B)
				if (!bb.equals(b) && b.getNumBoite() < bb.getNumBoite())
					for (Article a : bb.getT())
						if (b.getCap() - b.getChargeAccu() >= a.getPoid())
							possible = false;

		}
		return possible;
	}

	/**
	 * Calcule le poids total libre inutilisé avant optimisation
	 * */
	public float taille_libre_inutilise(ArrayList<Boite> B) {
		float inutilise = 0;
		for (Boite b : B)
			inutilise += b.getCap() - b.getChargeAccu();
		return inutilise;
	}

	public float Borne_min(ArrayList<Article> a, float capacite) {
		float poids_total = 0;
		for (Article b : a)
			poids_total += b.getPoid();
		return Math.round(poids_total / capacite + 0.5);
	}

	public void optimiser_remplire_premiers_rangs(ArrayList<Boite> B,
			int objectif1) {
		ArrayList<Boite> BB = new ArrayList<Boite>(B);
		Boite t = null;
		int objectif = objectif1;
		while (BB.size() > objectif) {

			for (Boite b : BB) {

				if (b.getChargeAccu() == 0) {
					// System.out.println("il faut supprimer une boite "
					// + b.getNumBoite() + " son poids actuel est: "
					// + b.getChargeAccu());
					t = b;

				}

				else {
					Boite bb = BB.get(BB.size() - 1);
					if (bb.getNumBoite() == BB.size() - 1
							&& b.getNumBoite() < bb.getNumBoite()) {

						for (Article aa : bb.getT()) {

							if (b.getCap() - b.getChargeAccu() > aa.getPoid()) {
								Permut_2.add(new Permutation_Artic(aa, b, bb));

							}
						}

					}
					if (!Permut_2.isEmpty()) {
						for (Permutation_Artic x : Permut_2)
							BB = effectuer_transformation_simple(x, BB);
						Permut_2.removeAll(Permut_2);

					} else {
						objectif = 1000;
					}

				}

			}

			if (t != null) {
				t.getT().removeAll(t.getT());
				t.calcule_poids_actuel();
				BB.remove(t);
				t = null;
			}

		}

		B.removeAll(B);
		for (Boite b : BB)
			for (Boite bb : BB) {
				if (b.getNumBoite() < bb.getNumBoite())
					permuter_article_boite(b, bb);
			}
		B.addAll(BB);

	}

	public void optimiser_permutation(ArrayList<Boite> B, int objectif1) {
		ArrayList<Boite> BB = new ArrayList<Boite>();
		float poids, poids1;
		Boite t = null;
		BB.addAll(B);
		int objectif = objectif1;
		while (BB.size() > objectif) {

			for (Boite b : BB) {

				if (b.getChargeAccu() == 0) {
					t = b;

				}

				else {
					poids = b.getChargeAccu();
					for (Boite bb : BB)

						if (b.getNumBoite() < bb.getNumBoite()) {
							for (Article a : b.getT()) {
								for (Article aa : bb.getT()) {
									poids1 = b.getChargeAccu() - a.getPoid()
											+ aa.getPoid();
									if (poids1 > poids
											&& poids1 < b.getCap()
											&& (bb.getChargeAccu()
													- aa.getPoid() + a
														.getPoid()) < bb
													.getCap()
											&& poids1 < b.getCap()
											&& a.Hors_liste(A)
											&& aa.Hors_liste(A)) {
										Permut_1.add(new Permutation_Artic(a,
												aa, b, bb));
										poids = poids1;
										A.add(a);
										A.add(aa);
									}
								}
							}

						}
					if (!Permut_1.isEmpty()) {
						for (Permutation_Artic x : Permut_1)
							BB = effectuer_transformation_permutation(x, BB);
						Permut_1.removeAll(Permut_1);
						A.remove(0);
						A.remove(0);

					} else if (!A.isEmpty()) {
						A.remove(0);
						A.remove(0);
					} else
						objectif = 1000;

				}
			}

			if (t != null) {

				t.getT().removeAll(t.getT());
				t.calcule_poids_actuel();
				BB.remove(t);
				t = null;

			}

		}
		B.removeAll(B);
		for (Boite b : BB)
			for (Boite bb : BB) {
				if (b.getNumBoite() < bb.getNumBoite())
					permuter_article_boite(b, bb);
			}
		B.addAll(BB);

	}

	public ArrayList<Boite> effectuer_transformation_simple(
			Permutation_Artic a, ArrayList<Boite> B) {
		boolean test = false;

		for (Boite b : B) {
			if (b.getNumBoite() == a.getBoite_1().getNumBoite()) {
				test = b.ajouter(a.getArti_2());
				b.calcule_poids_actuel();

			}
			if (test && b.getNumBoite() == a.getBoite_2().getNumBoite()) {
				b.getT().remove(a.getArti_2());
				b.calcule_poids_actuel();
			}
		}
		return B;
	}

	public ArrayList<Boite> effectuer_transformation_permutation(
			Permutation_Artic a, ArrayList<Boite> B) {
		boolean test = false;
		for (Boite b : B)
			if (b.getNumBoite() == a.getBoite_1().getNumBoite()) {
				b.getT().remove(a.getArti_1());
				b.calcule_poids_actuel();
				test = b.ajouter(a.getArti_2());
				if (test) {
					b.calcule_poids_actuel();
				} else {
					b.ajouter(a.getArti_1());
				}
			}

		for (Boite b : B)

			if (test && b.getNumBoite() == a.getBoite_2().getNumBoite()) {
				b.getT().remove(a.getArti_2());
				b.calcule_poids_actuel();
				test = b.ajouter(a.getArti_1());
				if (test) {
					b.calcule_poids_actuel();
				} else {
					b.ajouter(a.getArti_2());
				}

			}

		return B;
	}

	public void permuter_article_boite(Boite b1, Boite b2) {
		ArrayList<Article> a = new ArrayList<Article>();
		if (b1.getCap() - b1.getChargeAccu() > b2.getCap() - b2.getChargeAccu()) {
			a.addAll(b1.getT());
			b1.getT().removeAll(b1.getT());
			b1.getT().addAll(b2.getT());
			b1.calcule_poids_actuel();
			b2.getT().removeAll(b2.getT());
			b2.getT().addAll(a);
			b2.calcule_poids_actuel();
		}

	}

}
