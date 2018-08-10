<h1>Se��o 01 - A grande variedade de impostos e o padr�o Strategy</h1>

<h2>Design Patterns</h2>
Classes e m�todos gigantes? Cinco minutos para entender o que aquele m�todo faz ou onde est� o c�digo que faz uma altera��o simples? Diversas vari�veis e diversos ifs e fors no mesmo m�todo? C�digo complexo e obscuro? Toda vez que uma altera��o aparece, voc� precisa mudar em 20 classes diferentes? Sim, problemas muito comuns do nosso dia-a-dia. Mas por que isso acontece?

Um fato que � conhecido sobre todo software � que ele, mais cedo ou mais tarde, vai mudar: novas funcionalidades aparecer�o, outras dever�o ser alteradas etc. O problema � que, geralmente, essas mudan�as n�o s�o feitas de forma muito planejada. Por esse motivo, durante o desenvolvimento de um projeto de software, � bem comum a cria��o de c�digo onde as responsabilidades se misturam e espalham por v�rias classes, fazendo com que a manuten��o do c�digo fique cada vez mais dif�cil, j� que uma simples mudan�a pode obrigar o desenvolvedor a alterar diversas classes. Nesse cen�rio, temos uma situa��o onde o 'Design' das classes n�o est� bom e � poss�vel melhor�-lo.

Para atingir o objetivo da melhora do 'Design' e diminuir o custo de manuten��o, existem algumas t�cnicas, onde podemos aplicar a orienta��o a objetos de uma maneira a simplificar o c�digo escrito.

Resolveremos diversos dos problemas que vemos em c�digos, como classes e m�todos gigantes que fazem muita coisa, classes que precisam conhecer mais 10 outras classes para fazer seu trabalho, m�todos complicados com muitos ifs e condi��es, mudan�as que se propagam por v�rias classes e assim por diante.

Algumas das principais t�cnicas para atingir um bom 'Design' s�o t�o comuns que foram catalogadas em um conjunto de alternativas para solucionar problemas de Design de c�digo, chamados de Design Patterns, mais conhecido em portugu�s como os Padr�es de Projetos, os quais, durante esse curso, aprenderemos a utilizar em um projeto. Um padr�o de projeto nada mais � do que uma solu��o elegante para um problema que � muito recorrente em nosso dia-a-dia.

Mais importante do que entender como � a implementa��o de um padr�o de projeto, � entender a motiva��o do padr�o: em quais casos ele faz sentido e deve ser aplicado. Durante os pr�ximos cap�tulos, observe o cen�rio do exemplo dado com aten��o e o leve para o seu mundo e para o contexto de sua aplica��o. Aquele cen�rio acontece? Se sim, ent�o talvez o padr�o de projeto ensinado naquele cap�tulo seja uma boa sa�da para controlar a crescente complexidade daquele c�digo.

Durante todos os outros cap�tulos, faremos muitos exerc�cios. Todas as explica��es ser�o baseadas em problemas do mundo real e voc�, ao final, utilizar� os conhecimentos adquiridos para resolver os exerc�cios, que s�o desafiadores! N�o se esque�a de prestar muita aten��o ao cen�rio e ao problema proposto. Entenda a motiva��o de cada padr�o para que voc� consiga lev�-lo para os problemas do seu mundo!

Muitas regras e c�digo complexo
Tomando como exemplo uma aplica��o cujo objetivo � a cria��o de or�amentos, temos uma regra de neg�cio na qual os valores dos or�amentos podem ser submetidos � alguns impostos, como ISS, ICMS e assim por diante. Com isso, temos a simples classe que representa o or�amento, recebendo via construtor o seu valor: 

```java
public class Orcamento { 
	private double valor; 

	public Orcamento(double valor) { 
		this.valor = valor; 
	} 

	public double getValor() { 
		return valor; 
	} 
} 

```
Com isso, podemos criar novos or�amentos, instanciando objetos do respectivo tipo e caso queiramos calcular um imposto sobre seu valor, basta utilizarmos o atributo valor para isso. Assim, podemos estipular que o ICMS valha 10% e precisamos calcul�-lo, baseado no valor do or�amento. Para isso, podemos ter a seguinte classe com um simples m�todo para realizar o c�lculo: 

