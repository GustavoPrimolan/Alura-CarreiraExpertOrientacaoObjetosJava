package templatemethod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import templatemethod.Item; 

public class Orcamento {
	
	private final double valor;
	private final List<Item> itens;

	public Orcamento(double valor) {
		this.valor = valor;
		itens = new ArrayList<>();
	}

	public double getValor() {
		return valor;
	}
	
	
	public void adicionaItem(Item item) {
		itens.add(item);
	}
	
	public List<Item> getItens() {
		//LISTA N�O PODE SER MODIFICADA PELAS CLASSES DE FORA
		return Collections.unmodifiableList(itens);
	}
	
	
	
}
