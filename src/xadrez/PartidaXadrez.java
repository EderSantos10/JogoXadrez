package xadrez;

import taboleirojogo.Posiçao;
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

	private void setupInicial() {
		taboleiro.lugarPeça(new Torre(taboleiro, Cor.BRANCO), new Posiçao(2, 1));
		taboleiro.lugarPeça(new Rei(taboleiro, Cor.PRETO), new Posiçao(0, 4));
		taboleiro.lugarPeça(new Rei(taboleiro, Cor.BRANCO), new Posiçao(7, 4));
		
	}
	
}