```java
public class CalculadorDeImpostos { 
	public void realizaCalculo(Orcamento orcamento) { 
		double icms = orcamento.getValor() * 0.1; System.out.println(icms); // imprimir� 50.0 
	} 
} 
```

Podemos ainda querer calcular outro imposto, como o ISS, que � 6% do valor do or�amento. Com isso, adicionamos a nova regra ao c�digo anterior. Mas devemos escolher qual o imposto que ser� calculado. Portanto, o m�todo realizaCalculo dever� receber uma informa��o, indicando qual o imposto ter� o c�lculo realizado: 

```java
public class CalculadorDeImpostos { 
	public void realizaCalculo(Orcamento orcamento, String imposto) { 
		if( "ICMS".equals(imposto) ) { 
			double icms = orcamento.getValor() * 0.1; 
			System.out.println(icms); // imprimir� 50.0 
		} else if( "ISS".equals(imposto) ) { 
			double iss = orcamento.getValor() * 0.06; 
			System.out.println(iss); // imprimir� 30.0 
		} 
	} 
} 
```

Note que uma das consequ�ncias do c�digo que acabamos de criar, � que espalhamos os c�lculos e nossas regras de neg�cio. Dessa maneira, n�o temos nenhum encapsulamento de nossas regras de neg�cio e elas se tornam bastante suscet�veis a serem replicadas em outros pontos do c�digo da aplica��o. Por que n�o encapsulamos as regras dos c�lculos em uma classe especializada para cada imposto?
Encapsulando o comportamento
Ao inv�s de mantermos as regras espalhadas pela nossa aplica��o, podemos encapsul�-las em classes cujas responsabilidades sejam realizar os c�lculos. Para isso, podemos criar as classes ICMS e ISS cada um com seu respectivo m�todo para calcular o valor do imposto de acordo com o or�amento.

```java

      public class ICMS {

          public double calculaICMS(Orcamento orcamento) {
              return orcamento.getValor() * 0.1;
          }

      }
      public class ISS {

          public double calculaISS(Orcamento orcamento) {
              return orcamento.getValor() * 0.06;
          }

      }
```

Agora temos as duas classes que separam a responsabilidade dos c�lculos de impostos, com isso, podemos utiliz�-las na classe CalculadorDeImpostos da seguinte maneira:

```java
      public class CalculadorDeImpostos {

          public void realizaCalculo(Orcamento orcamento, String imposto) {

          if( "ICMS".equals(imposto) ) {

            double icms = new ICMS().calculaICMS(orcamento);
            System.out.println(icms); // imprimir� 50.0

          } else if( "ISS".equals(imposto) ) {

            double iss = new ISS().calculaISS(orcamento);
            System.out.println(iss); // imprimir� 30.0

            }
          }
      }

```
Agora o c�digo est� melhor, mas n�o significa que esteja bom. Um ponto extremamente cr�tico desse c�digo � o fato de que quando quisermos adicionar mais um tipo diferente de c�lculo de imposto em nosso calculador, teremos que alterar essa classe adicionando mais um bloco de if, al�m de criarmos a classe que encapsular� o c�lculo do novo imposto. Parece bastante trabalho.

Eliminando os condicionais com polimorfismo e o pattern Strategy
O que queremos em nosso c�digo � n�o realizar nenhum condicional, ou seja, n�o termos mais que fazer ifs dentro do CalculadorDeImpostos. Dessa forma, n�o devemos mais receber a String com o nome do imposto, no qual realizamos os ifs. Mas como escolheremos qual o imposto que deve ser calculado?

Uma primeira possibilidade � criar dois m�todos separados na classe CalculadorDeImpostos. Um para o ICMS e outro para o ISS, dessa forma teremos:
```java

      public class CalculadorDeImpostos {

          public void realizaCalculoICMS(Orcamento orcamento) {

            double icms = new ICMS().calculaICMS(orcamento);
            System.out.println(icms);

          }

          public void realizaCalculoISS(Orcamento orcamento) {

            double iss = new ISS().calculaISS(orcamento);
            System.out.println(iss);

          }

      }

```
No entanto, agora s� transferimos o problema dos v�rios ifs para v�rios m�todos. O que n�o resolve o problema. O pr�ximo passo para conseguirmos melhorar essa solu��o � termos um �nico m�todo, gen�rico, que consegue realizar o c�lculo para qualquer imposto, sem fazer nenhum if dentro dele.

