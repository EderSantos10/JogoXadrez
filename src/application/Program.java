package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.ExceçaoXadrez;
import xadrez.PartidaXadrez;
import xadrez.PeçaXadrez;
import xadrez.PosiçaoXadrez;

public class Program {

	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		List <PeçaXadrez> capturada = new ArrayList<>();
		
		while (!partidaXadrez.getCheckMate()) {
			try {
				UI.limparTela();
				UI.printPartida(partidaXadrez, capturada);
				System.out.println();
				System.out.print("Inicio: ");
				PosiçaoXadrez inicial = UI.lerPosiçaoXadrez(sc);
				
				boolean[][] movimentoPossiveis = partidaXadrez.movimentoPossiveis(inicial);
				UI.limparTela();
				UI.printTaboleiro(partidaXadrez.getPeças(), movimentoPossiveis);
				
				System.out.println();
				System.out.print("Destino: ");
				PosiçaoXadrez destino = UI.lerPosiçaoXadrez(sc);
				
				PeçaXadrez capturadaPeça = partidaXadrez.performaceMovimentoXadrez(inicial, destino);
				
				if (capturadaPeça != null) {
					capturada.add(capturadaPeça);
				}
								
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
		UI.limparTela();
		UI.printPartida(partidaXadrez, capturada);
	}
}

	


