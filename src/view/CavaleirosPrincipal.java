package view;

import java.util.concurrent.Semaphore;

import controller.CavaleirosController;

public class CavaleirosPrincipal {
	public static void main(String[] args) {
		
		Semaphore pegarTocha = new Semaphore(1);
		Semaphore pegarPedra = new Semaphore(1);
		Semaphore passarnaPorta = new Semaphore(1);
		
		for (int i = 0; i < 4; i++) {
			CavaleirosController cavaleiro = new CavaleirosController(i+1, pegarTocha, pegarPedra, passarnaPorta);
			cavaleiro.start();
		}
		
	}
}
