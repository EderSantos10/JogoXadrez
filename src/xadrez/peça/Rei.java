package xadrez.peça;

import taboleirojogo.Posiçao;
import taboleirojogo.Taboleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PeçaXadrez;

public class Rei extends PeçaXadrez{
	
	private PartidaXadrez partidaXadrerz;

	public Rei(Taboleiro taboleiro, Cor cor, PartidaXadrez partidaXadrez) {
		super(taboleiro, cor);
		this.partidaXadrerz = partidaXadrez;
	}
	
	@Override
	public String toString() {
		return "R";
	}
	
	private boolean podeMover(Posiçao posiçao) {
		PeçaXadrez p = (PeçaXadrez) getTaboleiro().peça(posiçao);
		return p == null || p.getCor() != getCor();
	}
	
	private boolean testRoqueCastling(Posiçao posiçao) {
		PeçaXadrez p = (PeçaXadrez)getTaboleiro().peça(posiçao);
		return p != null && p instanceof Torre && p.getCor() == getCor() && p.getContadorMovimento() == 0;
	}

	@Override
	public boolean[][] movimentoPossiveis() {
		boolean [][] mat = new boolean[getTaboleiro().getLinhas()][getTaboleiro().getColunas()];
		
		Posiçao p = new Posiçao(0, 0);
		
		// acima
		p.setValues(posiçao.getLinha() - 1, posiçao.getColuna());
		if (getTaboleiro().posiçaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// abaixo
		p.setValues(posiçao.getLinha() + 1, posiçao.getColuna());
		if (getTaboleiro().posiçaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			}
		
		// direita
		p.setValues(posiçao.getLinha() , posiçao.getColuna() + 1);
		if (getTaboleiro().posiçaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			}
		
		// esquerda
		p.setValues(posiçao.getLinha() , posiçao.getColuna() - 1);
		if (getTaboleiro().posiçaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			}
		
		// diagonal superior esquerda
		p.setValues(posiçao.getLinha() - 1, posiçao.getColuna() - 1);
		if (getTaboleiro().posiçaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			}
		
		// diagonal superior direita
		p.setValues(posiçao.getLinha() - 1, posiçao.getColuna() + 1);
		if (getTaboleiro().posiçaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			}
		
		// diagonal inferior esquerda
		p.setValues(posiçao.getLinha() + 1, posiçao.getColuna() - 1);
		if (getTaboleiro().posiçaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			}
		
		// diagonal inferior direita
		p.setValues(posiçao.getLinha() + 1, posiçao.getColuna() + 1);
		if (getTaboleiro().posiçaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			}
		
		// #specialmove castling
		if (getContadorMovimento() == 0 && !partidaXadrerz.getCheck()) {
			// #specialmove castling kingside rook
			Posiçao posT1 = new Posiçao(posiçao.getLinha(), posiçao.getColuna() + 3);
			if (testRoqueCastling(posT1)) {
				Posiçao p1 = new Posiçao(posiçao.getLinha(), posiçao.getColuna() + 1);
				Posiçao p2 = new Posiçao(posiçao.getLinha(), posiçao.getColuna() + 2);
				if (getTaboleiro().peça(p1) == null && getTaboleiro().peça(p2) == null) {
					mat[posiçao.getLinha()][posiçao.getColuna() + 2] = true;
				}
			}
			// #specialmove castling queenside rook
			Posiçao posT2 = new Posiçao(posiçao.getLinha(), posiçao.getColuna() - 4);
			if (testRoqueCastling(posT2)) {
				Posiçao p1 = new Posiçao(posiçao.getLinha(), posiçao.getColuna() - 1);
				Posiçao p2 = new Posiçao(posiçao.getLinha(), posiçao.getColuna() - 2);
				Posiçao p3 = new Posiçao(posiçao.getLinha(), posiçao.getColuna() - 3);
				if (getTaboleiro().peça(p1) == null && getTaboleiro().peça(p2) == null && getTaboleiro().peça(p3) == null) {
					mat[posiçao.getLinha()][posiçao.getColuna() - 2] = true;
				}
			}
		}
		
		return mat;
	}		

}
