package templatemethod;

import java.util.Collections;
import java.util.List;

import chainofresponsibility.Item;

public class Orcamento {
	
	private double valor;

	public Orcamento(double valor) {
		this.valor = valor;
		
	}

	public double getValor() {
		return valor;
	}

	public List<Item> getItens() {
		//LISTA N�O PODE SER MODIFICADA PELAS CLASSES DE FORA
		return Collections.unmodifiableList(itens);
	}
	
}
