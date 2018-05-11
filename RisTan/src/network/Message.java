package network;

import java.io.*;
/**
 * This is a wrapper class, what contains the message type and data
 * It has to be serializable for the message transfer
 * @author Péter
 *
 */
public class Message implements Serializable{
	private static final long serialVersionUID = 1L;
/**
 * Enumeration for the possible message types
 * @author Péter
 *
 */
 public enum eMsgType{
	 /**
	  * For assigning the client thread and playerID
	  */
		Identification,
	/**
	 * 	For update game list
	 */
		Name,
	/**
	 * Indicates chat message
	 */
		Text,
	/**
	 * Indicates the game action
	 */
		Action
	};
	/**
	 * message type
	 */
	private eMsgType type;
	/**
	 * message data - payload
	 */
	private Object data;
	/**
	 * Constructor
	 * @param id
	 */
	Message(int id){
		type =eMsgType.Identification;
		data = id;
	}
	/**
	 * Constructor
	 * @param s
	 */
	public Message(String s){
		type = eMsgType.Text;
		data = s;
	}
	/**
	 * Constructor
	 * @param t
	 * @param d
	 */
	public Message(eMsgType t, Object d){
		type = t;
		data = d;
	}
	/**
	 * Get the message type
	 * @return
	 */
	public eMsgType GetType() {
		return type;
	}
	/**
	 * Get the message data
	 * @return
	 */
	public Object GetData() {
		return data;
	}
	/**
	 * Set the message type
	 * @param t
	 */
	public void SetType(eMsgType t) {
		type = t;
	}
	
	/**
	 * Set the message data
	 * @param payload
	 */
	public void SetData(Object payload) {
		data = payload;
	}
}
