package controller;

import java.util.concurrent.Semaphore;

public class CavaleirosController extends Thread {
	int caVelocidade = 0;
	static boolean tocha = true;
	static boolean pedra = true;
	boolean ptocha = false;
	boolean ppedra = false;
	int intervalo = 50;
	int espaco = 0;
	int corredor = 2000;
	int cavaleiro;
	private Semaphore pegaraPedra;
	private Semaphore pegaraTocha;
	private Semaphore passarnaPorta;
	String[] portas = new String[4];
	static int seqportas = 0;
	
	
	public CavaleirosController (int cavaleiro, Semaphore pegaraTocha, Semaphore pegaraPedra, Semaphore passarnaPorta) {
		this.cavaleiro = cavaleiro;
		this.pegaraPedra = pegaraPedra;
		this.pegaraTocha = pegaraTocha;
		this.passarnaPorta = passarnaPorta;
	}
	
	@Override
	public void run() {
	
		corrida();
		
	}
	
	private void corrida() {
		
		System.out.println("Cavaleiro #" + cavaleiro + " começou a corrida!");
		for (int i = 0; i <= corredor; i = i + caVelocidade) {
			try {
				sleep(50);
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
			}
			if (espaco > 500 && tocha == true) {
				try {
					pegaraTocha.acquire();
					ptocha(ptocha);
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				} finally {
					pegaraTocha.release();
				}
				
			}
			if (espaco > 1500 && pedra == true) {
				try {
					pegaraPedra.acquire();
					ppedra(ppedra);
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				} finally {
					pegaraPedra.release();
				}
				
			}
			
			if (ptocha) {caVelocidade = (int) ((Math.random() *3)+4);} else	
			if (ppedra) {caVelocidade = (int) ((Math.random() *3)+4);}
			else {caVelocidade = (int) ((Math.random() *3)+2);}
				
			espaco = espaco + caVelocidade;
			//System.out.println("Cavaleiro #" + cavaleiro + " percorreu " + espaco + " metros!");
			
		}
		System.out.println("Cavaleiro #" + cavaleiro + " chegou ao fim do corredor e...");
		//for (int i = 0; i < portas.length; i++) {
		//	portas[i] = "O Cavaleiro #" + cavaleiro + " entrou pela porta e foi devorado por um monstro!";
		//}
		portas[0] = "O Cavaleiro #" + cavaleiro + " entrou pela porta e tomou uma flechada no joelho. Ele se aposentou das suas aventuras...";
		portas[1] = "O Cavaleiro #" + cavaleiro + " entrou pela porta e comeu o cu do curioso. Esse foi o tesouro dele, eu acho.";
		portas[2] = "O Cavaleiro #" + cavaleiro + " entrou pela porta e foi devorad... Por um... Pelos Deuses! Arrumem um quarto! O Cavaleiro cogita se deve se juntar ou ir embora.";
		portas[3] = "O Cavaleiro #" + cavaleiro + " escorregou bem na soleira da porta e caiu de cara numa armadilha. É. Quem diria que corredores úmidos acumulariam tanto limo né?";
		portacerta(portas);
		
		try {
			passarnaPorta.acquire();
			sleep(1000);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		} finally {
			System.out.println(portas[seqportas]);
			seqportas++;
			passarnaPorta.release();
			
		}
		
	}
	
	private boolean ptocha (boolean ptocha) {
		if (tocha == true) {
			ptocha = true;
			tocha = false;
			System.out.println("Cavaleiro #" + cavaleiro + " pegou a tocha!");
		}
		return ptocha;
	}
	
	private boolean ppedra (boolean ppedra) {
		if (pedra == true && ptocha == false) {
			System.out.println("Cavaleiro #" + cavaleiro + " pegou a pedra brilhante!");
			ppedra = true;
			pedra = false;
		}
		return ppedra;
	}
	
	private String[] portacerta(String[] portas) {
		int portacerta = (int)(Math.random()*4);
		portas[portacerta] = "O cavaleiro #" + cavaleiro + " achou a porta certa que leva ao tesouro!";
		return portas;
	}
	
}
