package model.classes;

import model.interfaces.Agency;
import model.interfaces.Material;
import model.interfaces.Request;

public class RequestImpl implements Request {
	
	public RequestImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Agency getSendingAgency() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Agency getReceiverAgency() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Material getMaterialSended() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getQuantitySended() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRequestId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSendingAgency(Agency sendingAgency) {
		// TODO Auto-generated method stub

	}

}
