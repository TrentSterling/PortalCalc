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
	
	String worldName;
	
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

	public void setWorldName(String worldName) {
		this.worldName = worldName;
	}

	public String getWorldName() {
		return worldName;
	}
}
