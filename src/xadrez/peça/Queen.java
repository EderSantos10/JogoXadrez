package xadrez.peça;

import taboleirojogo.Posiçao;
import taboleirojogo.Taboleiro;
import xadrez.Cor;
import xadrez.PeçaXadrez;

public class Queen extends PeçaXadrez {

	public Queen(Taboleiro taboleiro, Cor cor) {
		super(taboleiro, cor);
		
	}
	
	@Override
	public String toString() {
		return "Q";
	}

	@Override
	public boolean[][] movimentoPossiveis() {
		boolean [][] mat = new boolean[getTaboleiro().getLinhas()][getTaboleiro().getColunas()];
		
		Posiçao p = new Posiçao(0, 0);
		
		// cima
		p.setValues(posiçao.getLinha() -1, posiçao.getColuna());
		while (getTaboleiro().posiçaoExistente(p) && !getTaboleiro().haUmaPeça(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha()-1);
		}
		if (getTaboleiro().posiçaoExistente(p) && eUmaPeçaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// esquerda
			p.setValues(posiçao.getLinha(), posiçao.getColuna() - 1);
			while (getTaboleiro().posiçaoExistente(p) && !getTaboleiro().haUmaPeça(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
				p.setColuna(p.getColuna() - 1);
				}
			if (getTaboleiro().posiçaoExistente(p) && eUmaPeçaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
				}
				
			// direita
			p.setValues(posiçao.getLinha(), posiçao.getColuna() + 1);
			while (getTaboleiro().posiçaoExistente(p) && !getTaboleiro().haUmaPeça(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
				p.setColuna(p.getColuna() + 1);
				}
			if (getTaboleiro().posiçaoExistente(p) && eUmaPeçaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
				}
			
			// baixo
			p.setValues(posiçao.getLinha() + 1, posiçao.getColuna());
			while (getTaboleiro().posiçaoExistente(p) && !getTaboleiro().haUmaPeça(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
				p.setLinha(p.getLinha() + 1);
			}
			if (getTaboleiro().posiçaoExistente(p) && eUmaPeçaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			// diagonal superior direita
			p.setValues(posiçao.getLinha() - 1, posiçao.getColuna() + 1);
			while (getTaboleiro().posiçaoExistente(p) && !getTaboleiro().haUmaPeça(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
				p.setValues(p.getLinha() - 1, p.getColuna() + 1);
			}
			if (getTaboleiro().posiçaoExistente(p) && eUmaPeçaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			// diagonal superior esquerda
				p.setValues(posiçao.getLinha() - 1, posiçao.getColuna() - 1);
				while (getTaboleiro().posiçaoExistente(p) && !getTaboleiro().haUmaPeça(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					p.setValues(p.getLinha() - 1, p.getColuna() - 1);
					}
				if (getTaboleiro().posiçaoExistente(p) && eUmaPeçaOponente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					}
					
				// diagonal inferior direita
				p.setValues(posiçao.getLinha() + 1, posiçao.getColuna() + 1);
				while (getTaboleiro().posiçaoExistente(p) && !getTaboleiro().haUmaPeça(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					p.setValues(p.getLinha() + 1, p.getColuna() + 1);
					}
				if (getTaboleiro().posiçaoExistente(p) && eUmaPeçaOponente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					}
				
				// diagonal inferior esquerda
				p.setValues(posiçao.getLinha() + 1, posiçao.getColuna() - 1);
				while (getTaboleiro().posiçaoExistente(p) && !getTaboleiro().haUmaPeça(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					p.setValues(p.getLinha() + 1, p.getColuna() - 1);
				}
				if (getTaboleiro().posiçaoExistente(p) && eUmaPeçaOponente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
		
		return mat;
	}
		
}
