package com.cupcake.bluetooth;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StreamCorruptedException;
import java.lang.reflect.Method;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.cupcake.util.Constants;

public class BlueToothManager
    {
	// the current status of connection with remote device
	private int connState;
	private final BluetoothAdapter bluetoothAdapter;
	// handler used to send msg back to the activity
	private final Handler handler;
	// declare and initialize the threads that are used in this class
	private ClientThread clientThread;
	private ServerThread serverThread;
	private CommunicationThread conmmunicationThread;

	public BlueToothManager(Context context, Handler handler)
	    {
		this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		this.connState = Constants.NONE;
		this.handler = handler;
		clientThread = null;
		serverThread = null;
		conmmunicationThread = null;
	    }

	/**
	 * Set the current state of the chat connection the function must be setup as synchronized for thread safety
	 * 
	 * @param connState
	 * An integer defining the current connection state
	 */
	public synchronized void setConnState(int connState, Object obj)
	    {
		Log.v("mad", "setConnState() " + this.connState + " -> " + connState);
		this.connState = connState;
		// update the state in UI
		handler.obtainMessage(Constants.MSG_CURRENT_STATE, connState, -1, obj).sendToTarget();
	    }

	public synchronized int getConnState()
	    {
		Log.v("mad", "\n getConnState() is " + connState);
		return connState;
	    }

	/**
	 * sending data from UI
	 * 
	 * @param buffer
	 * The bytes to write
	 */	
	public void sendObj(Object object)
	    {
		// Create temporary object because the previous one  is now blocking and cannot work
		CommunicationThread temp;
		
		// Synchronize a copy of the ConnectedThread
		synchronized (this)
		    {
			if (connState != Constants.CONNECTED)
			    return;
			temp = conmmunicationThread;
		    }
		// Perform the write unsynchronized
		temp.writeObj(object);
	    }

	/**
	 * Start the ClientThread to initiate a connection to a remote device.
	 * 
	 * @param device
	 * BluetoothDevice to connect with
	 */
	public synchronized void connectAsClient(BluetoothDevice device)
	    {
		Log.v("mad", "start to connect with: " + device.getName());

		// reset to welcome new connection with another device
		freeAll();
		// start new client thread to connect with new device
		clientThread = new ClientThread(device);
		clientThread.start();
		setConnState(Constants.CONNECTING, device.getName());
	    }

	/**
	 * free all threads and reset state to face new connection with another device
	 */
	public synchronized void freeAll()
	    {
		if (clientThread != null)
		    {
			clientThread.closeClientSocket();
			clientThread = null;
		    }

		if (serverThread != null)
		    {
			serverThread.closeServerSocket();
			serverThread = null;
		    }

		if (conmmunicationThread != null)
		    {
			conmmunicationThread.closeCommunication();
			conmmunicationThread = null;
		    }
	    }

	/**
	 * start the ServerThread, acting as remote Server device change the state as listenning this function must be thread safety
	 */
	public synchronized void ListenningAsServer()
	    {
		Log.v("mad", "ListenningAsServer() starts");
		// reset to welcome new connection with another device
		freeAll();
		serverThread = new ServerThread();
		serverThread.start();
		// update connState
		setConnState(Constants.LISTENNING, null);
	    }

	private void startCommunication(BluetoothSocket bluetoothSocket, BluetoothDevice bluetoothDevice)
	    {
		// free all threads including commu thread that should have been null because they are useless
		freeAll();
		// Start the thread to manage the connection and perform transmissions
		conmmunicationThread = new CommunicationThread(bluetoothSocket);
		conmmunicationThread.start();

		// Send back the connected device name to the UI Activity
		handler.obtainMessage(Constants.MSG_DEVICE_NAME, bluetoothDevice.getName()).sendToTarget();
	    }

	private class ServerThread extends Thread
	    {
		// The local server socket
		private final BluetoothServerSocket bluetoothServerSocket;

		public ServerThread()
		    {
			BluetoothServerSocket tmp = null;
			// Create a new listening server socket
			try
			    {
				// start listenning new connection from client
				//tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord(Constants.MY_BLUETOOTH_SERVER, Constants.MY_UUID_2);
				//tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord(Constants.MY_BLUETOOTH_SERVER, Constants.MY_UUID_2);
				// this does not worl i do not know why so wired!

				Method listenMethod = bluetoothAdapter.getClass().getMethod("listenUsingRfcommOn", new Class[] { int.class });
				tmp = (BluetoothServerSocket) listenMethod.invoke(bluetoothAdapter, Integer.valueOf(1));
			    } catch (Exception e)
			    {
				Log.v("mad", e.toString());
			    }
			this.bluetoothServerSocket = tmp;
			Log.v("mad", "BluetoothServerSocket extablished");
		    }

		@Override
		public void run()
		    {
			Log.v("mad", "starts server thread");
			// connected socket in server device
			BluetoothSocket temp = null;
			// Listen to the server socket if we're not connected
			while (connState != Constants.CONNECTED)
			    {
				try
				    {
					Log.v("mad", "server accept() blocking noow...");
					// a blocking call and will only return success or exception
					// bluetoothServerSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(Constants.BLUETOOTH_SERVER, Constants.MY_UUID);
					temp = bluetoothServerSocket.accept();				
					Log.v("mad", "BluetoothSocket established" + temp.toString());					
				    } catch (Exception e)
				    {
					Log.v("mad", "\n accept() failed \n" + e.toString());
					break;
					// continue;
				    }
				// Start new clientThread to connect with the given device
				if (temp != null)
				    {
					// the new conn is ready for data transfer
					startCommunication(temp, temp.getRemoteDevice());
				    }
				setConnState(Constants.CONNECTED, temp.getRemoteDevice().getName());
				closeServerSocket();
				break;
			    }
			Log.v("mad", "server thread ends");
		    }

		public void closeServerSocket()
		    {
			try
			    {
				bluetoothServerSocket.close();
			    } catch (IOException e)
			    {
				Log.v("mad", "\n close()  in server thread failed \n" + e.getMessage());
			    }
		    }
	    }

	private class ClientThread extends Thread
	    {
		// the socket to send and receive data
		private final BluetoothSocket bluetoothSocket;
		// the remote server device to connect with
		private final BluetoothDevice bluetoothDevice;

		public ClientThread(BluetoothDevice device)
		    {
			this.bluetoothDevice = device;
			BluetoothSocket tmp = null;
			// Get a BluetoothSocket for a connection with thegiven BluetoothDevice
			try
			    {
				//tmp = device.createRfcommSocketToServiceRecord(Constants.MY_UUID);
				// tmp = bluetoothDevice.createRfcommSocketToServiceRecord(Constants.MY_UUID_2);

				Method m = bluetoothDevice.getClass().getMethod("createRfcommSocket", new Class[] { int.class });
				tmp = (BluetoothSocket) m.invoke(bluetoothDevice, Integer.valueOf(1));
			    } catch (Exception e)
			    {
				Log.e("mad", "client socket create failed \n" + e.getMessage());
			    }
			this.bluetoothSocket = tmp;
			Log.v("mad", "\n bluetoothSocket in client extablished good \n");
		    }

		public void run()
		    {
			Log.v("mad", "client thread starts");
			// Cancel discovery because it will slow down the connection
			bluetoothAdapter.cancelDiscovery();
			// Make a connection to the BluetoothSocket
			try
			    {
				Log.v("mad", "\n client connect is now blocking .... \n");
				// a blocking call and will return success or exception
				bluetoothSocket.connect();
			    } catch (Exception e)
			    {
				// Close the socket
				try
				    {
					bluetoothSocket.close();
				    } catch (Exception ee)
				    {
					Log.v("mad", "\n unable to close() socket during connection failure\n" + ee.getMessage());
				    }
				return;
			    }
			Log.v("mad", "\n connection is good  in client \n");
			// the conn is successfully done when running here so we can make the current client thread as null to save space
			synchronized (BlueToothManager.this)
			    {
				clientThread = null;
			    }
			// Start the connected thread
			startCommunication(bluetoothSocket, bluetoothDevice);
			// Start new clientThread to connect with the given device
			setConnState(Constants.CONNECTED, bluetoothDevice.getName());
			Log.v("mad", "client thread ends");
		    }

		public void closeClientSocket()
		    {
			try
			    {
				bluetoothSocket.close();
			    } catch (IOException e)
			    {
				Log.v("mad", "\nclient thread close() of connect socket failed\n" + e.getMessage());
			    }
		    }
	    }

	private class CommunicationThread extends Thread
	    {
		// the connected socket that is ready to send and receive data
		private final BluetoothSocket connectedSocket;
		// streams used to data transfer between sockets
		private  ObjectInputStream inStream;		//对象输入流
		private  ObjectOutputStream outStream;	//对象输出流
		//private final InputStream in;
		//private final OutputStream out;

		public CommunicationThread(BluetoothSocket connectedSocket)
		    {
			Log.v("mad", "\n start communication thread");
			this.connectedSocket = connectedSocket;

			InputStream tmpIn = null;
			OutputStream tmpOut = null;
			// Get the BluetoothSocket input and output streams
			try
			    {
				// get streams
				tmpIn = connectedSocket.getInputStream();
				tmpOut = connectedSocket.getOutputStream();
			    } catch (Exception e)
			    {
				Log.v("mad", "\n get in and out stream failed \n" + e.getMessage());
			    }
			Log.v("mad", "\n get in and out stream GOOD \n");
			// assign local streams
			//in = tmpIn;
			//out = tmpOut;	
			try
			    {
				outStream = new ObjectOutputStream(tmpOut);
				inStream = new ObjectInputStream(new BufferedInputStream(tmpIn));
				Log.v("mad", "\n STREAMS ASSIGNMENT GOOD \n");
			    }catch (Exception e)
			    {
				// TODO Auto-generated catch block
				e.printStackTrace();
			    }	
			Log.v("mad", "\n streams assignment good \n");
		    }

		@Override
		public void run()
		    {
			Log.v("mad", "run() in communication thread starts");
			//byte[] buffer = new byte[1024];
			//int bytes;

			// Keep listening to the InputStream while connected
			while (true)
			    {
				try
				    {
					// Read from the InputStream
					//bytes = in.read(buffer);
					Object obj = inStream.readObject();
					// Send the obtained bytes to the UI Activity
					//handler.obtainMessage(Constants.RECEIVED_DATA, bytes, -1, buffer).sendToTarget();
					handler.obtainMessage(Constants.RECEIVED_DATA, -1, -1, obj).sendToTarget();
				    } catch (Exception e)
				    {
					Log.e("mad", "disconnected", e);
					break;
				    }
			    }
		    }

		/**
		 * Write to the connected OutStream.
		 * 
		 * @param buffer
		 * The bytes to write
		 */		
		public void writeObj(Object obj)
		    {
			try
			    {
				outStream.writeObject(obj);
				Log.v("mad", "\n out.write(buffer) content is " + obj.toString());
			    } catch (Exception e)
			    {
				Log.v("mad", "\n Exception during write");
			    }
		    }

		public void closeCommunication()
		    {
			try
			    {
				inStream.close();
				outStream.close();
				connectedSocket.close();
				Log.v("mad", "\n closeCommunication() in comm thread good \n");
			    } catch (Exception e)
			    {
				Log.v("mad", "\n closeCommunication() in comm thread failed \n" + e.getMessage());
			    }
		    }
	    }
    }
