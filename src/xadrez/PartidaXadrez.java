package xadrez;

import taboleirojogo.Posiçao;
import taboleirojogo.Taboleiro;
import xadrez.peça.Bispo;
import xadrez.peça.Cavalo;
import xadrez.peça.Peao;
import xadrez.peça.Queen;
import xadrez.peça.Rei;
import xadrez.peça.Torre;

import java.security.InvalidParameterException;
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
	private PeçaXadrez enPassantVuneravel;
	private PeçaXadrez promoçao;
	
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
	
	public PeçaXadrez getEnPassantVuneravel() {
		return enPassantVuneravel;
	}
	
	public PeçaXadrez getPromoçao() {
		return promoçao;
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
	
	public boolean[][] movimentoPossiveis(PosiçaoXadrez posiçaoInicial){
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
		
		PeçaXadrez moveuPeça = (PeçaXadrez)taboleiro.peça(destino);
		
		// #special move Promoçao
		promoçao = null;
		if (moveuPeça instanceof Peao) {
			if ((moveuPeça.getCor() == Cor.BRANCO && destino.getLinha() == 0) || moveuPeça.getCor() == Cor.PRETO && destino.getLinha() == 7) {
				promoçao = (PeçaXadrez)taboleiro.peça(destino);
				promoçao = replacePromoçaoPeça("Q");
			}
		}
		
		check =(testCheck(oponente(jogadorAtual))) ? true : false;
		
		if(testCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		}
		else {
			novoTurno();
		}		
		//#specialmove en passant
		if(moveuPeça instanceof Peao && (destino.getLinha() == inicial.getLinha() - 2 || destino.getLinha() == inicial.getLinha() + 2)) {
			enPassantVuneravel = moveuPeça;
		}
		else {
			enPassantVuneravel = null;
		}
		
		return (PeçaXadrez)capturadaPeça;
	}
	
	public PeçaXadrez replacePromoçaoPeça(String type) {
		if (promoçao == null) {
			throw new IllegalStateException("Nao ha peca para ser promovida.");
		}
		if (!type.equals("B") && !type.equals("T") && !type.equals("C") && !type.equals("Q")) {
			throw new InvalidParameterException("A peca nao e valida para promocao.");
		}
		
		Posiçao pos = promoçao.getPosiçaoXadrez().aPosiçao();
		Peça p = taboleiro.removePeça(pos);
		peçasNoTaboleiro.remove(p);
		
		PeçaXadrez novaPeça = novaPeça(type, promoçao.getCor());
		taboleiro.lugarPeça(novaPeça, pos);
		peçasNoTaboleiro.add(novaPeça);
		
		return novaPeça;
	}
	
	private PeçaXadrez novaPeça(String type, Cor cor) {
		if (type.equals("B")) return new Bispo(taboleiro, cor);
		if (type.equals("T")) return new Torre(taboleiro, cor);
		if (type.equals("C")) return new Cavalo(taboleiro, cor);
		 return new Queen(taboleiro, cor);
		
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
		
		// #Movimento especial Roque PE
		if (p instanceof Rei && destino.getColuna() == inicial.getColuna() + 2) {
			Posiçao iniT = new Posiçao(inicial.getLinha(), inicial.getColuna() + 3);
			Posiçao desT = new Posiçao(inicial.getLinha(), inicial.getColuna() + 1);
			PeçaXadrez torre = (PeçaXadrez)taboleiro.removePeça(iniT);
			taboleiro.lugarPeça(torre, desT);
			torre.acrescentaMovimentoContador();
		}
		// #Movimento especial Roque Grand
				if (p instanceof Rei && destino.getColuna() == inicial.getColuna() - 2) {
					Posiçao iniT = new Posiçao(inicial.getLinha(), inicial.getColuna() - 4);
					Posiçao desT = new Posiçao(inicial.getLinha(), inicial.getColuna() - 1);
					PeçaXadrez torre = (PeçaXadrez)taboleiro.removePeça(iniT);
					taboleiro.lugarPeça(torre, desT);
					torre.acrescentaMovimentoContador();
				}
				
		// #specialmove En Passant
				if (p instanceof Peao) {
					if (inicial.getColuna() != destino.getColuna() && capturadaPeça == null) {
						Posiçao posiçaoPeao;
						if (p.getCor() == Cor.BRANCO) {
							posiçaoPeao = new Posiçao(destino.getLinha() + 1, destino.getColuna());
						}
						else {
							posiçaoPeao = new Posiçao(destino.getLinha() - 1, destino.getColuna());
						}
						capturadaPeça = taboleiro.removePeça(posiçaoPeao);
						peçasCapturadas.add(capturadaPeça);
						peçasNoTaboleiro.remove(capturadaPeça);
					}									
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
		
		// #Movimento especial Roque PE
				if (p instanceof Rei && destino.getColuna() == inicial.getColuna() + 2) {
					Posiçao iniT = new Posiçao(inicial.getLinha(), inicial.getColuna() + 3);
					Posiçao desT = new Posiçao(inicial.getLinha(), inicial.getColuna() + 1);
					PeçaXadrez torre = (PeçaXadrez)taboleiro.removePeça(desT);
					taboleiro.lugarPeça(torre, iniT);
					torre.decrescentaMovimentoContador();
				}
				// #Movimento especial Roque Grand
						if (p instanceof Rei && destino.getColuna() == inicial.getColuna() - 2) {
							Posiçao iniT = new Posiçao(inicial.getLinha(), inicial.getColuna() - 4);
							Posiçao desT = new Posiçao(inicial.getLinha(), inicial.getColuna() - 1);
							PeçaXadrez torre = (PeçaXadrez)taboleiro.removePeça(desT);
							taboleiro.lugarPeça(torre, iniT);
							torre.decrescentaMovimentoContador();
						}
						
						// #specialmove En Passant
						if (p instanceof Peao) {
							if (inicial.getColuna() != destino.getColuna() && capturadaPeça == enPassantVuneravel) {
								PeçaXadrez peao = (PeçaXadrez)taboleiro.removePeça(destino);
								Posiçao posiçaoPeao;
								if (p.getCor() == Cor.BRANCO) {
									posiçaoPeao = new Posiçao(3, destino.getColuna());
								}
								else {
									posiçaoPeao = new Posiçao(4, destino.getColuna());
								}
								
								taboleiro.lugarPeça(peao, posiçaoPeao);																
							}									
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
		lugarNovaPeça('e', 1, new Rei(taboleiro, Cor.BRANCO, this));
		lugarNovaPeça('a', 2, new Peao(taboleiro, Cor.BRANCO, this));
		lugarNovaPeça('b', 2, new Peao(taboleiro, Cor.BRANCO, this));
		lugarNovaPeça('c', 2, new Peao(taboleiro, Cor.BRANCO, this));
		lugarNovaPeça('d', 2, new Peao(taboleiro, Cor.BRANCO, this));
		lugarNovaPeça('e', 2, new Peao(taboleiro, Cor.BRANCO, this));
		lugarNovaPeça('f', 2, new Peao(taboleiro, Cor.BRANCO, this));
		lugarNovaPeça('g', 2, new Peao(taboleiro, Cor.BRANCO, this));
		lugarNovaPeça('h', 2, new Peao(taboleiro, Cor.BRANCO, this));
		
		
		
		lugarNovaPeça('a' , 8, new Torre(taboleiro, Cor.PRETO));
		lugarNovaPeça('h' , 8, new Torre(taboleiro, Cor.PRETO));
		lugarNovaPeça('b' , 8, new Cavalo(taboleiro, Cor.PRETO));
		lugarNovaPeça('g' , 8, new Cavalo(taboleiro, Cor.PRETO));
		lugarNovaPeça('c' , 8, new Bispo(taboleiro, Cor.PRETO));
		lugarNovaPeça('f' , 8, new Bispo(taboleiro, Cor.PRETO));
		lugarNovaPeça('d', 8, new Queen(taboleiro, Cor.PRETO));
		lugarNovaPeça('e', 8, new Rei(taboleiro, Cor.PRETO, this));
		lugarNovaPeça('a', 7, new Peao(taboleiro, Cor.PRETO, this));
		lugarNovaPeça('b', 7, new Peao(taboleiro, Cor.PRETO, this));
		lugarNovaPeça('c', 7, new Peao(taboleiro, Cor.PRETO, this));
		lugarNovaPeça('d', 7, new Peao(taboleiro, Cor.PRETO, this));
		lugarNovaPeça('e', 7, new Peao(taboleiro, Cor.PRETO, this));
		lugarNovaPeça('f', 7, new Peao(taboleiro, Cor.PRETO, this));
		lugarNovaPeça('g', 7, new Peao(taboleiro, Cor.PRETO, this));
		lugarNovaPeça('h', 7, new Peao(taboleiro, Cor.PRETO, this));
		
	}
	
}