```java
      public class CalculadorDeImpostos {

          public void realizaCalculo(Orcamento orcamento) {

            double icms = new ICMS().calculaICMS(orcamento); 
            // Mas e se quisermos outro imposto?

            System.out.println(icms);

          }

      }

```
Agora estamos presos ao ICMS. Precisamos que nosso c�digo fique flex�vel o bastante para utilizarmos diferentes impostos na realiza��o do c�lculo. Uma possibilidade para resolvermos esse problema �, ao inv�s de instanciarmos o imposto que desejamos dentro do m�todo, recebermos uma inst�ncia do Imposto que queremos utilizar, como no c�digo seguinte:

```java

      public class CalculadorDeImpostos {

          public void realizaCalculo(Orcamento orcamento, Imposto imposto) {

            double valor = imposto.calcula(orcamento); 

            System.out.println(valor);

          }

      }

```
No entanto, n�o temos o tipo Imposto em nossa aplica��o e al�m disso, nesse tipo precisamos passar uma inst�ncia de ISS e ICMS. Para isso, podemos criar uma interface chamada Imposto e fazermos as classes ISS e ICMS a implementar.
```java

      public interface Imposto {
          double calcula(Orcamento orcamento);
      }

      public class ICMS implements Imposto {

          public double calcula(Orcamento orcamento) {
              return orcamento.getValor() * 0.1;
          }

      }

      public class ISS implements Imposto {

          public double calcula(Orcamento orcamento) {
              return orcamento.getValor() * 0.06;
          }

      }

```
E agora o nosso CalculadorDeImpostos est� pronto para ser utilizado e flex�vel o bastante para receber diferentes tipos (ou "estrat�gias") de impostos. Um c�digo que demonstra essa flexibilidade � o seguinte:

```java
      public class TesteDeImpostos {

          public static void main(String[] args) {
              Imposto iss = new ISS();
              Imposto icms = new ICMS();

              Orcamento orcamento = new Orcamento(500.0);

          CalculadorDeImpostos calculador = new CalculadorDeImpostos();

          // Calculando o ISS
          calculador.realizaCalculo(orcamento, iss);

          // Calculando o ICMS        
          calculador.realizaCalculo(orcamento, icms);
          }
      }

```
Agora, com um �nico m�todo em nosso CalculadorDeImpostos, podemos realizar o c�lculo de diferentes tipos de impostos, apenas recebendo a estrat�gia do tipo do imposto que desejamos utilizar no c�lculo.

Quando utilizamos uma hierarquia, como fizemos com a interface Imposto e as implementa��es ICMS e ISS, e recebemos o tipo mais gen�rico como par�metro, para ganharmos o polimorfismo na regra que ser� executada, simplificando o c�digo e sua evolu��o, estamos usando o Design Pattern chamado Strategy.

Repare que a cria��o de uma nova estrat�gia de c�lculo de imposto n�o implica em mudan�as no c�digo escrito acima! Basta criarmos uma nova classe que implementa a interface Imposto, que nosso CalculadorDeImpostos conseguir� calcul�-lo sem precisar de nenhuma altera��o!

<h2>O que � um padr�o de projeto?</h2>
Um padr�o de projeto � uma solu��o elegante para um problema que � recorrente no dia-a-dia do desenvolvedor.

Ou seja, por mais que desenvolvamos projetos diferentes, muitos dos problemas se repetem. Padr�es de projeto s�o solu��es elegantes e flex�veis para esses problemas.

<h2>Estudando a motiva��o dos padr�es</h2>

O que � mais importante ao estudar um padr�o de projetos?
R: Entender a motiva��o do padr�o de projeto e qual problema ele resolve.

O mais importante ao estudar padr�es de projeto � entender qual a real motiva��o do padr�o, e quando ele deve ser aplicado.

As implementa��es s�o menos importantes, pois eles podem variar. O importante � resolver o problema de maneira elegante, usando a ideia por tr�s do padr�o como um guia na implementa��o. Uma afirma��o muito comum sobre padr�es de projeto � que voc� os aplica mil vezes, e as mil vezes voc� termina com uma implementa��o diferente do mesmo padr�o.

<h2>O que � um padr�o de projeto?</h2>

