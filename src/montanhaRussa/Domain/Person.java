package montanhaRussa.Domain;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Person extends Thread{
	private String name;
	public boolean will;
	public boolean inMountain;
	private static ReentrantLock ticketGate = new ReentrantLock();
	private Car c;
	
	public Person(String name, Car c){
		super(name);
		will = false;
		inMountain = false;
		this.name = name;
		this.c = c;
	}
	
	@Override
	public void run(){
		Random r = new Random();
		while(c.stillRide()){
			if(!inMountain && !will ){
				try {
					Thread.sleep(r.nextInt(1001));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				will = true;
				System.out.println(name + " quer andar na montanha russa.");
			}
			if(will && !inMountain){
				this.board();
			}
			if(!will && inMountain){
				this.unboard();
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void board(){
		ticketGate.lock();
		if( Car.carDoorIn.getHoldCount()==0 && c.stillRide() && Car.inCount<Car.capacity){
			Car.inCount++;
			c.passengers.add(this);
			inMountain = true;
			System.out.println("\t\t>>>" + name + ": Entrei!");
		}
		ticketGate.unlock();
	}
	
	public void unboard(){
		ticketGate.lock();
		if( Car.carDoorOut.getHoldCount()==0 ){
			Car.outCount++;
			c.passengers.remove(this);
			inMountain = false;
			System.out.println("\t\t<<<" + name + ": Sai");
		}
		ticketGate.unlock();
	}
	
	@Override
	public String toString(){
		return name;
	}
	
}
