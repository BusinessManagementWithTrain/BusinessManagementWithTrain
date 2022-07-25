package view.staff;

import java.awt.EventQueue;

import javax.swing.JFrame;

import controller.classes.ManagerImpl;
import exceptions.EmptyWarehouseException;
import exceptions.FullWarehouseException;
import exceptions.LowTrainCapacityException;
import exceptions.NullManagerException;
import exceptions.StaffIsAlreadyNotWorkingException;
import exceptions.StaffIsAlreadyWorkingException;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class staffFrameWB {

	private JFrame frame;
	private JFrame managerFrame;

	/**
	 * Launch the application.
	 * @throws LowTrainCapacityException 
	 */
	public static void main(String[] args) throws LowTrainCapacityException {
		ManagerImpl.getManager(100);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					staffFrameWB window = new staffFrameWB(new JFrame());
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public staffFrameWB(JFrame f) {
		managerFrame = f;
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton startWorkingBtn = new JButton("Inizia a Lavorare");
		startWorkingBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					ManagerImpl.getManager().showFactoryInfo(null).getStuffMembers().startWorking();
				} catch (EmptyWarehouseException e1) {
					popUpEmptyWarehouse p = new popUpEmptyWarehouse();
					if(e.isPopupTrigger()) {
						p.show(e.getComponent(), 0, 0);
					}
				} catch (StaffIsAlreadyWorkingException e1) {
					popUpStaffWorking p = new popUpStaffWorking();
					if(e.isPopupTrigger()) {
						p.show(e.getComponent(), 0, 0);
					}
				} catch (NullManagerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		startWorkingBtn.setBounds(154, 51, 117, 32);
		frame.getContentPane().add(startWorkingBtn);
		
		JButton stopWorkingBtn = new JButton("Smetti di Lavorare");
		stopWorkingBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					ManagerImpl.getManager().showFactoryInfo(null).getStuffMembers().stopWorking();
				} catch (FullWarehouseException e1) {
					popUpFullWarehouse p = new popUpFullWarehouse();
					if(e.isPopupTrigger()) {
						p.show(e.getComponent(), 0, 0);
					}
				} catch (StaffIsAlreadyNotWorkingException e1) {
					popUpStaffIsAlreadyNotWorking p = new popUpStaffIsAlreadyNotWorking();
					if(e.isPopupTrigger()) {
						p.show(e.getComponent(), 0, 0);
					}
				} catch (NullManagerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		stopWorkingBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		stopWorkingBtn.setBounds(154, 112, 117, 32);
		frame.getContentPane().add(stopWorkingBtn);
		
		JButton backBtn = new JButton("Back");
		backBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setVisible(false);
				managerFrame.setVisible(true);
			}
		});
		backBtn.setBounds(341, 232, 85, 21);
		frame.getContentPane().add(backBtn);
	}
}
