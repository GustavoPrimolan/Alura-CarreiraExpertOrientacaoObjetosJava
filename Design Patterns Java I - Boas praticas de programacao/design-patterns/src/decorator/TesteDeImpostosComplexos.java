package decorator;

import decorator.Imposto;

public class TesteDeImpostosComplexos {
	
	public static void main(String[] args) {
		//SERIA NECESSÁRIO CRIAR UMA CLASSE PARA CADA IMPOSTO
		//ISSO SERIA INVIÁVEL
//		Imposto iss = new ISS();
//		Imposto issComIcms = new ISSComICMS();
//		Imposto issComIcmsComIcpp = new ISSComICMSComICPP();
		
		//UM IMPOSTO PODE SER COMPOSTO POR OUTRO IMPOSTO
		
		Orcamento orcamento = new Orcamento(500);
		
		double valor = iss.calcula(orcamento);
		
		System.out.println(valor);
	}

}
