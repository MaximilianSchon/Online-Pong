package server;

import Pong.*;

public class PongMonitor {
	
	private int p1Vel;
	private int p2Vel;
	
	private Pong pong;
	
	
	public PongMonitor(){
		pong = new Pong();
		
	}
	
	synchronized public Pong getPong(){
		return pong;
	}
	
	synchronized public void step(){
		
		pong.step(p1Vel, p2Vel);
	}
	
	
	synchronized public void setP1Vel(int v){
		p1Vel = v;
		
	}
	
	synchronized public void setP2Vel(int v){
		p2Vel = v;
	}
	
	synchronized public double getBallX(){
		return pong.getBallX();
	}
	
	synchronized public double getBallY(){
		return pong.getBallY();
	}
	
	synchronized public double getP1Y(){
		return pong.getPlayer1Y();
		
	}
	synchronized public double getP2Y(){
		return pong.getPlayer2Y();

	}
	

}
