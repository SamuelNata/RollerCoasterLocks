package montanhaRussa.Domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

public class Car implements Runnable {
	public static int capacity;
	public static int inCount;
	public static int outCount;
	private String name;
	public ArrayList<Person> passengers;
	private static int rides;
	public static ReentrantLock carDoorIn;
	public static ReentrantLock carDoorOut;
	
	public Car(String name, int capacity, int maximunRuns){
		this.name = name;
		Car.capacity = capacity;
		Car.rides = maximunRuns;
		passengers = new ArrayList<Person>();
	}
	
	@Override
	public void run(){
		carDoorIn = new ReentrantLock();
		carDoorOut = new ReentrantLock();
		carDoorIn.lock();
		carDoorOut.lock();
		
		while(rides>0){
			Car.inCount = 0;
			Car.outCount = 0;
			
			//Full Car
			System.out.println(name+" esperando pessoas: "+ Arrays.toString(passengers.toArray()));
			this.load();
			System.out.println(name+" cheio: "+ Arrays.toString(passengers.toArray()));
			
			//Ride
			System.out.println(name+" Correndo");
			for( Person p : passengers ){
				p.will=false;
			}
			
			//Empty Car
			System.out.println(name+" esvaziando: "+ Arrays.toString(passengers.toArray()));
			this.unload();
			System.out.println(name+" saiu todo mundo: "+ Arrays.toString(passengers.toArray()));
			
			//Count ride
			rides--;
			System.out.println("-----Fechando o brinquedo em: "+rides);
		}	
	}
	
	public void load(){
		carDoorIn.unlock();
		while(inCount<capacity){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		carDoorIn.lock();
	}
	
	public void unload(){
		carDoorOut.unlock();
		while( outCount<capacity ){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		carDoorOut.lock();
	}
	
	public boolean stillRide(){
		return rides!=0;
	}
	
}
