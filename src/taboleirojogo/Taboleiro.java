package taboleirojogo;

public class Taboleiro {
	
	private int linhas;
	private int colunas;
	private Peça[][] peças;
	
	public Taboleiro(int linhas, int colunas) {
		if (linhas < 1 || colunas < 1) {
			throw new ExceçaoTabuleiro("Erro ao criar Tabuleiro: Linhas ou Colunas menor que 1");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		peças = new Peça[linhas][colunas];
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}

	public Peça peça(int linha, int coluna) {
		if(!posiçaoExistente(linha, coluna)) {
			throw new ExceçaoTabuleiro("Essa posiçao nao esta no Tabuleiro");
		}	
		return peças[linha][coluna];
	}
	
	public Peça peça(Posiçao posiçao) {
		if(!posiçaoExistente(posiçao)) {
			throw new ExceçaoTabuleiro("Essa posiçao nao esta no Tabuleiro");
		}
		return peças[posiçao.getLinha()][posiçao.getColuna()];
	}
	
	public void lugarPeça(Peça peça, Posiçao posiçao) {
		if (haUmaPeça(posiçao)) {
			throw new ExceçaoTabuleiro("Ja existe uma peça nesta posiçao" + posiçao);
		}
		peças[posiçao.getLinha()][posiçao.getColuna()] = peça;
		peça.posiçao = posiçao;
	}
	
	public Peça removePeça(Posiçao posiçao) {
		if (!posiçaoExistente(posiçao)) {
			throw new ExceçaoTabuleiro("Essa posiçao nao esta no Tabuleiro");
		}
		if (peça(posiçao) == null) {
			return null;
		}
		Peça aux = peça(posiçao);
		aux.posiçao = null;
		peças[posiçao.getLinha()][posiçao.getColuna()] = null;
		return aux;
	}
	
	private boolean posiçaoExistente(int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >=0 && coluna < colunas;
	}
	
	public boolean posiçaoExistente(Posiçao posiçao) {
		return posiçaoExistente(posiçao.getLinha(), posiçao.getColuna());
	}
	
	public boolean haUmaPeça(Posiçao posiçao) {
		if(!posiçaoExistente(posiçao)) {
			throw new ExceçaoTabuleiro("Essa posiçao nao esta no Tabuleiro");
		}
		return peça(posiçao) != null;
	}

}
