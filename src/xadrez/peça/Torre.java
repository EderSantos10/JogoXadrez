package xadrez.peça;

import taboleirojogo.Taboleiro;
import xadrez.Cor;
import xadrez.PeçaXadrez;

public class Torre extends PeçaXadrez {

	public Torre(Taboleiro taboleiro, Cor cor) {
		super(taboleiro, cor);
		
	}
	
	@Override
	public String toString() {
		return "T";
	}

}
