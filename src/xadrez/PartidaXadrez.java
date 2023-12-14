package xadrez;

import taboleirojogo.Posiçao;
import taboleirojogo.Taboleiro;
import xadrez.peça.Bispo;
import xadrez.peça.Cavalo;
import xadrez.peça.Peao;
import xadrez.peça.Queen;
import xadrez.peça.Rei;
import xadrez.peça.Torre;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import taboleirojogo.Peça;

public class PartidaXadrez {
	
	private int turno;
	private Cor jogadorAtual;
	private Taboleiro taboleiro;
	private boolean check;
	private boolean checkMate;
	
	private List<Peça> peçasNoTaboleiro = new ArrayList<>();
	private List<Peça> peçasCapturadas = new ArrayList<>();
	
	public PartidaXadrez() {
		taboleiro = new Taboleiro(8, 8);
		turno = 1;
		jogadorAtual = Cor.BRANCO;
		setupInicial();
	}
	
	public int getTurno() {
		return turno;
	}
	
	public Cor getJogadorAtual() {
		return jogadorAtual;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
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
	
	public boolean[][] movimentosPossiveis(PosiçaoXadrez posiçaoInicial){
		Posiçao posiçao = posiçaoInicial.aPosiçao();
		validaçaoInicialPosiçao(posiçao);
		return taboleiro.peça(posiçao).movimentoPossiveis();
	}
		
	public PeçaXadrez performaceMovimentoXadrez(PosiçaoXadrez inicialPosiçao, PosiçaoXadrez destinoPosiçao) {
		Posiçao inicial = inicialPosiçao.aPosiçao();
		Posiçao destino = destinoPosiçao.aPosiçao();
		validaçaoInicialPosiçao(inicial);
		validaçaoPosiçaoDestino(inicial, destino);
		Peça capturadaPeça = fazerMovimento(inicial, destino);
		
		if (testCheck(jogadorAtual)) {
			desfazerMovimento(inicial, destino, capturadaPeça);
			throw new ExceçaoXadrez("Voce nao pode se por em CHECK");
		}
		
		check =(testCheck(oponente(jogadorAtual))) ? true : false;
		
		if(testCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		}
		else {
			novoTurno();
		}
		
		return (PeçaXadrez)capturadaPeça;
	}
	
	private Peça fazerMovimento(Posiçao inicial, Posiçao destino) {
		PeçaXadrez p = (PeçaXadrez)taboleiro.removePeça(inicial);
		p.acrescentaMovimentoContador();
		Peça capturadaPeça = taboleiro.removePeça(destino);
		taboleiro.lugarPeça(p, destino);
		
		if (capturadaPeça != null) {
			peçasNoTaboleiro.remove(capturadaPeça);
			peçasCapturadas.add(capturadaPeça);
		}
		
		return capturadaPeça;
	}
	
	private void desfazerMovimento(Posiçao inicial, Posiçao destino, Peça capturadaPeça) {
		PeçaXadrez p = (PeçaXadrez)taboleiro.removePeça(destino);
		p.decrescentaMovimentoContador();
		taboleiro.lugarPeça(p, inicial);
		
		if (capturadaPeça != null) {
			taboleiro.lugarPeça(capturadaPeça, destino);
			peçasCapturadas.remove(capturadaPeça);
			peçasNoTaboleiro.add(capturadaPeça);
		}
	}
	
	private void validaçaoInicialPosiçao(Posiçao posiçao) {
		if(!taboleiro.haUmaPeça(posiçao)) {
		throw new ExceçaoXadrez("Nao existe peca na posiçao inicial");
		}
		if(jogadorAtual != ((PeçaXadrez)taboleiro.peça(posiçao)).getCor()) {
			throw new ExceçaoXadrez("Essa peca nao e sua");
		}
		if(!taboleiro.peça(posiçao).aquiUmMovimentoPosivel()) {
			throw new ExceçaoXadrez("Nao existe movimentos possiveis para esta peca");
		}
	}	
	
	private void validaçaoPosiçaoDestino(Posiçao inicial, Posiçao destino) {
		if (!taboleiro.peça(inicial).movimentoPossivel(destino)) {
			throw new ExceçaoXadrez("A peca escolhida nao pode mover para o destino escolhido");
		}
	}
	
	private void novoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}
	
	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}
	
	private PeçaXadrez rei(Cor cor) {
		List<Peça> list = peçasNoTaboleiro.stream().filter(x -> ((PeçaXadrez)x).getCor() == cor).collect(Collectors.toList());
		for (Peça p : list) {
			if (p instanceof Rei) {
				return (PeçaXadrez)p;
			}
		}
		throw new IllegalStateException("Nao existe o Rei" + cor + "no tabuleiro");
	}
	
	private boolean testCheck(Cor cor) {
		Posiçao posiçaoRei = rei(cor).getPosiçaoXadrez().aPosiçao();
		List<Peça> peçasOponentes = peçasNoTaboleiro.stream().filter(x -> ((PeçaXadrez)x).getCor() == oponente(cor)).collect(Collectors.toList());
		for (Peça p : peçasOponentes) {
			boolean[][] mat = p.movimentoPossiveis();
			if (mat[posiçaoRei.getLinha()][posiçaoRei.getColuna()]) {
				return true;				
			}
		}
		return false;
	}
	
	private boolean testCheckMate(Cor cor) {
		if (!testCheck(cor)) {
			return false;
		}
		List<Peça> list = peçasNoTaboleiro.stream().filter(x -> ((PeçaXadrez)x).getCor() == cor).collect(Collectors.toList());
		for (Peça p : list) {
			boolean[][] mat = p.movimentoPossiveis();
			for (int i=0; i < taboleiro.getLinhas(); i++) {
				for (int j = 0; j < taboleiro.getColunas(); j++) {
					if (mat[i][j]) {
						Posiçao inicial = ((PeçaXadrez)p).getPosiçaoXadrez().aPosiçao();
						Posiçao destino = new Posiçao (i, j);
						Peça capturadaPeça = fazerMovimento(inicial, destino);
						boolean testCheck = testCheck(cor);
						desfazerMovimento(inicial, destino, capturadaPeça);
						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void lugarNovaPeça(char coluna, int linha, PeçaXadrez peça) {
		taboleiro.lugarPeça(peça, new PosiçaoXadrez(coluna, linha).aPosiçao());
		peçasNoTaboleiro.add(peça);
		
	}

	private void setupInicial() {
		lugarNovaPeça('h' , 1, new Torre(taboleiro, Cor.BRANCO));
		lugarNovaPeça('a' , 1, new Torre(taboleiro, Cor.BRANCO));
		lugarNovaPeça('b' , 1, new Cavalo(taboleiro, Cor.BRANCO));
		lugarNovaPeça('g' , 1, new Cavalo(taboleiro, Cor.BRANCO));
		lugarNovaPeça('c' , 1, new Bispo(taboleiro, Cor.BRANCO));
		lugarNovaPeça('f' , 1, new Bispo(taboleiro, Cor.BRANCO));
		lugarNovaPeça('d', 1, new Queen(taboleiro, Cor.BRANCO));
		lugarNovaPeça('e', 1, new Rei(taboleiro, Cor.BRANCO));
		lugarNovaPeça('a', 2, new Peao(taboleiro, Cor.BRANCO));
		lugarNovaPeça('b', 2, new Peao(taboleiro, Cor.BRANCO));
		lugarNovaPeça('c', 2, new Peao(taboleiro, Cor.BRANCO));
		lugarNovaPeça('d', 2, new Peao(taboleiro, Cor.BRANCO));
		lugarNovaPeça('e', 2, new Peao(taboleiro, Cor.BRANCO));
		lugarNovaPeça('f', 2, new Peao(taboleiro, Cor.BRANCO));
		lugarNovaPeça('g', 2, new Peao(taboleiro, Cor.BRANCO));
		lugarNovaPeça('h', 2, new Peao(taboleiro, Cor.BRANCO));
		
		
		
		lugarNovaPeça('a' , 8, new Torre(taboleiro, Cor.PRETO));
		lugarNovaPeça('h' , 8, new Torre(taboleiro, Cor.PRETO));
		lugarNovaPeça('b' , 8, new Cavalo(taboleiro, Cor.PRETO));
		lugarNovaPeça('g' , 8, new Cavalo(taboleiro, Cor.PRETO));
		lugarNovaPeça('c' , 8, new Bispo(taboleiro, Cor.PRETO));
		lugarNovaPeça('f' , 8, new Bispo(taboleiro, Cor.PRETO));
		lugarNovaPeça('d', 8, new Queen(taboleiro, Cor.PRETO));
		lugarNovaPeça('e', 8, new Rei(taboleiro, Cor.PRETO));
		lugarNovaPeça('a', 7, new Peao(taboleiro, Cor.PRETO));
		lugarNovaPeça('b', 7, new Peao(taboleiro, Cor.PRETO));
		lugarNovaPeça('c', 7, new Peao(taboleiro, Cor.PRETO));
		lugarNovaPeça('d', 7, new Peao(taboleiro, Cor.PRETO));
		lugarNovaPeça('e', 7, new Peao(taboleiro, Cor.PRETO));
		lugarNovaPeça('f', 7, new Peao(taboleiro, Cor.PRETO));
		lugarNovaPeça('g', 7, new Peao(taboleiro, Cor.PRETO));
		lugarNovaPeça('h', 7, new Peao(taboleiro, Cor.PRETO));
		
	}
	
}
