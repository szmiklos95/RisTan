package network;

import java.io.*;
/**
 * This is a wrapper class, what contains the message type and data
 * @author Péter
 *
 */
public class Message implements Serializable{
	private static final long serialVersionUID = 1L;

 public enum eMsgType{
		Identification,
		Name,
		Text,
		Action
	};
	
	private eMsgType type;
	private Object data;
	
	Message(int id){
		type =eMsgType.Identification;
		data = id;
	}
	
	public Message(String s){
		type = eMsgType.Text;
		data = s;
	}
	
	public Message(eMsgType t, Object d){
		type = t;
		data = d;
	}
	
	public eMsgType GetType() {
		return type;
	}
	
	public Object GetData() {
		return data;
	}
	
	public void SetType(eMsgType t) {
		type = t;
	}
	
	public void SetData(Object payload) {
		data = payload;
	}
}
