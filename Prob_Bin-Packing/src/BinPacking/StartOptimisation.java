package BinPacking;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class StartOptimisation {
	static int Length = 10;
	static int nombreArtic = 20;
	static float capacite = 10;

	public static void main(String[] args) throws IOException {
		ArrayList<Article> A = new ArrayList<Article>();
		ArrayList<Boite> B = new ArrayList<Boite>();
		Optimisation O = new Optimisation();

		File file = new File(
				"/home/chimou/workspace/Prob_Bin-Packing/HARD1.BPP");
		/**
		 * Parametre de la fonction objective pour HARD0.BPP objective = 56 pour
		 * HARD1.BPP objective = 57 pour HARD2.BPP objective = 56
		 * 
		 * */
		int objective = 57;
		BufferedReader lecteurAvecBuffer = null;
		String ligne;

		try {
			lecteurAvecBuffer = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException exc) {
			System.out.println("Erreur d'ouverture");
		}
		int l = 0;
		int p = 0;
		while ((ligne = lecteurAvecBuffer.readLine()) != null) {
			if (l == 1) {
				capacite = Float.valueOf(ligne);
				l++;
			} else if (l == 0) {
				nombreArtic = Integer.valueOf(ligne);
				l++;
			} else {
				A.add(new Article(p, Float.valueOf(ligne)));
				p++;
			}
		}
		lecteurAvecBuffer.close();
		System.out
				.println("=========================================================================");
		System.out
				.println("=                       PROBLEME DE BIN-PACKING                         =");
		System.out
				.println("=            MASTER 2 MATIS, UNIVERSITÉ DU HAVRE, 2012-2013             =");
		System.out
				.println("=                       CHIBANE MOURAD                                  =");
		System.out
				.println("=========================================================================\n");
		System.out.println(" Paramêtres d'entrée :( Nombre d'articles = "
				+ nombreArtic + " || " + "Capacité de chauqe boite = "
				+ capacite + " )\n");
		System.out.println(" Charge actuelle des boites = " + B.size()
				+ " , Espace libre dans chaque boite = " + capacite
				+ " unité\n");
		// Best_Fit_Desc
		Best_Fit_Desc(B, A);
		int c = 0;
		for (Boite C : B) {

			System.out.print("\n Boite(N°: \"" + C.getNumBoite()
					+ "\", Charge Totale = " + C.getChargeAccu()
					+ ", Contient les Articles : [ ");
			for (Article a : C.getT()) {
				System.out.print("Article(N°: \"" + a.getNum() + "\", Poids = "
						+ a.getPoid() + "), ");
				c++;
			}
			System.out.println("] )");
		}
		int Best_Fit_Desc = B.size();
		System.out.println("\nNombre totale d'aticles traités : " + c
				+ " Articles \n");
		System.out.println("Nombre de boites utilisées : " + B.size()
				+ " Boites \n");

		System.out.println("Est-il possible d'améliorer le resultat ? ==> "
				+ O.Amelioration_Possible(B) + "\n");
		System.out
				.println("Totale d'espace libre inutilisé avant l'optimisation : "
						+ O.taille_libre_inutilise(B) + " unité\n");
		System.out
				.println("Théoriquement le poids totale des Articles peut-être contenu dans : "
						+ O.Borne_min(A, capacite) + " Boites\n");
		System.out
				.println("========================================================================================");
		System.out
				.println("=                DÉTAILS DES BOITES APRÈS TRAITEMENT & OPTIMISATION                    =");
		System.out
				.println("========================================================================================\n");

		while (B.size() > objective) {
			O.optimiser_remplire_premiers_rangs(B, objective);

			O.optimiser_permutation(B, objective);

			// j++;
		}
		for (Boite C : B)
			System.out.println("Espace libre dans la boite \""
					+ C.getNumBoite() + "\" ===> "
					+ (C.getCap() - C.getChargeAccu()) + " Unité");

		c = 0;
		for (Boite C : B)
			if (C.getNumBoite() == B.size() - 1) {
				System.out.print("\n Boite(N°: \"" + C.getNumBoite()
						+ "\", Charge Totale = " + C.getChargeAccu()
						+ ", Contient les Articles : [ ");
				for (Article a : C.getT()) {
					System.out.print("Article(N°: \"" + a.getNum()
							+ "\", Poids = " + a.getPoid() + "), ");
					c++;
				}
				System.out.println("] )");
			}
		System.out.println(" Nombre d'article dans la dernière boite : " + c);

		System.out.println(" Théoriquement il nous faut : "
				+ O.Borne_min(A, capacite)
				+ " Boites, pour contenir tout les articles \n");
		System.out
				.println("Nombre de boites utilisées dans notre cas, après optimisation : "
						+ B.size() + " Boites");
		System.out
				.println("=============================================================================================");
		System.out
				.println("Nombre de boites utilisées en utilisant la méthode de résolution \"Best_Fit_Desc\" :"
						+ Best_Fit_Desc + " Boites");

	}

	static void Best_Fit_Desc(ArrayList<Boite> B, ArrayList<Article> A) {
		ArrayList<Article> AA = new ArrayList<Article>();

		B.add(new Boite(capacite, 0));
		Article tmp = null;
		while (!A.isEmpty()) {
			Article a = A.get(0);
			for (Article b : A)

				if (a.getPoid() < b.getPoid()) {
					tmp = b;
					a = b;

				} else
					tmp = a;

			A.remove(a);
			AA.add(tmp);

		}
		A.addAll(AA);
		System.out.println(" Détails des articles : Numéro / Poids : ");
		for (Article a : A) {
			System.out.print("\n Article(N°: " + a.getNum() + ", Poids = "
					+ a.getPoid() + " unité )\n");

		}
		while (!A.isEmpty()) {
			tmp = A.get(0);

			for (Boite b : B) {
				if (b.ajouter(tmp)) {
					A.remove(0);
					break;
				} else if (b.getCap() - b.getChargeAccu() < tmp.getPoid()
						&& tmp.getPoid() < capacite
						&& (B.size() - 1) == b.getNumBoite()) {
					B.add(new Boite(capacite, B.size()));

					break;
				} else if (tmp.getPoid() > capacite) {
					A.remove(0);
					break;
				}

			}

		}
		A.addAll(AA);
	}

}
