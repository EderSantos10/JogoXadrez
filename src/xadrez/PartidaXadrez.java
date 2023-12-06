package xadrez;

import taboleirojogo.Taboleiro;
import xadrez.peça.Rei;
import xadrez.peça.Torre;

public class PartidaXadrez {
	
	private Taboleiro taboleiro;
	
	public PartidaXadrez() {
		taboleiro = new Taboleiro(8, 8);
		setupInicial();
	}
	
	public PeçaXadrez[][] getpeças(){
		PeçaXadrez[][] mat = new PeçaXadrez[taboleiro.getLinhas()][taboleiro.getColunas()];
		for (int i=0; i<taboleiro.getLinhas(); i++) {
			for (int j=0; j<taboleiro.getColunas(); j++) {
				mat[i][j] = (PeçaXadrez) taboleiro.peça(i, j);
			}
		}
		return mat;
	}
	
	private void lugarNovaPeça(char coluna, int linha, PeçaXadrez peça) {
		taboleiro.lugarPeça(peça, new PosiçaoXadrez(coluna, linha).aPosiçao());
		
	}

	private void setupInicial() {
		lugarNovaPeça('c' , 1, new Torre(taboleiro, Cor.BRANCO));
		lugarNovaPeça('c' , 2, new Torre(taboleiro, Cor.BRANCO));
		lugarNovaPeça('d' , 2, new Torre(taboleiro, Cor.BRANCO));
		lugarNovaPeça('e' , 2, new Torre(taboleiro, Cor.BRANCO));
		lugarNovaPeça('e' , 1, new Torre(taboleiro, Cor.BRANCO));
		lugarNovaPeça('d', 1, new Rei(taboleiro, Cor.BRANCO));
		
		lugarNovaPeça('c' , 7, new Torre(taboleiro, Cor.PRETO));
		lugarNovaPeça('c' , 8, new Torre(taboleiro, Cor.PRETO));
		lugarNovaPeça('d' , 7, new Torre(taboleiro, Cor.PRETO));
		lugarNovaPeça('e' , 7, new Torre(taboleiro, Cor.PRETO));
		lugarNovaPeça('e' , 8, new Torre(taboleiro, Cor.PRETO));
		lugarNovaPeça('d', 8, new Rei(taboleiro, Cor.PRETO));
		
	}
	
}
