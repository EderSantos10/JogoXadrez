package xadrez.peça;

import taboleirojogo.Posiçao;
import taboleirojogo.Taboleiro;
import xadrez.Cor;
import xadrez.PeçaXadrez;

public class Peao extends PeçaXadrez{

	public Peao(Taboleiro taboleiro, Cor cor) {
		super(taboleiro, cor);
		
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
			
		}
		
		return mat;
	}

}
