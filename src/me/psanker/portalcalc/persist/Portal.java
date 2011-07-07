package me.psanker.portalcalc.persist;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;



@Entity
@Table(name = "pc_portal")
public class Portal {
	
	String world;
	String name;
	
	@Id
	UUID id;
	
	Integer x;
	Integer y;
	Integer z;
	
	boolean orientation;
	
	//String world_name;
	
	public Portal(){
		
		
	}
	
	public void setX(Integer x){this.x = x;}
	
	public Integer getX(){return x;}

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

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getId() {
		return id;
	}

	public void setWorld(String world) {
		this.world = world;
	}

	public String getWorld() {
		return world;
	}
}
