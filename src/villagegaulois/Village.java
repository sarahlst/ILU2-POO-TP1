package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum,int nbetals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbetals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}

	private static class Marche {
		private Etal[] etals; // tableau d'etals

		// Constructeur
		public Marche(int nbetals) {
			for (int i = 0; i < nbetals; i++) {
				etals[i] = new Etal();

			}
		}

		void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			// Verifier si l'etal est libre et l'indice est valide

			if ((!etals[indiceEtal].isEtalOccupe()) && (indiceEtal >= 0 && indiceEtal < etals.length)) {
				etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
			}

		}

		int trouverEtalLibre() {

			for (int i = 0; i < etals.length; i++) {
				// si l'etal n'est pas occupe on retourne lindice
				if (!etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}

		Etal[] trouverEtals(String produit) {
			// trouver le nombre etals qui vendent le produit
			int cpt = 0, j = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					cpt++;
				}
			}
			// Creation du tableau
			Etal[] res = new Etal[cpt];
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					res[j] = etals[i];
					j++;
				}
			}

			return res;
		}

		Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe() && etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			return null;
		}

		private String afficherMarche() {
			StringBuilder sb = new StringBuilder();
			int nbEtalVide = 0;

			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					sb.append(etals[i].afficherEtal());
				} else {
					nbEtalVide++;
				}
			}
			if (nbEtalVide > 0) {
				sb.append("Il reste " + nbEtalVide + "étals non utilisés dans le marché.\n");
			}
			return sb.toString();

		}

	}
}
