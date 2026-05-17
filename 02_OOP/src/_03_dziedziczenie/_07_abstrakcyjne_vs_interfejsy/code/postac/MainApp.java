package _03_dziedziczenie._07_abstrakcyjne_vs_interfejsy.code.postac;

public class MainApp {
	public static void main(String[] args) {
//		Postac postac = new Postac();
//
//		postac.strzelaj();
//
//		Zolnierz zolnierz = postac;
//		zolnierz.strzelaj();
//
//		Pilkarz pilkarz = postac;
//		pilkarz.strzelaj();
//
//		((Pilkarz)postac).strzelaj();
//		Pilkarz pilkarz1 = postac;
//		pilkarz1.strzelaj();
//		Zolnierz zolnierz1 = postac;
//		zolnierz1.strzelaj();

		// rozwiązanie Javowe
		PostacExplicit postacExplicit = new PostacExplicit();
		postacExplicit.asPilkarz().strzelaj();
		postacExplicit.asZolnierz().strzelaj();
	}
}
