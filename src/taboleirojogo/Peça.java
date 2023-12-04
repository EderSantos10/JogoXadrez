package taboleirojogo;

public class Peça {
	
	protected Posiçao posiçao;
	private Taboleiro taboleiro;
	
	public Peça(Taboleiro taboleiro) {
		this.taboleiro = taboleiro;
	}

	protected Taboleiro getTaboleiro() {
		return taboleiro;
	}
	


}
