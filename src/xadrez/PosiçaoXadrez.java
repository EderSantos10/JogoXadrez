package xadrez;

import taboleirojogo.Posiçao;

public class PosiçaoXadrez {
	
	private char coluna;
	private int linha;
	
	public PosiçaoXadrez(char coluna, int linha) {
		if(coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8) {
			throw new ExceçaoXadrez("Erro de instanciamento PosiçaoXadrez invalidada < a1 ou maior que h8");
		}
		this.coluna = coluna;
		this.linha = linha;
	}

	public char getColuna() {
		return coluna;
	}


	public int getLinha() {
		return linha;
	}

	protected Posiçao aPosiçao(){
		
	return new Posiçao (8 - linha, coluna - 'a');
	}

	protected static PosiçaoXadrez paraPosiçao(Posiçao posiçao) {
		return new PosiçaoXadrez ((char)('a' - posiçao.getColuna()), 8 - posiçao.getLinha());
	}
	
	@Override
	public String toString() {
		return "" + coluna + linha;
	}
	
}
