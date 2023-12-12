package xadrez.peça;

import taboleirojogo.Posiçao;
import taboleirojogo.Taboleiro;
import xadrez.Cor;
import xadrez.PeçaXadrez;

public class Rei extends PeçaXadrez{

	public Rei(Taboleiro taboleiro, Cor cor) {
		super(taboleiro, cor);
		
	}
	
	@Override
	public String toString() {
		return "R";
	}
	
	private boolean podeMover(Posiçao posiçao) {
		PeçaXadrez p = (PeçaXadrez) getTaboleiro().peça(posiçao);
		return p == null || p.getCor() != getCor();
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
		
		return mat;
	}		

}
