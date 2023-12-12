package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PeçaXadrez;
import xadrez.PosiçaoXadrez;

public class UI {
	
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	public static void limparTela() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
	
	public static PosiçaoXadrez lerPosiçaoXadrez(Scanner sc) {
		try {
			String s = sc.nextLine();
			char coluna = s.charAt(0);
			int linha = Integer.parseInt(s.substring(1));
			return new PosiçaoXadrez(coluna, linha);
		}
		catch (RuntimeException e) {
			throw new InputMismatchException("Erro ao ler a posiçao do Xadrez, valores validos a1 ao h8");
		}
	}
	
	public static void printPartida(PartidaXadrez partidaXadrez) {
		printTaboleiro(partidaXadrez.getPeças());
		System.out.println();
		System.out.println("Turno : " + partidaXadrez.getTurno());
		System.out.println("Aguardando jogador: " + partidaXadrez.getJogadorAtual());
	}
	
	public static void printTaboleiro(PeçaXadrez[][] peças) {
		for (int i=0; i<peças.length; i++) {
			System.out.print((8 - i) + " ");
			for(int j=0; j<peças.length; j++) {
				printPeça(peças[i][j], false);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	public static void printTaboleiro(PeçaXadrez[][] peças, boolean[][] movimentosPossiveis) {
		for (int i=0; i<peças.length; i++) {
			System.out.print((8 - i) + " ");
			for(int j=0; j<peças.length; j++) {
				printPeça(peças[i][j], movimentosPossiveis[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	private static void printPeça(PeçaXadrez peça, boolean background) {
		if (background) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		if (peça == null) {
			System.out.print("-" + ANSI_RESET);
		}
		else {
			if (peça.getCor() == Cor.BRANCO){
				System.out.print(ANSI_WHITE + peça + ANSI_RESET);
			}
			else {
				System.out.print(ANSI_YELLOW + peça + ANSI_RESET);
			}
		}
		System.out.print(" ");
	}

}
