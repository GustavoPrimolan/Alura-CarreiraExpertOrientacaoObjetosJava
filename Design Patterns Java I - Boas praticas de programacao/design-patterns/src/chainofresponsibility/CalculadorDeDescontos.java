package chainofresponsibility;

public class CalculadorDeDescontos {
	
	public double calcula(Orcamento orcamento) {
		
		
		Desconto d1 = new DescontoPorCincoItens();
		Desconto d2 = new DescontoPorMaisDeQuinhentosReais();
		Desconto d3 = new SemDesconto();
		
		d1.setProximo(d2);
		d2.setProximo(d3);
		
		return d1.desconta(orcamento);
		
		
		/*
		double desconto = new DescontoPorCincoItens().desconto(orcamento);
		if(desconto == 0) desconto = new DescontoPorMaisDeQuinhentosReais().desconta(orcamento);
		*/
		
		//mais de 5 itnes, desconto
		/*if(orcamento.getItens().size() > 5) {
			return orcamento.getValor() * 0.1;
		}
		
		//segunda regra...
		else if(orcamento.getValor() > 500.0) {
			return orcamento.getValor() * 0.7;
		}
		*/
		//em caso contrario
		//eturn 0;
	}
	
}
