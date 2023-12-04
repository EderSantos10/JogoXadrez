package xadrez;

import taboleirojogo.Peça;
import taboleirojogo.Taboleiro;

public class PeçaXadrez extends Peça{
	
	private Cor cor;

	public PeçaXadrez(Taboleiro taboleiro, Cor cor) {
		super(taboleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	

}
