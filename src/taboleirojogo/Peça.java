package taboleirojogo;

public abstract class Peça {
	
	protected Posiçao posiçao;
	private Taboleiro taboleiro;
	
	public Peça(Taboleiro taboleiro) {
		this.taboleiro = taboleiro;
		posiçao = null;
	}

	protected Taboleiro getTaboleiro() {
		return taboleiro;
	}
	
	public abstract boolean[][] movimentoPossiveis();
	
	public boolean movimentoPossivel(Posiçao posiçao) {
		return movimentoPossiveis()[posiçao.getLinha()][posiçao.getColuna()];
	}
	
	public boolean aquiUmMovimentoPosivel() {
		boolean[][] mat = movimentoPossiveis();
		for (int i=0; i<mat.length; i++) {
			for(int j=0; j<mat.length; j++) {
				if (mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}	
	
}
