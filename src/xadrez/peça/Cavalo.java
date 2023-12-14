package xadrez.peça;

import taboleirojogo.Posiçao;
import taboleirojogo.Taboleiro;
import xadrez.Cor;
import xadrez.PeçaXadrez;

public class Cavalo extends PeçaXadrez {

	public Cavalo(Taboleiro taboleiro, Cor cor) {
		super(taboleiro, cor);
		
	}

	public String toString() {
		return "C";
	}
	
	private boolean podeMover(Posiçao posiçao) {
		PeçaXadrez p = (PeçaXadrez) getTaboleiro().peça(posiçao);
		return p == null || p.getCor() != getCor();
	}

	@Override
	public boolean[][] movimentoPossiveis() {
		boolean [][] mat = new boolean[getTaboleiro().getLinhas()][getTaboleiro().getColunas()];
		
		Posiçao p = new Posiçao(0, 0);
		
		p.setValues(posiçao.getLinha() - 1, posiçao.getColuna() + 2);
		if (getTaboleiro().posiçaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		p.setValues(posiçao.getLinha() - 1, posiçao.getColuna() - 2);
		if (getTaboleiro().posiçaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			}
		
		p.setValues(posiçao.getLinha() + 1, posiçao.getColuna() + 2);
		if (getTaboleiro().posiçaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			}
		
		p.setValues(posiçao.getLinha() + 1, posiçao.getColuna() - 2);
		if (getTaboleiro().posiçaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			}
		
		p.setValues(posiçao.getLinha() - 2, posiçao.getColuna() - 1);
		if (getTaboleiro().posiçaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			}
		
		p.setValues(posiçao.getLinha() - 2, posiçao.getColuna() + 1);
		if (getTaboleiro().posiçaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			}
		
		p.setValues(posiçao.getLinha() + 2, posiçao.getColuna() - 1);
		if (getTaboleiro().posiçaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			}
		
		p.setValues(posiçao.getLinha() + 2, posiçao.getColuna() + 1);
		if (getTaboleiro().posiçaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			}
		return mat;
	}		

}
