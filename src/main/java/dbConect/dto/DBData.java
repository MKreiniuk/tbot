package dbConect.dto;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;

@SuppressWarnings("serial")
@Document(collection = "DBData")
@Getter
public class DBData  implements Serializable{
	
	@Id
	String id;
	
	String name;

	public DBData(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "DBData [id=" + id + ", name=" + name + "]";
	}
	
	

}
