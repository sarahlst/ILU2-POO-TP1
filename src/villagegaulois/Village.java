package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;  


	/*public Village(String nom, int nbVillageoisMaximum) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
	}*/ 
	public Village(String nom,int nbVillageoisMaximum,int nbEtals) {
        this.nom = nom;
        villageois = new Gaulois[nbVillageoisMaximum];
        this.marche = new Marche(nbEtals);  // 🔹 création du marché interne ici
    }
	
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
	    StringBuilder chaine = new StringBuilder();
	    chaine.append(vendeur.getNom())
	          .append(" cherche un endroit pour vendre ")
	          .append(nbProduit).append(" ").append(produit).append(".\n");

	    int indiceEtal = marche.trouverEtalLibre();
	    if (indiceEtal != -1) {
	        marche.utiliserEtal(indiceEtal, vendeur, produit, nbProduit);
	        chaine.append("Le vendeur ").append(vendeur.getNom())
	              .append(" vend des ").append(produit)
	              .append(" à l'étal n°").append(indiceEtal + 1).append(".\n");
	    } else {
	        chaine.append("Il n’y a plus d’étal libre pour ").append(vendeur.getNom()).append(".\n");
	    }
	    return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		 StringBuilder chaine = new StringBuilder();
		 Etal[] etalsproduit = marche.trouverEtals(produit);
		 if ( etalsproduit.length == 0 ) {
			 chaine.append("Il n'y a pas de vendeur qui propose ").append(produit).append(" ").append("au marché.").append(".\n");
		 }else if (etalsproduit.length == 1) {
		        chaine.append("Seul le vendeur ").append(etalsproduit[0].getVendeur().getNom())
	              .append(" propose des ").append(produit).append(" au marché.\n");
	    } else {
	        chaine.append("Les vendeurs qui proposent des ").append(produit).append(" sont :\n");
	        for (Etal etal : etalsproduit) {
	            chaine.append("-"
	            		+ " ").append(etal.getVendeur().getNom()).append("\n");
	        }
	    }
	    return chaine.toString();
	}

	public Etal rechercherEtal(Gaulois vendeur) {
	    return marche.trouverVendeur(vendeur);
	}


	public String partirVendeur(Gaulois vendeur) {
	    StringBuilder chaine = new StringBuilder();
	    Etal etal = marche.trouverVendeur(vendeur);
	    if (etal == null) {
	        chaine.append("Le vendeur ").append(vendeur.getNom()).append(" n'est pas au marché !\n");
	    } else {
	        chaine.append(etal.libererEtal()).append("\n");
	    }
	    return chaine.toString();
	}


	public String afficherMarche() {
	    StringBuilder chaine = new StringBuilder();
	    chaine.append("Le marché du village \"").append(nom).append("\" possède plusieurs étals :\n");
	    chaine.append(marche.afficherMarche());
	    return chaine.toString();
	} 
	


	// classe interne //
	static class Marche {
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