Voc� j� conhece padr�es de projeto? Em sua opini�o, o que � um padr�o de projeto?
<br/ >
R: Um padr�o de projeto � uma solu��o elegante para um problema que � recorrente no dia-a-dia do desenvolvedor.
Ou seja, por mais que desenvolvamos projetos diferentes, muitos dos problemas se repetem. Padr�es de projeto s�o solu��es elegantes e flex�veis para esses problemas.

<h2>Estudando a motiva��o dos padr�es</h2>
O que e mais importante ao estudar um padr�o de projeto?
R: Entender a motiva��o do padr�o de projeto e qual problema ele resolve.
As implementa��es s�o menos importantes, pois eles podem variar. O importante � resolver o problema de maneira elegante, usando a ideia por tr�s do padr�o como um guia na implementa��o. Uma afirma��o muito comum sobre padr�es de projeto � que voc� os aplica mil vezes, e as mil vezes voc� termina com uma implementa��o diferente do mesmo padr�o.


<h2>Implementando um Strategy</h2>
Crie todo o mecanismo para flexibilizar a cria��o de diferentes estrat�gias de impostos, igual visto no v�deo. Crie a interface Imposto, e as estrat�gias ICMS e ISS. O ISS deve ser 6% do valor do or�amento, e o ICMS deve ser 5% do valor do or�amento mais o valor fixo de R$ 50,00.

Crie a classe Orcamento, que tem como atributo um valor. Crie um construtor que recebe esse valor, e um getter para devolv�-lo.

Crie a classe CalculadorDeImpostos, que recebe um Orcamento e um Imposto. Essa classe calcula o imposto usando a estrat�gia recebida e imprime o resultado na tela.

Vamos come�ar com a classe Or�amento.

```java
    public class Orcamento {

        private double valor;

        public Orcamento(double valor) {
            this.valor = valor;
        }

        public double getValor() {
            return valor;
        }

    }
```
Em seguida, vamos criar a abstra��o Imposto. Todo imposto dever� implementar essa interface, j� que a interface � a que ser� utilizada para fazermos o Strategy.

```java
      public interface Imposto {
          double calcula(Orcamento orcamento);
      }
```
Agora vamos fazer as implementa��es concretas dos impostos ICMS e ISS, ambos implementando a interface Imposto, cada um com sua regra espec�fica:

```java
      public class ICMS implements Imposto {

          public double calcula(Orcamento orcamento) {
              return orcamento.getValor() * 0.05 + 50;
          }

      }

      public class ISS implements Imposto {

          public double calcula(Orcamento orcamento) {
              return orcamento.getValor() * 0.06;
          }

      }
```
Vamos criar agora uma classe cliente, que receber� a estrat�gia, e a utilizar�. Veja a classe abaixo: ela recebe a estrat�gia, e a utiliza para calcular o imposto; em seguida, apenas exibe o resultado na tela. Em uma implementa��o mais real, essa classe poderia fazer algo mais �til com o resultado.


```java
      public class CalculadorDeImposto {

        public void calcula(Orcamento orcamento, Imposto estrategiaDeImposto) {
          double resultado = estrategiaDeImposto.calcula(orcamento);
          System.out.println(resultado);
        }
      }
```

<h2>Estrat�gia para o imposto ICCC</h2>
Implemente mais uma estrat�gia de c�lculo de imposto.

Crie o imposto que se chama ICCC, que retorna 5% do valor total caso o or�amento seja menor do que R$ 1000,00 reais, 7% caso o valor esteja entre R$ 1000 e R$ 3000,00 com os limites inclusos, ou 8% mais 30 reais, caso o valor esteja acima de R$ 3000,00.

Escreva um m�todo main que testa sua implementa��o.

Basta criar a classe ICCC, que implementa a interface Imposto. Em seguida, uma classe de teste que invocar� essa estrat�gia, passando uma or�amento qualquer de exemplo.

```java
     public class ICCC implements Imposto {

        public double calcula(Orcamento orcamento) {
          if(orcamento.getValor() < 1000) {
            return orcamento.getValor() * 0.05;
          }
          else if (orcamento.getValor() <= 3000) {
            return orcamento.getValor() * 0.07;
          }
          else {
            return orcamento.getValor() * 0.08 + 30;
          }
        }
      }

     public  class Teste {
        public static void main(String[] args) {
          Orcamento reforma = new Orcamento(500.0);

          Imposto novoImposto = new ICCC();
          System.out.println(novoImposto.calcula(reforma));
        }
      }

```

