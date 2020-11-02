import java.util.Scanner;
import java.util.Stack;

public class Comparador {
	
	public boolean balanceo(String texto) {
		Stack<Character> stack= new Stack<Character>();
		char letra;
		boolean balanceado= true;
		
		for (int i=0; i<texto.length();i++) {
			
			letra=texto.charAt(i);
			if (letra=='(' || letra=='[' || letra=='{') {
				stack.add(letra);
			}else if (letra==')' || letra==']' || letra=='}'){
				if (stack.isEmpty()) {
					break;
				}else{
					if ((stack.peek()== '(' && letra==')') ||(stack.peek()== '{' && letra=='}') ||(stack.peek()== '[' && letra==']') ) {
						stack.pop();
						balanceado=true;
						
					}else {
						balanceado=false;
						break;
					}
				}
			}
		}
		
		if (!stack.isEmpty()) {
			balanceado=false;
		}
		
		if (balanceado) {
			System.out.println("Los parentesis están balanceados");
			return true;
		}else {
			System.out.println("Los parentesis no están balanceados");
			return false;
		}
	}
	
	
	
	
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Introduce los parentesis");
		String texto = scan.next();
		Comparador a= new Comparador();
		a.balanceo(texto);
		
	}
}
