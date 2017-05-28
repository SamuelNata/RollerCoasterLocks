package montanhaRussa.Domain;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
	public static void main( String [] args ){
		int n=-1;
		int c=-1;
		int p=-1;
		
		if(args.length==3){
			n = Integer.valueOf(args[0]);
			c = Integer.valueOf(args[1]);
			p = Integer.valueOf(args[2]);
		}else{
			if(args.length==0){
				Scanner r = new Scanner(System.in);
				
				System.out.println("Entre com o número de pessoas no parque: ");
				n = r.nextInt();
				
				System.out.println("Entre com a capacidade do carro: ");
				c = r.nextInt();
				
				System.out.println("Entre com o número de voltas do brinquedo: ");
				p = r.nextInt();
				
				r.close();
			}
			else{
				System.out.println("Você deve entrar com 3 numeros: numero de pessoas, capacidade do carro,"
					+ " e quantidade de voltas que o brinquedo irá executar.");
				return;
			}
		}
		if(n<=0){
			System.out.println("O número de pessoas deve ser maior que zero.");
			return;
		}
		if(c<=0){
			System.out.println("A capacidade do carro deve ser maior que zero.");
			return;
		}
		if(n<=c){
			System.out.println("O número de pessoas deve ser maior que a capacidade do carro.");
		}
		if(p<=0){
			System.out.println("O número de voltas do brinquedo deve ser maior que zero.");
		}
		
		runProblem(n, c, p);
	}
	
	public static void runProblem( int numPersons, int carCapacity, int numRides){
		Runnable c1 = new Car("C1", carCapacity, numRides);
		Thread c = new Thread(c1);
		
		//Create persons
		ArrayList<Person> ps = new ArrayList<Person>();
		for( int i=1 ; i<=numPersons ; i++){
			ps.add(new Person("p"+i, (Car)c1));
		}
		
		//Start persons and car
		c.start();
		for( Person p : ps){
			p.start();
		}
		
		//Wait until everybody end
		try {
			c.join();
			System.out.println("Montanha russa fechada.");
			for( Person p : ps){
				p.join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