<h2>Utilidade da classe CalculadoraDeImpostos</h2>

A classe CalculadoraDeImpostos agora tem pouco c�digo. Ela simplesmente invoca a estrat�gia de imposto e imprime atrav�s de um System.out.println() na tela.

Ser� que ela precisa continuar existindo?

<br/>
Nesse exemplo, como a CalculadoraDeImpostos apenas exibe uma mensagem na tela, ela n�o � necess�ria. Mas agora imagine que, al�m de calcular o imposto, essa classe precisasse fazer mais alguma coisa, como por exemplo, alterar o status do Or�amento, ou notificar algum outro objeto desse valor calculado. Nesse caso, precisar�amos de uma classe para conter essa regra de neg�cios, e a classe CalculadoraDeImpostos seria uma boa candidata.

Repare que n�o h� resposta correta; tudo sempre depende do contexto, do problema que estamos resolvendo.

<h2>Estrat�gias para Investimentos</h2>

Muitas pessoas optam por investir o dinheiro das suas contas banc�rias. Existem diversos tipos de investimentos, desde investimentos conservadores at� mais arrojados.

Independentemente do investimento escolhido, o titular da conta recebe apenas 75% do lucro do investimento, pois 25% � imposto.

Implemente um mecanismo que invista o valor do saldo dela em um dos v�rios tipos de investimento e, dado o retorno desse investimento, 75% do valor � adicionado no saldo da conta.

Crie a classe RealizadorDeInvestimentos que recebe uma estrat�gia de investimento, a executa sobre uma conta banc�ria, e adiciona o resultado seguindo a regra acima no saldo da conta.

Os poss�veis tipos de investimento s�o:

"CONSERVADOR", que sempre retorna 0.8% do valor investido;
"MODERADO", que tem 50% de chances de retornar 2.5%, e 50% de chances de retornar 0.7%;
"ARROJADO", que tem 20% de chances de retornar 5%, 30% de chances de retornar 3%, e 50% de chances de retornar 0.6%.
Para verificar se a chance � maior que 30%, por exemplo, use:

  boolean escolhido = new java.util.Random().nextDouble() > 0.30;

Vamos come�ar modelando nossa classe Conta, com um saldo e um m�todo deposita():

```java
      class Conta {
        private double saldo;

        public void deposita(double valor) {
          this.saldo += valor;
        }

        public double getSaldo() {
          return this.saldo;
        }
      }
```

Em seguida vamos criar a interface Investimento, e suas implementa��es concretas:

```java
      interface Investimento {
        double calcula(Conta conta);
      }

      class Conservador implements Investimento{
        public double calcula(Conta conta) {
          return conta.getSaldo() * 0.008;
        } 
      }

      class Moderado implements Investimento {
        private Random random;

        public Moderado() {
          this.random = new Random();
        }

        public double calcula(Conta conta) {
          if(random.nextInt(2) == 0) return conta.getSaldo() * 0.025;
          else return conta.getSaldo() * 0.007;
        }
      }

      class Arrojado implements Investimento {
        private Random random;

        public Arrojado() {
          this.random = new Random();
        }

        public double calcula(Conta conta) {
          int chute = random.nextInt(10);
          if(chute >= 0 && chute <= 1) return conta.getSaldo() * 0.05;
          else if (chute >= 2 && chute <= 4) return conta.getSaldo() * 0.03;
          else return conta.getSaldo() * 0.006;
        }
      }
```

Agora vamos fazer a classe RealizadorDeInvestimentos, que receber� uma conta e um investimento, e depositar� o valor do investimento na conta:
```java
      class RealizadorDeInvestimentos {
        public void realiza(Conta conta, Investimento investimento) {
          double resultado = investimento.calcula(conta);

          conta.deposita( resultado * 0.75 );
          System.out.println ( "Novo saldo: " + conta.getSaldo());
        }
      }
```

<h2>Strategy o tempo todo?</h2>

Se eu tenho apenas uma �nica estrat�gia, faz sentido implementar o Strategy?

Depende do problema. Lembre-se que c�digos simples s�o mais f�ceis de manter sempre. Se voc� s� tem uma estrat�gia, talvez fa�a mais sentido voc� n�o usar o Strategy, j� que voc� estaria flexibilizando algo sem necessidade.

