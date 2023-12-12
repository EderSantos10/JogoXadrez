package xadrez;

import taboleirojogo.Posiçao;
import taboleirojogo.Taboleiro;
import xadrez.peça.Rei;
import xadrez.peça.Torre;
import taboleirojogo.Peça;

public class PartidaXadrez {
	
	private Taboleiro taboleiro;
	
	public PartidaXadrez() {
		taboleiro = new Taboleiro(8, 8);
		setupInicial();
	}
	
	public PeçaXadrez[][] getPeças(){
		PeçaXadrez[][] mat = new PeçaXadrez[taboleiro.getLinhas()][taboleiro.getColunas()];
		for (int i=0; i<taboleiro.getLinhas(); i++) {
			for (int j=0; j<taboleiro.getColunas(); j++) {
				mat[i][j] = (PeçaXadrez) taboleiro.peça(i, j);
			}
		}
		return mat;
	}
		
	public PeçaXadrez performaceMovimentoXadrez(PosiçaoXadrez inicialPosiçao, PosiçaoXadrez destinoPosiçao) {
		Posiçao inicial = inicialPosiçao.aPosiçao();
		Posiçao destino = destinoPosiçao.aPosiçao();
		validaçaoInicialPosiçao(inicial);
		validaçaoPosiçaoDestino(inicial, destino);
		Peça capturadaPeça = fazerMovimento(inicial, destino);
		return (PeçaXadrez)capturadaPeça;
	}
	
	private Peça fazerMovimento(Posiçao inicial, Posiçao destino) {
		Peça p = taboleiro.removePeça(inicial);
		Peça capturadaPeça = taboleiro.removePeça(destino);
		taboleiro.lugarPeça(p, destino);
		return capturadaPeça;
	}
	
	private void validaçaoInicialPosiçao(Posiçao posiçao) {
		if(!taboleiro.haUmaPeça(posiçao)) {
		throw new ExceçaoXadrez("Nao existe peça na posiçao inicial");
		}
		if(!taboleiro.peça(posiçao).aquiUmMovimentoPosivel()) {
			throw new ExceçaoXadrez("Nao existe movimentos possiveis para esta peça");
		}
	}	
	
	private void validaçaoPosiçaoDestino(Posiçao inicial, Posiçao destino) {
		if (!taboleiro.peça(inicial).movimentoPossivel(destino)) {
			throw new ExceçaoXadrez("A peça escolhida nao pode mover para o destino escolhido");
		}
	}

	private void lugarNovaPeça(char coluna, int linha, PeçaXadrez peça) {
		taboleiro.lugarPeça(peça, new PosiçaoXadrez(coluna, linha).aPosiçao());
		
	}

	private void setupInicial() {
		lugarNovaPeça('c' , 1, new Torre(taboleiro, Cor.BRANCO));
		lugarNovaPeça('c' , 2, new Torre(taboleiro, Cor.BRANCO));
		lugarNovaPeça('d' , 2, new Torre(taboleiro, Cor.BRANCO));
		lugarNovaPeça('e' , 2, new Torre(taboleiro, Cor.BRANCO));
		lugarNovaPeça('e' , 1, new Torre(taboleiro, Cor.BRANCO));
		lugarNovaPeça('d', 1, new Rei(taboleiro, Cor.BRANCO));
		
		lugarNovaPeça('c' , 7, new Torre(taboleiro, Cor.PRETO));
		lugarNovaPeça('c' , 8, new Torre(taboleiro, Cor.PRETO));
		lugarNovaPeça('d' , 7, new Torre(taboleiro, Cor.PRETO));
		lugarNovaPeça('e' , 7, new Torre(taboleiro, Cor.PRETO));
		lugarNovaPeça('e' , 8, new Torre(taboleiro, Cor.PRETO));
		lugarNovaPeça('d', 8, new Rei(taboleiro, Cor.PRETO));
		
	}
	
}
