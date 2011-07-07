package me.psanker.portalcalc.persist;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;



@Entity
@Table(name = "pc_portal")
public class Portal {
	
	@Id
	String name;
	
	Integer x;
	Integer y;
	Integer z;
	
	Integer uid;
	
	//String world_name;
	
	public Portal(){
		
		
	}
	
	public void setX(Integer x){this.x = x;}
	
	public Integer getX(){return x;}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getUid() {
		return uid;
	}

	public void setZ(Integer z) {
		this.z = z;
	}

	public Integer getZ() {
		return z;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public Integer getY() {
		return y;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
/*
	public void setWorld_name(String world_name) {
		this.world_name = world_name;
	}

	public String getWorld_name() {
		return world_name;
	}
*/
}
