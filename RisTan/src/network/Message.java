package network;

import java.io.*;

/* This is a wrapper class
 *  This class is contain all the type of message: 
 * - int: assign the client id to serverThread
 * - String: chat
 * - ???: MAP (just the start of the game)
 * - ???: The actions what has done the player
 * */

public class Message implements Serializable{
	private static final long serialVersionUID = 1L;

 enum eMsgType{
	 	Init,
		Identification,
		Name,
		Text,
		Map,
		Action
	};
	
	/* I need a tag variable what can cointain anytype of message:
	/*  ~ void*                             */
	private eMsgType type;
	private Object data;
	
	Message(){
		type = eMsgType.Init;
		data = null;
	}
	
	Message(int id){
		type =eMsgType.Identification;
		data = id;
	}
	
	Message(String s){
		type = eMsgType.Text;
		data = s;
	}
	
	Message(eMsgType t, Object d){
		type = t;
		data = d;
	}
	
	public eMsgType GetType() {
		return type;
	}
	
	public void SetType(eMsgType t) {
		type = t;
	}
	
	public void SetData(Object payload) {
		switch(type) {
		case Init:
			data = null;
			break;
		case Identification:
			data = (int)payload;
			break;
		case Text:
			data = (String)payload;
			break;
		case Map:
			break;
		case Action:
			break;
		default:
			break;
		}
	}
	
	public Object GetData() {
		return data;
	}
}
