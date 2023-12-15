package xadrez.peça;

import taboleirojogo.Posiçao;
import taboleirojogo.Taboleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PeçaXadrez;

public class Peao extends PeçaXadrez{
	
	private PartidaXadrez partidaXadrez;
	
	public Peao(Taboleiro taboleiro, Cor cor, PartidaXadrez partidaXadrez) {
		super(taboleiro, cor);
		this.partidaXadrez = partidaXadrez;
	}
	
	@Override
	public String toString(){
		return "P";
	}

	@Override
	public boolean[][] movimentoPossiveis() {
		boolean [][] mat = new boolean[getTaboleiro().getLinhas()][getTaboleiro().getColunas()];
		
		Posiçao p = new Posiçao(0, 0);
		
		if (getCor() == Cor.BRANCO) {
			p.setValues(posiçao.getLinha() - 1, posiçao.getColuna());
			if (getTaboleiro().posiçaoExistente(p) && !getTaboleiro().haUmaPeça(p)) {
				mat [p.getLinha()][p.getColuna()] = true;
			}
			p.setValues(posiçao.getLinha() - 2, posiçao.getColuna());
			Posiçao p2 = new Posiçao(posiçao.getLinha()-1, posiçao.getColuna());
			if (getTaboleiro().posiçaoExistente(p) && !getTaboleiro().haUmaPeça(p) && getTaboleiro().posiçaoExistente(p2) && !getTaboleiro().haUmaPeça(p2) && getContadorMovimento() == 0) {
				mat [p.getLinha()][p.getColuna()] = true;
			}
			p.setValues(posiçao.getLinha() - 1, posiçao.getColuna() - 1);
			if (getTaboleiro().posiçaoExistente(p) && eUmaPeçaOponente(p)) {
				mat [p.getLinha()][p.getColuna()] = true;
			}
			p.setValues(posiçao.getLinha() - 1, posiçao.getColuna() + 1);
			if (getTaboleiro().posiçaoExistente(p) && eUmaPeçaOponente(p)) {
				mat [p.getLinha()][p.getColuna()] = true;
			}
			
			// #specialmove en passant white
			if (posiçao.getLinha() == 3) {
				Posiçao left = new Posiçao(posiçao.getLinha(), posiçao.getColuna() - 1);
				if (getTaboleiro().posiçaoExistente(left) && eUmaPeçaOponente(left) && getTaboleiro().peça(left) == partidaXadrez.getEnPassantVuneravel()) {
					mat[left.getLinha() - 1][left.getColuna()] = true;
				}
				Posiçao right = new Posiçao(posiçao.getLinha(), posiçao.getColuna() + 1);
				if (getTaboleiro().posiçaoExistente(right) && eUmaPeçaOponente(right) && getTaboleiro().peça(right) == partidaXadrez.getEnPassantVuneravel()) {
					mat[right.getLinha() - 1][right.getColuna()] = true;
				}
			}
		}
		else {
			p.setValues(posiçao.getLinha() + 1, posiçao.getColuna());
			if (getTaboleiro().posiçaoExistente(p) && !getTaboleiro().haUmaPeça(p)) {
				mat [p.getLinha()][p.getColuna()] = true;
			}
			p.setValues(posiçao.getLinha() + 2, posiçao.getColuna());
			Posiçao p2 = new Posiçao(posiçao.getLinha() + 1, posiçao.getColuna());
			if (getTaboleiro().posiçaoExistente(p) && !getTaboleiro().haUmaPeça(p) && getTaboleiro().posiçaoExistente(p2) && !getTaboleiro().haUmaPeça(p2) && getContadorMovimento() == 0) {
				mat [p.getLinha()][p.getColuna()] = true;
			}
			p.setValues(posiçao.getLinha() + 1, posiçao.getColuna() - 1);
			if (getTaboleiro().posiçaoExistente(p) && eUmaPeçaOponente(p)) {
				mat [p.getLinha()][p.getColuna()] = true;
			}
			p.setValues(posiçao.getLinha() + 1, posiçao.getColuna() + 1);
			if (getTaboleiro().posiçaoExistente(p) && eUmaPeçaOponente(p)) {
				mat [p.getLinha()][p.getColuna()] = true;
			}
			// #specialmove en passant black
			if (posiçao.getLinha() == 4) {
				Posiçao left = new Posiçao(posiçao.getLinha(), posiçao.getColuna() - 1);
				if (getTaboleiro().posiçaoExistente(left) && eUmaPeçaOponente(left) && getTaboleiro().peça(left) == partidaXadrez.getEnPassantVuneravel()) {
					mat[left.getLinha() + 1][left.getColuna()] = true;
				}
				Posiçao right = new Posiçao(posiçao.getLinha(), posiçao.getColuna() + 1);
				if (getTaboleiro().posiçaoExistente(right) && eUmaPeçaOponente(right) && getTaboleiro().peça(right) == partidaXadrez.getEnPassantVuneravel()) {
					mat[right.getLinha() + 1][right.getColuna()] = true;
				}
			}			
		}
		
		return mat;
	}

}