Mas, se � n�tido que novas estrat�gias aparecer�o, com certeza um Strategy � mais limpo do que um conjunto de ifs, conforme discutimos nesse cap�tulo.

Novamente, avalie o contexto e veja se o padr�o de projeto vai trazer benef�cios para aquele cen�rio.

<h2>Quando usar o Strategy?</h2>
Quando devemos aplicar Strategy? Cite um exemplo que voc� viveu que poderia ter utilizado esse padr�o.
O padr�o Strategy � muito �til quando temos um conjunto de algoritmos similares, e precisamos alternar entre eles em diferentes peda�os da aplica��o. No exemplo do v�deo, temos diferentes maneiras de calcular o imposto, e precisamos alternar entre elas.

O Strategy nos oferece uma maneira flex�vel para escrever diversos algoritmos diferentes, e de passar esses algoritmos para classes clientes que precisam deles. Esses clientes desconhecem qual � o algoritmo "real" que est� sendo executado, e apenas mandam o algoritmo rodar. Isso faz com que o c�digo da classe cliente fique bastante desacoplado das implementa��es concretas de algoritmos, possibilitando assim com que esse cliente consiga trabalhar com N diferentes algoritmos sem precisar alterar o seu c�digo.

------------------------------------------------------------------------------------
<h1>Se��o 02 - Muitos Descontos e o Chain of Responsibility</h1>

Nosso or�amento pode receber um desconto de acordo com o tipo da venda que ser� efetuada. Por exemplo, se o cliente comprou mais de 5 �tens, ele recebe 10% de desconto; se ele fez uma compra casada de alguns produtos, recebe 5% de desconto, e assim por diante.

Em uma implementa��o tipicamente procedural, ter�amos algo do tipo:

```java
    public class CalculadorDeDescontos {
      public double calcula(Orcamento orcamento) {
        // verifica primeira regra de poss�vel desconto
        if(orcamento.getItens().size() > 5) {
          return orcamento.getValor() * 0.1;
        }

        // verifica segunda regra de poss�vel desconto
        else if(orcamento.getValor() > 500.0) {
          return orcamento.getValor() * 0.07;
        }

        // verifica terceira, quarta, quinta regra de poss�vel desconto ...
        // um monte de ifs daqui pra baixo
      }
    }
```

Ver esse monte de ifs em sequ�ncia nos lembra o cap�tulo anterior, na qual extra�mos cada um deles para uma classe espec�fica. Vamos repetir o feito, j� que com classes menores, o c�digo se torna mais simples de entender:
```java
    public class DescontoPorMaisDeCincoItens {
      public double desconta(Orcamento orcamento) {
        if(orcamento.getItens().size() > 5) {
          return orcamento.getValor() * 0.1;
        }
        else {
          return 0;
        }
      }
    }
    public class DescontoPorMaisDeQuinhentosReais {
      public double desconta(Orcamento orcamento) {
        if(orcamento.getValor() > 500) {
          return orcamento.getValor() * 0.07;
        }
        else {
          return 0;
        }
      }
    }
```

Veja que tivemos que sempre colocar um return 0;, afinal o m�todo precisa sempre retornar um double. Vamos agora substituir o conjunto de ifs na classe CalculadorDeDesconto. Repare que essa classe procura pelo desconto que deve ser aplicado; caso o anterior n�o seja v�lido, tenta o pr�ximo.
```java
    public class CalculadorDeDescontos {
      public double calcula(Orcamento orcamento) {
        // vai chamando os descontos na ordem at� que algum deles d� diferente de zero...
        double desconto = new DescontoPorMaisDeCincoItens().desconta(orcamento);
        if(desconto == 0) 
          desconto = new DescontoPorMaisDeQuinhentosReais().desconta(orcamento);
        if(desconto == 0) 
          desconto = new ProximoDesconto().desconta(orcamento);
        // ...

        return desconto;
      }
    }
```

O c�digo j� est� um pouco melhor. Cada regra de neg�cio est� em sua respectiva classe. O problema agora � como fazer essa sequ�ncia de descontos ser aplicada na ordem, pois precisamos colocar mais um if sempre que um novo desconto aparecer.

