package xadrez;

import taboleirojogo.Peça;
import taboleirojogo.Posiçao;
import taboleirojogo.Taboleiro;

public abstract class PeçaXadrez extends Peça{
	
	private Cor cor;
	private int contadorMovimento;

	public PeçaXadrez(Taboleiro taboleiro, Cor cor) {
		super(taboleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}	
	
	public int getContadorMovimento() {
		return contadorMovimento;
	}
	
	public void acrescentaMovimentoContador() {
		contadorMovimento++;
	}
	
	public void decrescentaMovimentoContador() {
		contadorMovimento--;
	}
	
	public PosiçaoXadrez getPosiçaoXadrez() {
		return PosiçaoXadrez.paraPosiçao(posiçao);
	}
	
	protected boolean eUmaPeçaOponente(Posiçao posiçao) {
		PeçaXadrez p = (PeçaXadrez)getTaboleiro().peça(posiçao);
		return p != null && p.getCor() != cor;
	}

}
