package model.classes;

import model.interfaces.Factory;
import model.interfaces.Material;
import model.interfaces.Request;

public class RequestImpl implements Request {
	
	public RequestImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Factory getSendingFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Factory getReceiverFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Material getSentMaterial() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSentQuantity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRequestId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSendingFactory(Factory sendingAgency) {
		// TODO Auto-generated method stub
		
	}
}
