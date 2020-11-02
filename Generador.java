import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Generador {

	public String[] E= {"N","(EOE)","EOE", "EE"};
	public String[] N= {"1","2","3","4","5","6","7","8","9","0"};
	public String[] O= {"+","-","*","/"};
	public char[] comprobar= {'1','2','3','4','5','6','7','8','9','0','*','-','+','/', '(', ')'};
	public List<String> oraciones= new ArrayList<String>();
	public List<Integer> medicionNiveles= new ArrayList<Integer>();
	public Stack<node> listaNodos= new Stack<node>();
	public arbol_derivacion arbol=new arbol_derivacion();


	public boolean initialVerification(String text) {

		//Esto es por si el texto es mayor a tres, se compruebe de izquierda a derecha
		if (text.length()>=2) {
			for (int i=0; i<text.length(); i++) {
				char letra=text.charAt(i);
				boolean tmp=false;

				for (int j=0; j<comprobar.length;j++) {
					if (letra==comprobar[j]) {
						tmp=true;
					}
				}

				if (!tmp) {
					return false;
				}
			}

			//Si el texto es de 1, se comprueba si es un numero	
		}else if (text.length()==1){
			for (int i=0; i<10; i++) {
				if (text.charAt(0)==comprobar[i]) {
					return true;
				}
			}
		}
		
		if(text.contains("++") || text.contains("+-") || text.contains("+*") || text.contains("+/")) {
			return false;
		}
		if(text.contains("-+") || text.contains("--") || text.contains("-*") || text.contains("-/")) {
			return false;
		}
		if(text.contains("*+") || text.contains("*-") || text.contains("**") || text.contains("*/")) {
			return false;
		}
		if(text.contains("/+") || text.contains("/-") || text.contains("/*") || text.contains("//")) {
			return false;
		}
		
		if(text.charAt(text.length()-1)=='+' ||text.charAt(text.length()-1)=='/' ||text.charAt(text.length()-1)=='*' || text.charAt(text.length()-1)=='-') {
			return false;
		}
		
		if(text.charAt(0)=='+' ||text.charAt(0)=='/' ||text.charAt(0)=='*' || text.charAt(0)=='-') {
			return false;
		}
		
		return true;

	}

	public boolean isBalanced(String text) {

		Comparador b=new Comparador();
		return b.balanceo(text); 
	}




	public String crearCadenas(String text) {

		String TraducidoEOE=evaluarEOE(text);
		oraciones.add(TraducidoEOE);
		System.out.println(TraducidoEOE);

		while (TraducidoEOE.length()>1) {


			TraducidoEOE=TraducidoEOE.replace("EOE", "E");

			if (TraducidoEOE==oraciones.get(oraciones.size()-1)) {
				oraciones.remove(oraciones.size()-1);
				oraciones.add(TraducidoEOE);
				if (TraducidoEOE.contains("(E)")) {
					TraducidoEOE=TraducidoEOE.replace("(E)", "E");
				}else if(TraducidoEOE.contains("EE")) {
					TraducidoEOE=TraducidoEOE.replace("EE", "E");
				}

			}
			oraciones.add(TraducidoEOE);
			System.out.println(TraducidoEOE);

		}


		return TraducidoEOE;
	}

	public String evaluarEOE(String text) {

		

		System.out.println(text);
		text=text.replace('+', 'O');
		text=text.replace("/", "O");
		text=text.replace("*", "O");
		text=text.replace("-", "O");
		text=text.replace("1", "E");
		text=text.replace("2", "E");
		text=text.replace("3", "E");
		text=text.replace("4", "E");
		text=text.replace("5", "E");
		text=text.replace("6", "E");
		text=text.replace("7", "E");
		text=text.replace("8", "E");
		text=text.replace("9", "E");
		text=text.replace("0", "E");

		return text;
	}


	
	public String sustitucionCompleta(String text) throws InterruptedException {
/*
		this.crearCadenas(text);
		int counterStringNumeros=0;

		String actual=oraciones.get(0);

		for (int i=0;i<actual.length();i++) {
			char digitoActual=actual.charAt(i);	

			if (actual.length()-1-i>=2) {
				
				if (digitoActual=='E' && actual.charAt(i+1)=='O' && actual.charAt(i+2)=='E') {
					node padre= new node("E");
					node tmp= new node("E");
					tmp.addChild(new node(text.charAt(counterStringNumeros)));
					padre.izquierdo=tmp;
					counterStringNumeros++;
					tmp= new node("O");
					tmp.addChild(new node(text.charAt(counterStringNumeros)));
					padre.central=tmp;
					counterStringNumeros++;
					tmp= new node("E");
					tmp.addChild(new node(text.charAt(counterStringNumeros)));
					padre.derecho=tmp;
					counterStringNumeros++;



					i+=2;
					listaNodos.push(padre);


				}else if (digitoActual=='E' && actual.charAt(i+1)=='O' ){

					node padre= new node("E");
					node tmp= new node("E");
					tmp.addChild(new node(text.charAt(counterStringNumeros)));
					counterStringNumeros++;
					padre.addChild(tmp);
					listaNodos.push(padre);
					padre= new node("O");
					tmp= new node("O");
					tmp.addChild(new node(text.charAt(counterStringNumeros)));
					counterStringNumeros++;
					padre.addChild(tmp);


					listaNodos.push(padre);
					i+=1;


					//Caso )OE	
				}else if (digitoActual==')' && actual.charAt(i+1)=='O' && actual.charAt(i+2)=='E'){

					node padre= new node("O");
					node tmp= new node("O");
					tmp.parentesisIzquierdo=new node(text.charAt(counterStringNumeros));
					counterStringNumeros++;
					tmp.addChild(new node(text.charAt(counterStringNumeros)));
					counterStringNumeros++;
					padre.addChild(tmp);
					listaNodos.push(padre);


					padre= new node("E");
					tmp= new node("E");
					tmp.addChild(new node(text.charAt(counterStringNumeros)));
					counterStringNumeros++;

					padre.addChild(tmp);
					listaNodos.push(padre);
					i+=2;





				}else if (digitoActual=='E' && actual.charAt(i+1)==')') {
					node padre= new node("E");
					node tmp= new node("E");
					tmp.parentesisIzquierdo=new node(text.charAt(counterStringNumeros));
					counterStringNumeros++;
					tmp.addChild(new node(text.charAt(counterStringNumeros)));
					counterStringNumeros++;
					padre.addChild(tmp);
					listaNodos.push(padre);
					i+=1;


				}else if (digitoActual=='(' && actual.charAt(i+1)=='E' && actual.charAt(i+2)=='E') {
					node padre= new node("E");
					node tmp= new node("E");
					tmp.parentesisIzquierdo=new node(text.charAt(counterStringNumeros));
					counterStringNumeros++;
					tmp.addChild(new node(text.charAt(counterStringNumeros)));
					counterStringNumeros++;
					padre.addChild(tmp);
					listaNodos.push(padre);
					i+=1;


				}else if (digitoActual=='(' && actual.charAt(i+1)=='E' && actual.charAt(i+2)==')') {
					node padre= new node("E");
					node tmp= new node("E");
					tmp.parentesisIzquierdo=new node(text.charAt(counterStringNumeros));
					counterStringNumeros++;
					tmp.addChild(new node(text.charAt(counterStringNumeros)));
					counterStringNumeros++;
					padre.addChild(tmp);
					tmp.derecho=new node(text.charAt(counterStringNumeros));
					counterStringNumeros++;
					listaNodos.push(padre);
					i+=2;


				}else if(digitoActual=='O' && actual.charAt(i+1)=='(') {

					node padre= new node("O");
					node tmp= new node("O");
					tmp.parentesisIzquierdo=new node(text.charAt(counterStringNumeros));
					counterStringNumeros++;
					tmp.addChild(new node(text.charAt(counterStringNumeros)));
					counterStringNumeros++;
					padre.addChild(tmp);
					listaNodos.push(padre);



					//Caso E	
				}else if(digitoActual=='O') {

					node padre= new node("O");
					node tmp= new node("O");
					tmp.addChild(new node(text.charAt(counterStringNumeros)));
					counterStringNumeros++;
					padre.addChild(tmp);
					listaNodos.push(padre);



					//Caso E	
				}else if(digitoActual=='E') {

					node padre= new node("E");
					node tmp= new node("E");
					tmp.addChild(new node(text.charAt(counterStringNumeros)));
					counterStringNumeros++;
					padre.addChild(tmp);
					listaNodos.push(padre);


				}


			}else if(actual.length()-1-i>=2) {
				if (digitoActual=='E' && actual.charAt(i+1)=='E') {
					node padre= new node("E");
					node tmp= new node("E");
					tmp.addChild(new node(text.charAt(counterStringNumeros)));
					counterStringNumeros++;
					padre.addChild(tmp);
					listaNodos.push(padre);



				}else if (digitoActual=='O' && actual.charAt(i+1)=='('){

					node padre= new node("O");
					node tmp= new node("O");
					tmp.addChild(new node(text.charAt(counterStringNumeros)));
					counterStringNumeros++;
					padre.addChild(tmp);
					listaNodos.push(padre);
					tmp.parentesisDerecho=new node(text.charAt(counterStringNumeros));
					counterStringNumeros++;
					i+=1;


					//Caso )OE	
				}else if(digitoActual=='O') {

					node padre= new node("O");
					node tmp= new node("O");
					tmp.addChild(new node(text.charAt(counterStringNumeros)));
					counterStringNumeros++;
					padre.addChild(tmp);
					listaNodos.push(padre);



					//Caso E	
				}else if(digitoActual=='E') {

					node padre= new node("E");
					node tmp= new node("E");
					tmp.addChild(new node(text.charAt(counterStringNumeros)));
					counterStringNumeros++;
					padre.addChild(tmp);
					listaNodos.add(listaNodos.size(), padre);;

				}
			}else {
				if(digitoActual=='O') {

					node padre= new node("O");
					node tmp= new node("O");
					tmp.addChild(new node(text.charAt(counterStringNumeros)));
					counterStringNumeros++;
					padre.addChild(tmp);
					listaNodos.push(padre);



					//Caso E	
				}else if(digitoActual=='E') {

					node padre= new node("E");
					node tmp= new node("E");
					tmp.addChild(new node(text.charAt(counterStringNumeros)));
					counterStringNumeros++;
					padre.addChild(tmp);
					listaNodos.push(padre);

				}else {
					
				}
			}

			

		}




		String oracionActual;

		//Aquí solo se crea el nodo padre y se recuperan los nodos anteriores
		for (int i=1;i<oraciones.size()-2;i++) {
			int totalNodes=listaNodos.size();
			int counterNodes=0;
			oracionActual=oraciones.get(i);

			for (int k=0;k<oracionActual.length();k++) {
				char digitoActual=oracionActual.charAt(k);	
				counterNodes=0;

				if(!oracionActual.contains("O")) {
					
					if (k==oracionActual.length()-1) {
						node padre= new node("E");
						node tmp1=listaNodos.get(0);
						padre.izquierdo=tmp1;
						listaNodos.remove(0);
						listaNodos.push(padre);
					}else {
						node padre= new node("E");
						node tmp1=listaNodos.get(0);
						node tmp2=listaNodos.get(1);
						padre.izquierdo=tmp1;
						padre.central=tmp2;

						listaNodos.remove(0);
						listaNodos.remove(0);

						k+=1;
						listaNodos.push(padre);
					}	
					
				}else {
					//Caso EOE
					if (oracionActual.length()-1-k>=3) {
						/*if (digitoActual=='E' && oracionActual.charAt(k+1)=='O' && oracionActual.charAt(k+2)=='E' && oracionActual.charAt(k+3)=='O') {
							node padre= new node("E");
							node tmp1=listaNodos.get(0);
							node tmp2=listaNodos.get(1);
							node tmp3=listaNodos.get(2);
							node tmp4=listaNodos.get(3);
							padre.izquierdo=tmp1;
							padre.central=tmp2;
							padre.derecho=tmp3;

							listaNodos.remove(0);
							listaNodos.remove(0);
							listaNodos.remove(0);
							listaNodos.remove(0);


							
							listaNodos.push(padre);

							padre=new node("O");
							padre.izquierdo=tmp1;
							listaNodos.push(padre);
							
							
							k+=3;


							//Caso EE	
						}else if (digitoActual=='E' && oracionActual.charAt(k+1)=='O' && oracionActual.charAt(k+2)=='E') {
							node padre= new node("E");
							node tmp1=listaNodos.get(0);
							node tmp2=listaNodos.get(1);
							node tmp3=listaNodos.get(2);
							padre.izquierdo=tmp1;
							padre.central=tmp2;
							padre.derecho=tmp3;

							listaNodos.remove(0);
							listaNodos.remove(0);
							listaNodos.remove(0);


							k+=2;
							listaNodos.push(padre);



							//Caso EE	
						}else if (digitoActual=='E' && oracionActual.charAt(k+1)=='E' && oracionActual.charAt(k+2)=='E') {
							node padre= new node("E");
							node tmp1=listaNodos.get(0);
							node tmp2=listaNodos.get(1);
							node tmp3=listaNodos.get(2);
							padre.izquierdo=tmp1;
							padre.central=tmp2;
							padre.derecho=tmp3;

							listaNodos.remove(0);
							listaNodos.remove(0);
							listaNodos.remove(0);


							k+=2;
							listaNodos.push(padre);



							//Caso EE	
						}else if (digitoActual=='E' && oracionActual.charAt(k+1)=='E' && oracionActual.charAt(k+2)==')' ) {
							node padre= new node("E");
							node tmp1=listaNodos.get(0);
							node tmp2=listaNodos.get(1);
							padre.izquierdo=tmp1;
							padre.central=tmp2;

							listaNodos.remove(0);
							listaNodos.remove(0);

							listaNodos.push(padre);
							k+=2;

							//Caso EO(	
						}else if (digitoActual=='E' && oracionActual.charAt(k+1)=='O' && oracionActual.charAt(k+2)=='('){

							node padre= new node("E");
							padre.addChild(listaNodos.remove(0));

							listaNodos.push(padre);
							padre= new node("O");
							padre.addChild(listaNodos.remove(0));

							listaNodos.push(padre);
							k+=1;
						}else if (digitoActual=='E' && oracionActual.charAt(k+1)=='E') {
							node padre= new node("E");
							padre.addChild(listaNodos.remove(0));

							listaNodos.push(padre);

							//Caso O

						}else if (digitoActual=='E' && oracionActual.charAt(k+1)==')') {
							node padre= new node("E");
							padre.addChild(listaNodos.remove(0));
							//padre.parentesisDerecho=new node(")");

							listaNodos.push(padre);
							k+=1;

						}if (digitoActual=='O' && oracionActual.charAt(k+1)=='('){

							node padre= new node("O");
							padre.addChild(listaNodos.remove(0));

							listaNodos.push(padre);
							padre.parentesisDerecho=new node("(");
							

							listaNodos.push(padre);
							k+=1;




							//Caso O

						}
					}else if (oracionActual.length()-1-k>=2) {

						if (digitoActual=='E' && oracionActual.charAt(k+1)=='O' && oracionActual.charAt(k+2)=='E') {
							node padre= new node("E");
							node tmp1=listaNodos.get(0);
							node tmp2=listaNodos.get(1);
							node tmp3=listaNodos.get(2);
							padre.izquierdo=tmp1;
							padre.central=tmp2;
							padre.derecho=tmp3;

							listaNodos.remove(0);
							listaNodos.remove(0);
							listaNodos.remove(0);


							k+=2;
							listaNodos.push(padre);



							//Caso EE	
						}else if (digitoActual=='E' && oracionActual.charAt(k+1)=='E' && oracionActual.charAt(k+2)=='E') {
							node padre= new node("E");
							node tmp1=listaNodos.get(0);
							node tmp2=listaNodos.get(1);
							node tmp3=listaNodos.get(2);
							padre.izquierdo=tmp1;
							padre.central=tmp2;
							padre.derecho=tmp3;

							listaNodos.remove(0);
							listaNodos.remove(0);
							listaNodos.remove(0);


							k+=2;
							listaNodos.push(padre);



							//Caso EE	
						}else if (digitoActual=='E' && oracionActual.charAt(k+1)=='E' && oracionActual.charAt(k+2)==')' ) {
							node padre= new node("E");
							node tmp1=listaNodos.get(0);
							node tmp2=listaNodos.get(1);
							padre.izquierdo=tmp1;
							padre.central=tmp2;

							listaNodos.remove(0);
							listaNodos.remove(0);

							listaNodos.push(padre);
							k+=2;

							//Caso EO(	
						}else if (digitoActual=='E' && oracionActual.charAt(k+1)=='O' && oracionActual.charAt(k+2)=='('){

							node padre= new node("E");
							padre.addChild(listaNodos.remove(0));

							listaNodos.push(padre);
							padre= new node("O");
							padre.addChild(listaNodos.remove(0));

							listaNodos.push(padre);
							k+=1;
						}else if (digitoActual=='E' && oracionActual.charAt(k+1)=='E') {
							node padre= new node("E");
							padre.addChild(listaNodos.remove(0));

							listaNodos.push(padre);

							//Caso O

						}else if (digitoActual=='E' && oracionActual.charAt(k+1)==')') {
							node padre= new node("E");
							padre.addChild(listaNodos.remove(0));
							//padre.parentesisDerecho=new node(")");

							listaNodos.push(padre);
							k+=1;

						}if (digitoActual=='O' && oracionActual.charAt(k+1)=='('){

							node padre= new node("O");
							padre.addChild(listaNodos.remove(0));

							listaNodos.push(padre);
							padre.parentesisDerecho=new node("(");
							

							listaNodos.push(padre);
							k+=1;




							//Caso O

						}
					}
					else if(digitoActual=='O') {
						node padre= new node("O");
						padre.addChild(listaNodos.remove(0));

						listaNodos.push(padre);

					}else if(digitoActual=='E') {

						node padre= new node("E");
						padre.addChild(listaNodos.remove(0));

						listaNodos.push(padre);

					}
				}
				

			}

		}
		
		System.out.println(listaNodos.size());
		if ((text.length()>11 || text.contains("("))) {
			node tmp=listaNodos.remove(0);
			listaNodos.add(tmp);
		}
		
		for(int i=0;i<listaNodos.size();i++) {
			arbol.ayudantePreorden(listaNodos.get(i));
		}

		/*if (listaNodos.size()==2) {

			this.arbol.root.addChild(listaNodos.get(0));
			this.arbol.root.addChild(listaNodos.get(1));
		}else if (listaNodos.size()==3) {

			if (listaNodos.get(0).value=="E" && listaNodos.get(1).value=="E" && listaNodos.get(2).value=="E") {
				node tmp=new node("E");
				tmp.addChild(listaNodos.get(1));
				tmp.addChild(listaNodos.get(0));

				this.arbol.root.addChild(tmp);
				this.arbol.root.addChild(listaNodos.get(2));

			}else {

				this.arbol.root.addChild(listaNodos.get(0));
				this.arbol.root.addChild(listaNodos.get(1));
				this.arbol.root.addChild(listaNodos.get(2));
			}
		}else{
			if (listaNodos.get(0).value=="E" && listaNodos.get(1).value=="E" && listaNodos.get(2).value=="E") {
				node tmp=new node("E");
				tmp.addChild(listaNodos.get(1));
				tmp.addChild(listaNodos.get(0));

				node tmp2=new node("E");
				tmp2.addChild(listaNodos.get(2));
				tmp2.addChild(listaNodos.get(3));

				this.arbol.root.addChild(tmp);
				this.arbol.root.addChild(tmp2);

			}
		}*/

		
		
		return "A";
	}

	
	
	public void cambio(String text) {
		String traducidoEOE=this.evaluarEOE(text);
		System.out.println(traducidoEOE);
		
		
		//Pasar del text a TraducidoEOE
		for (int i=0;i<text.length();i++) {
			if (traducidoEOE.charAt(i)=='(' || traducidoEOE.charAt(i)==')') {
				
			}else {
				node tmp=new node(traducidoEOE.charAt(i));
				node num=new node(text.charAt(i));
				tmp.addChild(num);
				listaNodos.add(tmp);
			}
			
		}
	
		while (traducidoEOE.contains("O")) {
			for (int i=0;i<traducidoEOE.length();i++) {
				
				if (i<traducidoEOE.length()-2) {
					if (traducidoEOE.charAt(i)=='E' && traducidoEOE.charAt(i+1)=='O' && traducidoEOE.charAt(i+2)=='E') {
						node padre= new node("E");
						node tmp1=listaNodos.get(0);
						node tmp2=listaNodos.get(1);
						node tmp3=listaNodos.get(2);
						
						padre.izquierdo=tmp1;
						padre.central=tmp2;
						padre.derecho=tmp3;
						
						listaNodos.remove(0);
						listaNodos.remove(0);
						listaNodos.remove(0);
						listaNodos.add(padre);
						
						traducidoEOE=traducidoEOE.replaceFirst("EOE", "E");
					}else if (traducidoEOE.charAt(i)=='(' && traducidoEOE.charAt(i+1)=='E' && traducidoEOE.charAt(i+2)=='E') {
						node padre= new node("E");
						node tmp1=listaNodos.get(0);
						node tmp2=listaNodos.get(1);

						
						padre.izquierdo=tmp1;
						padre.central=tmp2;

						listaNodos.add(padre);

						listaNodos.remove(0);
						listaNodos.remove(0);

						
						i++;
						traducidoEOE=traducidoEOE.replaceFirst("\\(EE", "(E");
						
						
						//Este está bien
					}else if (traducidoEOE.charAt(i)=='(' && traducidoEOE.charAt(i+1)=='E' && traducidoEOE.charAt(i+2)==')') {
						node padre= new node("E");
						node tmp1=new node('(');
						node tmp2=listaNodos.get(0);
						node tmp3=new node(')');
						
						padre.izquierdo=tmp1;
						padre.central=tmp2;
						padre.derecho=tmp3;
						
						listaNodos.remove(0);

						listaNodos.add(padre);
						System.out.println(traducidoEOE);
						traducidoEOE=traducidoEOE.replaceFirst("\\(E\\)", "E");
						System.out.println(traducidoEOE);
						
					}else if (traducidoEOE.charAt(i)==')' || traducidoEOE.charAt(i)=='('){
						
						
					}else {
						node padre= new node(traducidoEOE.charAt(i));
						node tmp1=listaNodos.get(0);	
						padre.izquierdo=tmp1;			
						listaNodos.remove(0);
						listaNodos.add(padre);
					}
					
				}else if (traducidoEOE.charAt(i)!=')' && traducidoEOE.charAt(i)!='('){
					node padre= new node(traducidoEOE.charAt(i));
					node tmp1=listaNodos.get(0);	
					padre.izquierdo=tmp1;			
					listaNodos.remove(0);
					listaNodos.add(padre);
				}
				
				
			}
		}

		traducidoEOE=traducidoEOE.replace("EOE", "E");
		
		
		//Reduccion final de EE a E
		while (listaNodos.size()>1) {
			for (int i=0;i<listaNodos.size();i++) {
				if (i!=listaNodos.size()-1) {
					node padre= new node("E");
					node tmp1=listaNodos.get(0);
					node tmp2=listaNodos.get(1);
					
					padre.izquierdo=tmp1;
					padre.central=tmp2;
					
					listaNodos.remove(0);
					listaNodos.remove(0);
					listaNodos.add(padre);
				}else {
					node tmp=new node("E");
					tmp.addChild(listaNodos.get(0));
					listaNodos.remove(0);
					listaNodos.add(tmp);
				}
			}
		}
		
		System.out.println(listaNodos.size());
		arbol.ayudantePreorden(listaNodos.get(0));
		
	}
	
	
	
	
	public static void main(String[] args){
		Generador a=new Generador();

		Scanner sc = new Scanner(System.in); 

		System.out.println("Ingresa la expresión aritmetica a analizar:");
		String text=sc.nextLine();

		if (a.initialVerification(text)) {
			if (a.isBalanced(text)) {
				
				a.cambio(text);
			}
		}else {
			System.out.println("La expresion no es valida");
		}


		;


	}

}