Precis�vamos fazer com que um desconto qualquer, caso n�o deva ser executado, automaticamente passe para o pr�ximo, at� encontrar um que fa�a sentido. Algo do tipo:
```java
      public class DescontoPorMaisDeQuinhentosReais {
        public double desconta(Orcamento orcamento) {
          if(orcamento.getValor() > 500) {
            return orcamento.getValor() * 0.07;
          }
          else {
            return PROXIMO DESCONTO;
          }
        }
      }
```
Todos os descontos t�m algo em comum. Todos eles calculam o desconto dado um or�amento. Podemos criar uma abstra��o para representar um desconto gen�rico. Por exemplo:

```java
      public interface Desconto {
        double desconta(Orcamento orcamento);
      }

      public class DescontoPorMaisDeCincoItens implements Desconto { ... }
      public class DescontoPorMaisDeQuinhentosReais implements Desconto { ... }
```
Para fazer aquele c�digo funcionar agora, basta fazer com que todo desconto receba um pr�ximo desconto! Observe o c�digo abaixo:

```java
      public interface Desconto {
        double desconta(Orcamento orcamento);
        void setProximo(Desconto proximo);
      }

      public class DescontoPorMaisDeCincoItens implements Desconto {
        private Desconto proximo;

        public void setProximo(Desconto proximo) {
          this.proximo = proximo;
        }

        public double desconta(Orcamento orcamento) {
          if(orcamento.getItens().size() > 5) {
            return orcamento.getValor() * 0.1;
          }
          else {
            return proximo.desconta(orcamento);
          }
        }
      }
      public class DescontoPorMaisDeQuinhentosReais implements Desconto {
        private Desconto proximo;

        public void setProximo(Desconto proximo) {
          this.proximo = proximo;
        }

        public double desconta(Orcamento orcamento) {
          if(orcamento.getValor() > 500) {
            return orcamento.getValor() * 0.07;
          }
          else {
            return proximo.desconta(orcamento);
          }
        }
      }
```

Ou seja, se o or�amento atende a regra de um desconto, o mesmo j� calcula o desconto. Caso contr�rio, ele passa para o "pr�ximo" desconto, qualquer que seja esse pr�ximo desconto.

Basta agora plugarmos todas essas classes juntas. Veja que um desconto recebe um "pr�ximo". Para o desconto, pouco importa qual � o pr�ximo desconto. Eles est�o totalmente desacoplados um do outro!

```java
      public class CalculadorDeDescontos {
        public double calcula(Orcamento orcamento) {
          Desconto d1 = new DescontoPorMaisDeCincoItens ();
          Desconto d2 = new DescontoPorMaisDeQuinhentosReais();

          d1.setProximo(d2);

          return d1.desconta(orcamento);
        }
      }

      public class TestaDescontos {
        public static void main(String[] args) {
          CalculadorDeDescontos calculador = new CalculadorDeDescontos();

              Orcamento orcamento = new Orcamento(500.0);
              orcamento.adicionaItem(new Item("CANETA", 250.0));
              orcamento.adicionaItem(new Item("LAPIS", 250.0));

              double desconto = calculador.calcula(orcamento);

              System.out.println(desconto);

        }
      }
```
Esses descontos formam como se fosse uma "corrente", ou seja, um ligado ao outro. Da� o nome do padr�o de projeto: Chain of Responsibility. A ideia do padr�o � resolver problemas como esses: de acordo com o cen�rio, devemos realizar alguma a��o. Ao inv�s de escrevermos c�digo procedural, e deixarmos um �nico m�todo descobrir o que deve ser feito, quebramos essas responsabilidades em v�rias diferentes classes, e as unimos como uma corrente.

Nosso problema � s� fazer o algoritmo parar agora. Se ele n�o encontrar nenhum desconto v�lido, o valor deve ser 0. Vamos criar a classe SemDesconto, que ser� o fim da corrente.

```java
      public class SemDesconto implements Desconto {

          public double desconta(Orcamento orcamento) {
              return 0;
          }

          public void setProximo(Desconto desconto) {
              // nao tem!
          }
      }

      public class CalculadorDeDescontos {
        public double calcula(Orcamento orcamento) {
          Desconto d1 = new DescontoPorMaisDeCincoItens();
          Desconto d2 = new DescontoPorMaisDeQuinhentosReais();
          Desconto d3 = new SemDesconto();

          d1.setProximo(d2);
          d2.setProximo(d3);

          return d1.desconta(orcamento);
        }
      }
```

