package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;

	public Village(String nom, int nbVillageoisMaximum) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
	}

	// classe interne //
	private static class Marche {
		private Etal[] etals;

		public Marche(int nbetals) {
			etals = new Etal[nbetals]; // creation d'un tableau d'etals
			// creer les objets concrets pour remplir les cases
			for (int i = 0; i < nbetals; i++) {
				etals[i] = new Etal();
			}

		}

		void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			// l'emplacement de l'etal est-il valide ?
			if (indiceEtal >= 0 && indiceEtal < etals.length) {
				etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);

			}
		}

		int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (!etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;

		}

		Etal[] trouverEtals(String produit) {
			int cpt = 0;
			// calcul du cpt ( nombre exacte des etals )
			for (Etal etal : etals) {
				if (etal.contientProduit(produit))
					cpt++;
			}
			// creation d'un tableau avec cpt
			Etal[] resultat = new Etal[cpt];
			int j = 0;
			for (Etal etal : etals) {
				if (etal.contientProduit(produit)) {
					resultat[j] = etal;
					j++;
				}
			}
			return resultat;
		}

		Etal trouverVendeur(Gaulois gaulois) {
			// verifier si l'etal est occupé et si
			// le vendeur est le gaulois passé en paramétre

			for (Etal etal : etals) {
				if (etal.isEtalOccupe() && etal.getVendeur() == gaulois) {
					return etal;
				}
			}
			return null;
		}

		public String afficherMarche() {
			StringBuilder sb = new StringBuilder();
			int nbretalvide = 0 ;
			for (Etal etal : etals) {
				if (etal.isEtalOccupe()) {
					sb.append(etal.afficherEtal());
				} else {
					nbretalvide++;
				}
			}
			if ( nbretalvide > 0 ){
				sb.append("Il reste ").append(nbretalvide).append(" étals non utilisés dans le marché ");
				
			}
			return sb.toString();
			
		}

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
}