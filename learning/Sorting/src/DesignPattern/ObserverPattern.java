package DesignPattern;

import java.util.ArrayList;
import java.util.List;

public class ObserverPattern {
	public static void main(String[] args) {

		Subject subject = new Subject();
		Observer binaryObserver1 = new BinaryObserver(subject);
		Observer binaryObserver2 = new BinaryObserver(subject);

		subject.setState(15);
			
		subject.setState(20);

	}
}

class Subject {
	
	private int state = 0;
	List<Observer> observers = new ArrayList<Observer>();
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
		this.notifyObservers();
	}
	
	public void attachObservers(Observer observer) {
		this.observers.add(observer);
	}

	private void notifyObservers() {
		for (Observer observer:observers) {
			observer.update();
		}
	}
	
	
}

abstract class Observer {
	protected int id;
	private static int idCount = 0;

	protected Subject subject;
	public abstract void update();


	
	
	public int getId() {
		return id;
	}




	public void setId(int id) {
		this.id = id;
	}




	public Subject getSubject() {
		return subject;
	}




	public void setSubject(Subject subject) {
		this.subject = subject;
	}




	public static int getIdCount() {
		return idCount;
	}

	public static void setIdCount(int idCount) {
		Observer.idCount = idCount;
	}
}

class BinaryObserver extends Observer{
	
	BinaryObserver(Subject subject) {
		this.setId(Observer.getIdCount()+1);
		Observer.setIdCount(Observer.getIdCount()+1);
		
		this.setSubject(subject);
		this.getSubject().attachObservers(this);
	}
	
	 public void update() {
		 System.out.println("Binary Observer"+this.getId()+":Find subject state = " + this.subject.getState());
	 }
}

