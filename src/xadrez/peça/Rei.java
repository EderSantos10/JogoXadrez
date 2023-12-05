package xadrez.peça;

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

}
