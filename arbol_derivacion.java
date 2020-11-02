import java.util.ArrayList;
import java.util.List;

public class arbol_derivacion {
	node root= new node("E");



	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	public void ayudantePreorden(node nodo) {
		String espacio="";
		
		ayudantePreorden(nodo, espacio);
		
	}

	private void ayudantePreorden(node nodo, String espacio)
	{
		if (nodo == null) {
			
			return;
			
		}
			

		/*if (nodo.izquierdo!=null) {
			System.out.println(espacio+nodo.izquierdo.value);
		}
		if (nodo.central!=null) {
			System.out.println(espacio+nodo.central.value);
		}
		if (nodo.derecho!=null) {
			System.out.println(espacio+nodo.derecho.value);
		}*/
		
		System.out.println(espacio+nodo.value);
		/*if (nodo.izquierdo==null && nodo.derecho==null && nodo.central==null) {
			System.out.println(espacio+"final");
		}*/

		espacio=espacio+"|";
		ayudantePreorden(nodo.parentesisIzquierdo, espacio);
		ayudantePreorden(nodo.izquierdo, espacio);
		ayudantePreorden(nodo.central, espacio);
		ayudantePreorden(nodo.derecho, espacio);
		ayudantePreorden(nodo.parentesisDerecho, espacio);
	}
	//recorre subarbol derecho
}

class node {
	String value;
	node izquierdo,
	central,
	derecho,
	parentesisDerecho,
	parentesisIzquierdo;
		

	public node(String string) {
		value=string;
	}

	public node(char charAt) {
		value=String.valueOf(charAt);
	}

	public void addChild(node a) {
		if (this.central==null && a.value=="O") {
			this.central=a;
		}else if (this.izquierdo==null) {
			this.izquierdo=a;
		}else if (this.derecho==null) {
			this.derecho=a;
		}else if (a.value==")") {
			this.parentesisDerecho=a;
		}else if (a.value=="(") {
			this.parentesisIzquierdo=a;
		}else{
			this.central=a;
		}
	}
}

