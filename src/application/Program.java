package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.ExceçaoXadrez;
import xadrez.PartidaXadrez;
import xadrez.PeçaXadrez;
import xadrez.PosiçaoXadrez;

public class Program {

	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		
		while (true) {
			try {
				UI.limparTela();
				UI.printTaboleiro(partidaXadrez.getPeças());
				System.out.println();
				System.out.print("Inicio: ");
				PosiçaoXadrez inicial = UI.lerPosiçaoXadrez(sc);
				
				System.out.println();
				System.out.print("Destino: ");
				PosiçaoXadrez destino = UI.lerPosiçaoXadrez(sc);
				
				PeçaXadrez capturadaPeça = partidaXadrez.performaceMovimentoXadrez(inicial, destino);					
			}
		
			catch (ExceçaoXadrez e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			
			}
		}
	}
}

	