A classe SemDesconto n�o atribui o pr�ximo desconto, pois ela n�o possui um pr�ximo. Na realidade, ela � o ponto final da nossa cadeia de responsabilidades.

Note tamb�m que nossa classe Orcamento cresceu, e agora recebe �tens tamb�m. A mudan�a � simples:

```java
      public class Orcamento {

          private double valor;
          private List<Item> itens;

          public Orcamento(double valor) {
              this.valor = valor;
              this.itens = new ArrayList<Item>();
          }

          public double getValor() {
              return valor;
          }

          public List<Item> getItens() {
              return Collections.unmodifiableList(itens);
          }

          public void adicionaItem(Item item) {
              itens.add(item);
          }

      }
      public class Item {

          private String nome;
          private double valor;

          public Item(String nome, double valor) {
              this.nome = nome;
              this.valor = valor;
          }

          public String getNome() {
              return nome;
          }

          public double getValor() {
            return valor;
          }

      }
```

<h2>Desconto por Venda Casada</h2>

Implemente mais uma estrat�gia de desconto: 5% se tivermos LAPIS e CANETA na mesma compra.

Para isso, crie a classe DescontoPorVendaCasada. Adicione essa classe na cadeia, para suceder o DescontoPorMaisDeQuinhentosReais.

Para verificar se um item est� no or�amento voc� pode usar uma fun��o como a seguinte:

```java
        private boolean existe(String nomeDoItem, Orcamento orcamento) {
            for (Item item : orcamento.getItens()) {
                if(item.getNome().equals(nomeDoItem)) return true;
            }
            return false;
        }
```
Parecido com o exerc�cio anterior, nosso desconto implementa a interface Desconto e passa para o pr�ximo desconto caso o or�amento n�o deva receber esse desconto.


```java
    public class DescontoPorVendaCasada implements Desconto {

        private Desconto proximo;

        public double desconta(Orcamento orcamento) {
            if(aconteceuVendaCasadaEm(orcamento)) return orcamento.getValor() * 0.05;
            else return proximo.desconta(orcamento);
        }

        private boolean aconteceuVendaCasadaEm(Orcamento orcamento) {
            return existe("CANETA", orcamento) && existe("LAPIS", orcamento);
        }

        private boolean existe(String nomeDoItem, Orcamento orcamento) {
            for (Item item : orcamento.getItens()) {
                if(item.getNome().equals(nomeDoItem)) return true;
            }
            return false;
        }

        public void setProximo(Desconto proximo) {
            this.proximo = proximo;
        }

    }

    public class TestaCorrente {
      public static void main(String[] args) {
        Desconto d1 = new DescontoPorCincoItens();
        Desconto d2 = new DescontoPorMaisDeQuinhentosReais();
        Desconto d3 = new DescontoPorVendaCasada();
        Desconto d4 = new SemDesconto();

        d1.setProximo(d2);
        d2.setProximo(d3);
        d3.setProximo(d4);

    Item lapis = new Item("LAPIS", 15.00);
        Item caneta = new Item("CANETA", 15.00);
        Item borracha = new Item("borracha", 15.00);

        Orcamento orcamento = new Orcamento(500.0);
        orcamento.adiciona(caneta);
        orcamento.adiciona(borracha);
        orcamento.adiciona(lapis);        

        double desconto = d1.desconta(orcamento);
        System.out.println(desconto);
      }
    }
```

<h2>Utiidade da classe CalculadorDeDescontos</h2>

Ser� que a classe CalculadorDeDescontos � realmente necess�ria? Discuta a utilidade dela.

Agora que implementamos o Chain of Responsibility, temos cada uma das responsabilidades separadas em uma classe, e uma forma de unir essa corrente novamente. Veja a flexibilidade que o padr�o nos deu: podemos montar a corrente da forma como quisermos, e sem muitas complica��es.

Mas precisamos de uma classe que monte essa corrente na ordem certa, com todos os descontos necess�rios. Por isso que optamos pela classe CalculadorDeDescontos. Ela poderia ter qualquer outro nome como CorrenteDeDescontos, e assim por diante, mas fato � que em algum lugar do seu c�digo voc� precisar� montar essa corrente.